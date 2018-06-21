<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.yinhai.ec.base.util.SystemPropertiesManager" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <!-- 跳转主题页面 -->
    <%
        WebApplicationContext context = (WebApplicationContext)this.getServletConfig().getServletContext()
        .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        SystemPropertiesManager spm = (SystemPropertiesManager)context.getBean("systemPropertiesManager");
        String systemTheme = spm.getSystemTheme();
        response.sendRedirect("UI/themes/"+systemTheme+"/index.jsp");
    %>
  </head>
  <body>
</body>
</html>