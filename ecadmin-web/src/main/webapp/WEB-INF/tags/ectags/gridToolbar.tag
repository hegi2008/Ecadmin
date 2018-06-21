<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="java.lang.reflect.Method"%>
<%@ tag import="java.beans.PropertyDescriptor"%>
<%
	PropertyDescriptor pd = new PropertyDescriptor("id", getParent().getClass());
	Method method = pd.getReadMethod();
	String id = (String) method.invoke(getParent());
	jspContext.setAttribute("id", id);
%>
<%@tag description='表格的自定义按钮,放在表格内部,与gridItem平级,再在内部添加linkbutton即可' display-name='toolbar' %>
<script>
	${id}_toolbar = '#${id}_toolbar';
</script>
<div id="${id}_toolbar" style="padding:5px;">
    <jsp:doBody></jsp:doBody>
</div>