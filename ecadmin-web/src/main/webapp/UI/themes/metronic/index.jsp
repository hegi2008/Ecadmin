<%@ page import="com.yinhai.ec.base.util.HYConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<!--<![endif]-->
<head>
    <%
        if(request.getSession(false).getAttribute(HYConst.SESSION_USER) == null){
            response.sendRedirect("login");
        }
    %>
    <title>首页</title>
    <meta name="keywords" content="bootstrap框架,响应式后台">
    <meta name="description" content="metronic主题界面,响应式主题界面">
    <%@ include file="head.inc.jsp" %>
    <link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/layout.min.css" rel="stylesheet">
    <link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/themes/default.min.css" rel="stylesheet">
    <link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/custom.min.css" rel="stylesheet">
</head>
<body class="page-container-bg-solid">
    <div class="page-wrapper">
        <%@include file="pages/header.jsp"%>
        <div class="page-wrapper-row full-height">
            <div class="page-wrapper-middle">
                <div class="page-container">
                    <div class="page-content-wrapper">
                        <div class="page-head">
                            <div class="container">
                                <!-- BEGIN PAGE TITLE -->
                                <div class="page-title">
                                    <%--<h1>Admin Dashboard 2
                                        <small>statistics, charts, recent events and reports</small>
                                    </h1>--%>
                                    <ul class="page-breadcrumb breadcrumb" style="padding-bottom: 0px;">
                                        <li>
                                            <a href="index">Home</a>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                        <li>
                                            <span>Dashboard</span>
                                        </li>
                                    </ul>
                                </div>
                                <!-- END PAGE TITLE -->
                                <!-- BEGIN PAGE TOOLBAR -->
                                <div class="page-toolbar">
                                    <!-- BEGIN THEME PANEL -->
                                    <%--<div class="btn-group btn-theme-panel">
                                        <a href="javascript:;" class="btn dropdown-toggle" data-toggle="dropdown">
                                            <i class="icon-settings"></i>
                                        </a>
                                        <div class="dropdown-menu theme-panel pull-right dropdown-custom hold-on-click">

                                        </div>
                                    </div>--%>
                                    <!-- END THEME PANEL -->
                                </div>
                                <!-- END PAGE TOOLBAR -->
                            </div>
                        </div>
                        <!-- iframe开始 -->
                        <div class="page-content">
                            <iframe class="J_iframe" name="iframe0" width="100%" src="<%=basePath%>UI/welcome.html" frameborder="0"></iframe>
                        </div>
                        <!-- iframe结束 -->
                    </div>
                </div>
            </div>
        </div>
        <%@include file="pages/foot.jsp"%>
    </div>
    <!--[if lt IE 9]>
    <script src="/UI/js/respond.min.js"></script>
    <script src="/UI/js/excanvas.min.js"></script>
    <script src="/UI/js/ie8.fix.min.js"></script>
    <![endif]-->
    <%@include file="basic.js.inc.jsp"%>
    <script src="/UI/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/UI/themes/metronic/js/WEBAPP.js"></script>
</body>
</html>
