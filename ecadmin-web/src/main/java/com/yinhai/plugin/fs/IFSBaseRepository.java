package com.yinhai.plugin.fs;

/**
* @package com.yinhai.plugin.fs
* <p>Title: IFSBaseRepository.java</p>
* <p>Description: 文件基础仓储</p>
* @author cjh
* @date 2016年7月18日 上午10:14:57
* @version 1.0
 */
public interface IFSBaseRepository {

	/**
	  * @package com.yinhai.plugin.fs
	  * @method get 方法 
	  * @describe <p>方法说明:根据文件业务ID,返回最新的文件元信息,不包含文件流</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月18日 上午10:11:02
	 */
	public FSBaseDomain get(String businessFileId);

	/**
	  * @package com.yinhai.plugin.fs
	  * @method save 方法 
	  * @describe <p>方法说明:自动根据文件业务ID保存文件,FSBaseDomain 文件流和文件名称不能为空</p>
	  * @return FSBaseDomain 
	  * @author cjh
	  * @date 2016年7月18日 上午10:12:17
	 */
	public FSBaseDomain storeFile(String businessFileId, FSBaseDomain fs);
	
	/**
	  * @package com.yinhai.plugin.fs
	  * @method delete 方法 
	  * @describe <p>方法说明:根据业务ID删除文件(删除所有!)</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月18日 上午10:14:01
	 */
	public void delete(String businessFileId);

	/**
	  * @package com.yinhai.plugin.fs
	  * @method removeByFileName 方法 
	  * @describe <p>方法说明: 文件的完整名称，例如：变形金刚.mp4 (注意:不建议使用该方法)</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月18日 上午10:14:35
	 */
	@Deprecated
	public void removeByFileName(String fileName);

}
