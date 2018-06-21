package com.yinhai.plugin.fs.mongo.template;

import com.mongodb.MongoClient;
import com.mongodb.MongoGridFSException;
import com.mongodb.ReadPreference;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSDownloadByNameOptions;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.gridfs.GridFS;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
* @package com.yinhai.plugin.fs.mongo.template
* <p>Title: GridFSTemplate.java</p>
* <p>Description: 针对MongoDB 3.2.2版本的GridFS操作</p>
* @author cjh
* @date 2016年6月27日 下午1:40:13
* @version 1.0
 */
public class GridFSTemplate {
	private String bucketName;
	private String tempBucketName;// 存放临时文件路劲
	private int chunkSize;
	private static final int DEFAULT_REVISION = 0;
	private static final String DEFAULT_BUSINESS_FILE_COLLECTION = "businessfiles";
	private static final String DEFAULT_TEMPBUCKETNAME = "tempfile";
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private volatile boolean checkedIndexes;
	private MongoDatabase database;
	private String businessFileCollectionName;
	private MongoCollection<Document> businessFileCollection;
	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public GridFSTemplate(MongoTemplate mongoTemplate) {
		this.bucketName = GridFS.DEFAULT_BUCKET;
		this.tempBucketName = DEFAULT_TEMPBUCKETNAME;
		this.chunkSize = GridFS.DEFAULT_CHUNKSIZE;
		this.businessFileCollectionName = DEFAULT_BUSINESS_FILE_COLLECTION;
		this.database = mongoTemplate.getMongoDatabase();
		this.businessFileCollection = this.database.getCollection(this.businessFileCollectionName);
	}

	public GridFSFile find(String businessFileId) {
		return find(businessFileId, this.bucketName);
	}
	
	public GridFSFile find(String businessFileId, String bucketName) {
		checkCreateBusinessFileIndex();
		Document query = new Document("businessFileId", businessFileId).append("level", new BsonInt32(1));
		Document businessFile = this.businessFileCollection.find(query).first();
		ObjectId fileid = (ObjectId) businessFile.get("fileid");
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		GridFSFindIterable fsFindIterable = bucket.find(new Document().append("_id", fileid));
		return fsFindIterable.first();
	}
	
	public GridFSFile findTempFile(String id) {
		return findTempFile(id, this.tempBucketName);
	}
	
	public GridFSFile findTempFile(String id, String bucketName) {
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		GridFSFindIterable fsFindIterable = bucket.find(new Document().append("_id", new ObjectId(id)));
		return fsFindIterable.first();
	}
	
	public ObjectId store(String businessFileId, InputStream inputStream, String fileName) {
		return store(businessFileId, inputStream, fileName, new Document(),this.bucketName);
	}
	
	public ObjectId store(String businessFileId, InputStream inputStream, String fileName, String bucketName) {
		return store(businessFileId, inputStream, fileName, new Document(), bucketName);
	}
	
	public ObjectId store(String businessFileId, InputStream inputStream, String fileName, Document metadata) {
		return store(businessFileId, inputStream, fileName, metadata, this.bucketName);
	}
	
