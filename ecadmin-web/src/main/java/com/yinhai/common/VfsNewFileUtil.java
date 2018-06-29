package com.yinhai.common;

import org.apache.commons.vfs2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <p>Title: VfsNewFileUtil.java</p>
 * <p>Description: sftp,ftp文件操作工具类</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 久远银海</p>
 *
 * @author Hyman
 * @version 1.0
 * @date 2015-10-22
 */
public class VfsNewFileUtil {
    //ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder
    private static FileSystemManager fileManager;
    private static Logger log = LoggerFactory.getLogger(VfsNewFileUtil.class);

    static {
        try {
            fileManager = VFS.getManager();
            VFS.setUriStyle(true);
        } catch (FileSystemException e) {
            log.error("Init VfsNewFileUtil FileManager Fail.", e);
        }

    }

    /**
     * exists 方法
     * <p>方法说明:传入"ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder"
     * 只要有一层不存在,视为不存在</p>
     *
     * @return boolean
     * @author Hyman
     * @date 2015-10-16
     */
    public static boolean exists(String repository) throws IOException {
        FileObject fileObject;
        if (repository != null && !"".equals(repository)) {
            //ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder
            String ftpHead = "";
            if (repository.startsWith("ftp://")) {
                ftpHead += "ftp://";
                repository = repository.substring(6);
            } else if (repository.startsWith("sftp://")) {
                ftpHead += "sftp://";
                repository = repository.substring(7);
            }
            String[] paths = repository.split("/");
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (i == 0) {
                    ftpHead += path;
                } else {
                    ftpHead += "/" + path;
                }
                if (!path.endsWith(".jpg") && !path.endsWith(".png")
                        && !path.endsWith(".jpeg") && !path.endsWith(".bmp")
                        && !path.endsWith(".gif")) {
                    fileObject = fileManager.resolveFile(ftpHead);
                    fileObject.refresh();
                    if (!fileObject.isReadable()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean removeFile(String repository) throws IOException {
        if (exists(repository)) {
            FileObject fileObj = fileManager.resolveFile(repository);
            fileObj.refresh();
            if (fileObj.exists()) {
                return removeFile(fileObj);
            }
        }
        return true;
    }

    private static boolean removeFile(FileObject rootFileObj) throws FileSystemException {
        if (rootFileObj.isFolder()) {
            FileObject[] childFileObjs = rootFileObj.getChildren();
            if (childFileObjs.length > 0) {
                for (FileObject o : childFileObjs) {
                    if (o.isFolder()) {
                        if (o.getChildren().length > 0) {
                            removeFile(o);
                        } else {
                            o.refresh();// 必须刷新，否则删除失败
                            o.deleteAll();
                        }
                    } else {
                        o.refresh();// 必须刷新，否则删除失败
                        o.deleteAll();
                    }
                }
            }
        }
        rootFileObj.refresh();// 必须刷新，否则删除失败
        rootFileObj.deleteAll();
        return true;
    }


    /**
     * createFolder 方法
     * <p>方法说明:传入"ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder",
     * 会依次判断路劲是否存在,不存在就会创建文件夹</p>
     *
     * @return boolean
     * @throws FileSystemException
     * @author Hyman
     * @date 2015-10-16
     */
    public static boolean createFolder(String uri) throws FileSystemException {
        FileObject fileObject;
        if (uri != null && !"".equals(uri)) {
            //ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder
            String ftpHead = "";
            if (uri.startsWith("ftp://")) {
                ftpHead += "ftp://";
                uri = uri.substring(6);
            } else if (uri.startsWith("sftp://")) {
                ftpHead += "sftp://";
                uri = uri.substring(7);
            }
            String[] paths = uri.split("/");
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (i == 0) {
                    ftpHead += path;
                } else {
                    ftpHead += "/" + path;
                }
                if (!path.endsWith(".jpg") && !path.endsWith(".png")
                        && !path.endsWith(".jpeg") && !path.endsWith(".bmp")
                        && !path.endsWith(".gif") && !path.endsWith(".rar")
                        && !path.endsWith(".zip") && !path.endsWith(".swf") && !path.endsWith(".flv") && !path.endsWith(".mp4")) {
                    fileObject = fileManager.resolveFile(ftpHead);
                    fileObject.refresh();
                    if (!fileObject.isReadable()) {
                        fileObject.createFolder();
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * createFolder 方法
     * <p>方法说明:传入"ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder",
     * 会依次判断路劲是否存在,不存在就会创建文件夹</p>
     * <p>注:targetFolderName可以是多级路劲,如test/imgs</p>
     *
     * @return boolean
     * @throws FileSystemException
     * @author Hyman
     * @date 2015-10-16
     */
    public static boolean createFolder(String uri, String targetFolderName) throws FileSystemException {
        FileObject fileObject;
        if (uri != null && !"".equals(uri)) {
            //ftp://akimg:akimg@222.216.206.142:21/imgs/testFolder
            String ftpHead = "";
            if (uri.startsWith("ftp://")) {
                ftpHead += "ftp://";
                uri = uri.substring(6);
            } else if (uri.startsWith("sftp://")) {
                ftpHead += "sftp://";
                uri = uri.substring(7);
            }
            String[] paths = uri.split("/");
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                if (i == 0) {
                    ftpHead += path;
                } else {
                    ftpHead += "/" + path;
                }
                if (!path.endsWith(".jpg") && !path.endsWith(".png")
                        && !path.endsWith(".jpeg") && !path.endsWith(".bmp")
                        && !path.endsWith(".gif")) {
                    fileObject = fileManager.resolveFile(ftpHead);
                    fileObject.refresh();
                    if (!fileObject.isReadable()) {
                        fileObject.createFolder();
                    }
                }
            }
            if (targetFolderName != null && !"".equals(targetFolderName)) {
                String[] ps = targetFolderName.split("/");
                for (int i = 0; i < ps.length; i++) {
                    String pp = ps[i];
                    ftpHead += "/" + pp;
                    if (!targetFolderName.endsWith(".jpg") && !targetFolderName.endsWith(".png")
                            && !targetFolderName.endsWith(".jpeg") && !targetFolderName.endsWith(".bmp")
                            && !targetFolderName.endsWith(".gif")) {
                        FileObject fileObj = fileManager.resolveFile(ftpHead);
                        fileObj.refresh();
                        if (!fileObj.isReadable()) {
                            fileObj.createFolder();
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * getFileInputStream 方法
     * <p>方法说明:获取文件输入流</p>
     *
     * @return InputStream
     * @author Hyman
     * @date 2015-10-22
     */
    public static InputStream getFileInputStream(String repository) throws IOException {
        if (exists(repository)) {
            FileObject fileObj = fileManager.resolveFile(repository);
            if (fileObj.isFile()) {
                fileObj.refresh();
                FileContent content = fileObj.getContent();
                InputStream is = content.getInputStream();
                return is;
            }
        }
        return null;
    }

    /**
     * saveFile 方法
     * <p>方法说明:上传文件,文件名必须紧跟在repository后面</p>
     * <p>方法说明:上传文件前,无需判断路劲存在与否,会自动判断并创建路劲</p>
     *
     * @return void
     * @author Hyman
     * @date 2015-10-22
     */
    public static void saveFile(String repository, byte[] bytes) throws IOException {
        saveFile(repository, new ByteArrayInputStream(bytes));
    }

    /**
     * saveFile 方法
     * <p>方法说明:上传文件,文件名必须紧跟在repository后面</p>
     * <p>方法说明:上传文件前,无需判断路劲存在与否,会自动判断并创建路劲</p>
     *
     * @return void
     * @author Hyman
     * @date 2015-10-22
     */
    public static void saveFile(String repository, File file) throws IOException {
        saveFile(repository, new FileInputStream(file));
    }

    /**
     * saveFile 方法
     * <p>方法说明:上传文件,文件名必须紧跟在repository后面</p>
     * <p>方法说明:上传文件前,无需判断路劲存在与否,会自动判断并创建路劲</p>
     *
     * @return void
     * @author Hyman
     * @date 2015-10-22
     */
    public static void saveFile(String repository, InputStream is) throws IOException {
        if (createFile(repository)) {
            FileObject fileObj = fileManager.resolveFile(repository);
            OutputStream out = fileObj.getContent().getOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = is.read(buf)) != -1; ) {
                out.write(buf, 0, readNum);
            }
            out.flush();
            out.close();
        }
    }

    /**
     * createFile 方法
     * <p>方法说明:创建文件,文件名必须紧跟在repository后面</p>
     * <p>创建文件之前会检查路劲,能逐级创建路劲,调用此方法,就不必再次调用createFolder了</p>
     *
     * @return void
     * @author Hyman
     * @date 2015-10-22
     */
    public static boolean createFile(String repository) throws IOException {
        FileObject fileObj;
        boolean b = false;
        if (createFolder(repository)) {
            fileObj = fileManager.resolveFile(repository);
            String fileName = fileObj.getName().getBaseName();
            fileObj.refresh();
            if (fileObj.isReadable() && fileObj.isFile()) {
                // 已经存在
                b = true;
            } else if (!fileObj.exists() && fileName.indexOf(".") > 0) {
                // 不存在
                fileObj.createFile();
                b = true;
            }

        }
        return b;
    }
}
