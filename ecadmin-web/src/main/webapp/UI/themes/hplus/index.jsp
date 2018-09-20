<%@page import="com.yinhai.ec.base.util.HYConst"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
  	<%
  		if(request.getSession(false).getAttribute(HYConst.SESSION_USER) == null){
  			response.sendRedirect("login");
  		}
  	%>
    <title>主页</title>
	<meta name="keywords" content="bootstrap框架,响应式后台">
	<meta name="description" content="HY是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<%@ include file="head.inc.jsp" %>
  </head>
  <body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element text-center">
                        	<c:if test="${!empty user.userHeaderPic}">
                        		<span><img alt="头像" id="userHeaderPic" class="img-circle" width="64px" height="64px" src="${imageUrl}${user.userHeaderPic}" /></span>
							</c:if> 
							 <c:if test="${empty user.userHeaderPic}">
								<span><img alt="image" id="userHeaderPic" class="img-circle" width="64px" height="64px" src="<%=basePath%>UI/themes/hplus/img/user.jpg" /></span>
							</c:if>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               		<span class="block m-t-xs"><strong class="font-bold">${user.userName}</strong></span>
                                	<span class="text-muted text-xs block">${user.userLogin}<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li>
                                	<a class="J_menuItem" href="<%=basePath%>UI/themes/hplus/userHeaderPicture.jsp">修改头像</a>
                                </li>
                                <li>
                                	<a class="J_menuItem" href="<%=basePath%>index/viewUserInfo">个人资料</a>
                                </li>
                                <li>
                                	<a href="javascript:void(0);" onclick="getLocked();">锁定屏幕</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                	<a href="logout">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">HY
                        </div>
                    </li>
                    <!-- 菜单开始 -->
                    <c:forEach items="${allMenus}" var="menu">
                    	<li>
	                    	<a class="J_menuItem" href="${menu.menu_url}">
	                    		<c:if test="${empty menu.menu_icon}">
	                    			<i class="fa fa-menu"></i>
	                    		</c:if>
	                    		<c:if test="${not empty menu.menu_icon}">
	                    			<i class="fa fa-${menu.menu_icon}"></i>
	                    		</c:if>
	                    		<span class="nav-label">${menu.menu_name}</span>
	                    		<c:if test="${not empty menu.child}">
	                    			<span class="fa arrow"></span>
	                    		</c:if>
	                    	</a>
	                    	<c:if test="${not empty menu.child}">
		                    	<ul class="nav nav-second-level">
		                    	<c:forEach items="${menu.child}" var="m">
		                    		<li><a class="J_menuItem" href="${m.menu_url}">${m.menu_name}</a></li>
		                    	</c:forEach>
		                    	</ul>
	                    	</c:if>
	                    </li>
                    </c:forEach>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                    	<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <!-- <form role="search" class="navbar-form-custom" method="post" action="http://www.zi-han.net/theme/hplus/search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form> -->
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li class="dropdown">
                            <!--暂不支持消息
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                            </a>-->
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a href="mailbox.html">
                                        <div>
                                            <i class="fa fa-envelope fa-fw"></i> 您有16条未读消息
                                            <span class="pull-right text-muted small">4分钟前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-qq fa-fw"></i> 3条新回复
                                            <span class="pull-right text-muted small">12分钟钱</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="UI/notifications.html">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="welcome.html">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive">
                        	<a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll">
                        	<a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther">
                        	<a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="<%=basePath%>UI/welcome.html" frameborder="0" data-id="welcome.html" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2016-2020 <a href="http://www.yinhai.com" target="_blank">贵州医美达</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
        <div id="right-sidebar">
            <div class="sidebar-container">
                <ul class="nav nav-tabs navs-3">
                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div id="tab-1" class="tab-pane active">
                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                        </div>
                        <div class="skin-setttings">
                            <div class="title">主题设置</div>
                            <div class="setings-item">
                                <span>收起左侧菜单</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                        <label class="onoffswitch-label" for="collapsemenu">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定顶部</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                        <label class="onoffswitch-label" for="fixednavbar">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>
                        			固定宽度
                    			</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                        <label class="onoffswitch-label" for="boxedlayout">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="title">皮肤选择</div>
                            <div class="setings-item default-skin nb">
                                <span class="skin-name ">
		                         <a href="#" class="s-skin-0">
		                            	 默认皮肤
		                         </a>
                    			</span>
                            </div>
                            <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        			<a href="#" class="s-skin-1">
                          				 蓝色主题
                        			</a>
                    			</span>
                            </div>
                            <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        		<a href="#" class="s-skin-3">黄色/紫色主题</a>
                    			</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="<%=basePath%>UI/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>UI/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="<%=basePath%>UI/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="<%=basePath%>UI/plugins/layer/layer.min.js"></script>
    <script src="<%=basePath%>UI/themes/hplus/js/hy.index.min.js"></script>
    <script src="<%=basePath%>UI/themes/hplus/js/contabs.min.js"></script>
    <script src="<%=basePath%>UI/plugins/pace/pace.min.js"></script>
    <script src="<%=basePath%>UI/plugins/moment/moment.min.js"></script>
    <script src="<%=basePath%>UI/js/api.base.js"></script>
</body>
</html>