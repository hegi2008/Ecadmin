<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="header navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-inner">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
					<span class="sr-only">切换导航</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
		      	</button>
				<a class="navbar-brand" href="#"> 
					<img src="statics/image/index/logo.png" alt="logo"> 
				</a>
			</div>
			<div id="example-navbar-collapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="javascript:void(0);" class="dropdown-toggle headerRealTime">
							<span id="headerRealTime"></span>
							<span class="fa fa-clock-o"></span>
						</a>
					</li>
					<li class="dropdown notification">
						<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-bell gl_lg" aria-hidden="true"></span>
							<span class="badge">6</span>
						</a>
					</li>
					<li class="dropdown notification">
						<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-tasks gl_lg" aria-hidden="true"></span>
							<span class="badge">6</span>
						</a>
					</li>
					<li class="dropdown user">
						<a href="#" id="userHeaderPic" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<c:if test="${!empty user.userHeaderPic}">
								<img alt="" src="${imageUrl}${user.userHeaderPic}">
							</c:if> 
							 <c:if test="${empty user.userHeaderPic}">
								<img alt="" src="statics/image/index/avatar1_small.jpg">
							</c:if> 
							<span class="username">${user.userName}</span>
							<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="javascript:void(0);" onclick="userInfoEdit();"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;我的信息</a>
							</li>
							<li>
								<a href="javascript:void(0);" onclick="systemSettings();"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>&nbsp;系统配置</a>
							</li>
							<!-- <li>
								<a href="javascript:void(0);"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;我的日历</a>
							</li>
							<li>
								<a href="javascript:void(0);"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;我的邮件</a>
							</li>
							<li>
								<a href="javascript:void(0);"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>&nbsp;我的任务</a>
							</li> -->
							<li class="divider"></li>
							<li>
								<a href="javascript:void(0);" onclick="getLocked();"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span>&nbsp;锁定屏幕</a>
							</li>
							<li>
								<a href="logout"><i class="fa fa-key" aria-hidden="true"></i>&nbsp;注销登陆</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>