	public ObjectId store(String businessFileId, InputStream inputStream, String fileName, Document metadata, String bucketName) {
		GridFSUploadOptions gridFSUploadOptions = getGridFSUploadOptions(metadata);
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		ObjectId file_id = bucket.uploadFromStream(fileName, inputStream, gridFSUploadOptions);
		Document businessfile = new Document("businessFileId", businessFileId);
		businessfile.append("filename", fileName);
		businessfile.append("uploadDate", new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date()));
		businessfile.append("metadata", metadata);
		businessfile.append("fileid", file_id);
		businessfile.append("level", new BsonInt32(1));// 最新版本
		try {
			insertBusinessFileCollection(businessfile);
		} catch (Exception e) {
			deleteFileAndChunks(file_id, this.bucketName);
			throw e;
		}
		return file_id;
	}
	
	private void insertBusinessFileCollection(Document businessfile) {
		checkBusinessFileLevel(businessfile.getString("businessFileId"));
		this.businessFileCollection.insertOne(businessfile);
	}
	
	private void checkBusinessFileLevel(String businessFileId) {
		Document query = new Document("businessFileId", businessFileId).append("level", new BsonInt32(1));
		if(this.businessFileCollection.withReadPreference(ReadPreference.primary()).find(query).first() != null) {
			UpdateResult result = this.businessFileCollection.updateMany(query, new Document("$set", new Document("level", new BsonInt32(0))));
			if (result.wasAcknowledged() && result.getModifiedCount() == 0L){
				throw new MongoGridFSException(String.format("Update BusinessFile error, No File Found: %s", new Object[] { businessFileId, "level=1"}));
			}
		}
	}
	
	private void checkCreateBusinessFileIndex() {
		if (!checkedIndexes) {
			Document businessFileIndex = (new Document("businessFileId", Integer.valueOf(1))).append("level", Integer.valueOf(1));
			if (!hasIndex(this.businessFileCollection.withReadPreference(ReadPreference.primary()), businessFileIndex)){
				this.businessFileCollection.createIndex(businessFileIndex);
			}
			Document businessIdIndex = (new Document("businessFileId", Integer.valueOf(1)));
			if(!hasIndex(this.businessFileCollection.withReadPreference(ReadPreference.primary()), businessIdIndex)){
				this.businessFileCollection.createIndex(businessIdIndex);
			}
			checkedIndexes = true;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean hasIndex(MongoCollection<?> collection, Document index) {
		boolean hasIndex = false;
		ArrayList indexes = (ArrayList) collection.listIndexes().into(new ArrayList());
		Iterator iterator = indexes.iterator();
		do {
			if (!iterator.hasNext())
				break;
			Document indexDoc = (Document) iterator.next();
			if (!((Document) indexDoc.get("key", Document.class)).equals(index))
				continue;
			hasIndex = true;
			break;
		} while (true);
		return hasIndex;
	}
	
	public ObjectId storeTempFile(InputStream inputStream, String fileName) {
		return storeTempFile(inputStream, fileName, new Document(),this.tempBucketName);
	}
	
	public ObjectId storeTempFile(InputStream inputStream, String fileName, String bucketName) {
		return storeTempFile(inputStream, fileName, new Document(), bucketName);
	}
	
	public ObjectId storeTempFile(InputStream inputStream, String fileName, Document metadata) {
		return storeTempFile(inputStream, fileName, metadata, this.tempBucketName);
	}
	
	public ObjectId storeTempFile(InputStream inputStream, String fileName, Document metadata, String bucketName) {
		GridFSUploadOptions gridFSUploadOptions = getGridFSUploadOptions(metadata);
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		return bucket.uploadFromStream(fileName, inputStream, gridFSUploadOptions);
	}
	
	public void delete(String businessFileId) {
		delete(businessFileId, this.bucketName);
	}
	
	public void delete(String businessFileId, String bucketName) {
		FindIterable<Document> it = this.businessFileCollection.find(new Document("businessFileId", businessFileId));
		for (Document document : it) {
			if(document.containsKey("fileid")){
				ObjectId fileid = (ObjectId) document.get("fileid");
				deleteFileAndChunks(fileid, bucketName);
			}
		}
		DeleteResult deleteResult = this.businessFileCollection.deleteMany(new Document("businessFileId", businessFileId));
		if(deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() == 0L){
			throw new MongoGridFSException(String.format("Delete BusinessFile error, No BusinessFile Found: %s", new Object[] { businessFileId}));
		}
	}
	
	public void deleteTempFile(String id) {
		deleteFileAndChunks(new ObjectId(id),this.tempBucketName);
	}
	
	public void deleteTempFile(String id, String bucketName) {
		deleteFileAndChunks(new ObjectId(id),bucketName);
	}
	
	@Deprecated
	public void deleteByName(String name) {
		deleteByName(name, this.bucketName);
	}
	
	@Deprecated
	public void deleteByName(String name, String bucketName) {
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		GridFSDownloadByNameOptions downloadOptions = getGridFSDownloadByNameOptions(DEFAULT_REVISION);
		GridFSFile file = getFileByName(name, downloadOptions, bucket);
		deleteFileAndChunks(file.getObjectId(),bucketName);
	}
	
	public GridFSDownloadStream openTempDownloadStream(String id) {
		return openTempDownloadStream(id, this.tempBucketName);
	}
	
	public GridFSDownloadStream openTempDownloadStream(String id, String bucketName) {
		GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
		return bucket.openDownloadStream(new ObjectId(id));
	}
	
	public GridFSDownloadStream openDownloadStream(String businessFileId) {
		return openTempDownloadStream(businessFileId, this.bucketName);
	}
	
	public GridFSDownloadStream openDownloadStream(String businessFileId, String bucketName) {
		Document query = new Document("businessFileId", businessFileId).append("level", new BsonInt32(1));
		if(this.businessFileCollection.find(query).first() != null){
			Document businessFile = this.businessFileCollection.find(query).first();
			if(businessFile.containsKey("fileid")){
				ObjectId fileid = (ObjectId) businessFile.get("fileid");
				GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
				return bucket.openDownloadStream(fileid);
			}
		}
		return null;
	}
	
	public void downloadFileToStream(String businessFileId, OutputStream out) {
		downloadFileToStream(businessFileId, out, this.bucketName);
	}
	
	public void downloadFileToStream(String businessFileId, OutputStream out, String bucketName) {
		Document query = new Document("businessFileId", businessFileId).append("level", new BsonInt32(1));
		if(this.businessFileCollection.find(query).first() != null){
			Document businessFile = this.businessFileCollection.find(query).first();
			if(businessFile.containsKey("fileid")){
				ObjectId fileid = (ObjectId) businessFile.get("fileid");
				GridFSBucket bucket = GridFSBuckets.create(database, bucketName);
				bucket.downloadToStream(fileid, out);
			}
		}
	}
	
	private GridFSUploadOptions getGridFSUploadOptions(Document metadata) {
		return new GridFSUploadOptions().chunkSizeBytes(this.chunkSize).metadata(metadata);
	}
	
	private GridFSDownloadByNameOptions getGridFSDownloadByNameOptions(int revision) {
		return new GridFSDownloadByNameOptions().revision(revision);
	}
	
	private static MongoCollection<?> getFilesCollection(MongoDatabase database, String bucketName) {
		return database.getCollection((new StringBuilder()).append(bucketName).append(".files").toString())
				.withCodecRegistry(CodecRegistries.fromRegistries(
						new CodecRegistry[] { database.getCodecRegistry(), MongoClient.getDefaultCodecRegistry() }));
	}

	private static MongoCollection<?> getChunksCollection(MongoDatabase database, String bucketName) {
		return database.getCollection((new StringBuilder()).append(bucketName).append(".chunks").toString())
				.withCodecRegistry(MongoClient.getDefaultCodecRegistry());
	}
	
	private void deleteFileAndChunks(ObjectId id, String bucketName) {
		MongoCollection<?> filesCollection = getFilesCollection(this.database, bucketName);
		MongoCollection<?> chunksCollection = getChunksCollection(this.database, bucketName);
		DeleteResult result = filesCollection.deleteOne(new BsonDocument("_id", new BsonObjectId(id)));
		chunksCollection.deleteMany(new BsonDocument("files_id", new BsonObjectId(id)));
		if (result.wasAcknowledged() && result.getDeletedCount() == 0L){
			throw new MongoGridFSException(String.format("No file found with the ObjectId: %s", new Object[] { id }));
		}else{
			return;
		}
	}
	
	private GridFSFile getFileByName(String filename, GridFSDownloadByNameOptions options, GridFSBucket bucket) {
		int revision = options.getRevision();
		int skip;
		int sort;
		if (revision >= 0) {
			skip = revision;
			sort = 1;
		} else {
			skip = -revision - 1;
			sort = -1;
		}
		GridFSFile fileInfo = (GridFSFile) bucket.find(new Document("filename", filename)).skip(skip)
				.sort(new Document("uploadDate", Integer.valueOf(sort))).first();
		if (fileInfo == null) {
			throw new MongoGridFSException(String.format("No file found with the filename: %s and revision: %s",
					new Object[] { filename, Integer.valueOf(revision) }));
		} else {
			return fileInfo;
		}
	}
	
	public int getChunkSize() {
		return chunkSize;
	}
	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	public MongoDatabase getDatabase() {
		return database;
	}
	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}
	public String getTempBucketName() {
		return tempBucketName;
	}
	public void setTempBucketName(String tempBucketName) {
		this.tempBucketName = tempBucketName;
	}
	public String getBusinessFileCollectionName() {
		return businessFileCollectionName;
	}
	public void setBusinessFileCollectionName(String businessFileCollectionName) {
		this.businessFileCollectionName = businessFileCollectionName;
	}
}
