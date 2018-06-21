<%@ page import="com.yinhai.ec.base.util.HYConst" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basePath", basePath);
%>
<%
	String systemTheme = (String) this.getServletConfig().getServletContext().getAttribute(HYConst.SYSTEM_THEME);
	request.setAttribute("systemTheme", systemTheme);
%>
<meta http-equiv="pragma" content="pragma"/>
<meta http-equiv="cache-control" content="public,max-age=86400"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<link href="<%=basePath%>UI/favicon.ico" rel="shortcut icon"/>
<link href="<%=basePath%>UI/themes/metronic/css/fonts.css" rel="stylesheet">
<link href="<%=basePath%>UI/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/plugins/font-awesome/css/font-awesome.min93e3.css" rel="stylesheet">
<link href="<%=basePath%>UI/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/themes/metronic/css/components.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/themes/metronic/css/plugins.min.css" rel="stylesheet">
<%--<link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/layout.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/themes/default.min.css" rel="stylesheet">
<link href="<%=basePath%>UI/themes/metronic/layouts/layout3/css/custom.min.css" rel="stylesheet">--%>
<script src="<%=basePath%>UI/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	if(typeof Base === 'undefined'){
		var Base = {};
	}
	Base.globvar = {
		contentPath:'<%=path%>',
		basePath:'<%=basePath%>',
		systemTheme:'<%=systemTheme%>'
	}
</script>