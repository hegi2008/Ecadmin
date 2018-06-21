<%@ tag language="java" pageEncoding="UTF-8" %>
<%@tag description='容器的按钮,要放在容器tabs的外面才能生效,再在tools内部添加linkbutton即可' display-name='tabtools' %>
<%@ attribute name="tabsid" description="容器id,按钮将放在哪个tabs内" required="true" type="java.lang.String" %>

<div id="<%=tabsid %>_tools">
	<jsp:doBody></jsp:doBody>
</div>
<script type="text/javascript">
$(function(){
	$('#<%=tabsid%>').tabs({
		tools:'#<%=tabsid%>_tools'
	});
});
</script>