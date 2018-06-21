<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="java.util.Random"%>
<%@tag import="com.yinhai.sysframework.util.TagUtil"%>
<%--@doc--%>
<%@tag description='生成form元素' display-name='form' %>
<%@attribute description='组件id页面唯一' name='id' type='java.lang.String' %>
<%@attribute name="novalidate" description="定义是否验证表单的字段，true:验证,false:不验证。默认false" type="java.lang.String" %>
<%@attribute name="ajax" description="定义是否使用ajax提交表单，true:使用,false:不使用。默认true" type="java.lang.String" %>
<%
if ((id == null || id.length() == 0)) {
	Random random = new Random();
	int nextInt = random.nextInt();
	nextInt = nextInt == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(nextInt);
	id = "ecform_" + String.valueOf(nextInt);
}
%>
<form id="<%=id %>" method="post" class="easyui-form" 
	
>
	<jsp:doBody/>
</form>