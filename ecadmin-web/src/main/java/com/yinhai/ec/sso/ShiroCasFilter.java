package com.yinhai.ec.sso;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroCasFilter extends AuthenticatingFilter {

	public ShiroCasFilter() {
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ticket = httpRequest.getParameter(TICKET_PARAMETER);
		return new CasToken(ticket);
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return executeLogin(request, response);
	}

	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		AuthenticationToken token;
		token = createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		Subject subject;
		subject = getSubject(request, response);
		try {
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			//logger.error("sso cas login faild,{}", e);
			return onLoginFailure(token, e, request, response);
		}
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}

	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		issueSuccessRedirect(request, response);
		return false;
	}

	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
			ServletResponse response) {
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated() || subject.isRemembered())
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				logger.error("Cannot redirect to the default success url", e);
			}
		else
			try {
				WebUtils.issueRedirect(request, response, failureUrl);
			} catch (IOException e) {
				logger.error("Cannot redirect to failure url : {}", failureUrl, e);
			}
		return false;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	private static Logger logger = LoggerFactory.getLogger(ShiroCasFilter.class);
	private static final String TICKET_PARAMETER = "ticket";
	private String failureUrl;

}