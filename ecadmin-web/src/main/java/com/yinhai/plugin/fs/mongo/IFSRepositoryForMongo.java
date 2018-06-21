package com.yinhai.plugin.fs.mongo;

import java.io.OutputStream;

import org.bson.Document;

import com.yinhai.plugin.fs.FSBaseDomain;
import com.yinhai.plugin.fs.IFSBaseRepository;

public interface IFSRepositoryForMongo extends IFSBaseRepository {
	/**
	  * @package com.yinhai.plugin.fs
	  * @method save 方法 
	  * @describe <p>方法说明:自动根据文件业务ID保存文件,FSBaseDomain 文件流和文件名称不能为空</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月18日 上午10:12:17
	 */
	public FSBaseDomain storeFile(String businessFileId, FSBaseDomain fs, Document metadata);
	
	/**
	  * @package com.yinhai.plugin.fs.mongo
	  * @method getTempFile 方法 
	  * @describe <p>方法说明: 返回临时文件</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月1日 上午10:52:54
	 */
	public FSBaseDomain getTempFile(String id); 
	
	/**
	  * @package com.yinhai.plugin.fs.mongo
	  * @method uploadTempFile 方法 
	  * @describe <p>方法说明:</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月1日 上午9:52:53
	 */
	public FSBaseDomain uploadTempFile(FSBaseDomain fs, Document metadata);
	
	/**
	  * @package com.yinhai.plugin.fs.mongo
	  * @method moveTemp2Master 方法 
	  * @describe <p>方法说明:根据文件业务ID,将临时文件移动到正式文件夹 并且删除临时文件</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月1日 上午10:02:05
	 */
	public FSBaseDomain moveAndRemoveTemp2Master(String businessFileId, String tmpid);
	
	/**
	  * @package com.yinhai.plugin.fs.mongo
	  * @method downloadFileToStream 方法 
	  * @describe <p>方法说明:文件下载到输出流</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月1日 上午11:34:09
	 */
	public void downloadFileToStream(String businessFileId, OutputStream out);
}
