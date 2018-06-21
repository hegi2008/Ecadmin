package com.yinhai.ec.base.shiro.realm;  

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.yinhai.ec.base.util.EndecryptUtils;

/**
* @package com.yinhai.ec.base.shiro.realm
* <p>Title: BaseCredentialsMatcher.java</p>
* <p>Description: 自定义密码比对验证</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-4 下午1:52:59
* @version 1.0
 */
public class BaseCredentialsMatcher extends SimpleCredentialsMatcher{
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		// 用户输入的用户名 密码
		String username = (String)token.getPrincipal();
	    String password = new String((char[])token.getCredentials());
	    String pwd_crypt = EndecryptUtils.md5Password(username, password);
	    Object accountCredentials = getCredentials(info);
		return equals(pwd_crypt, accountCredentials);
	}
}
 