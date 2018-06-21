<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="width" description="宽度,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认自适应" required="false" type="java.lang.String" %>
<%@ attribute name="id" description="面板id" required="true" type="java.lang.String" %>

<%
	if(width == null || "".equals(width.trim())){
		/* width = "auto"; */
	}
	if(height == null || "".equals(height.trim())){
		/* height = "auto"; */
		/* jspContext.setAttribute("height", height);  */
	}
%>
<div id="${id}" class="easyui-layout" style="width:<%=width%>;height:<%=height%>;">
	<jsp:doBody></jsp:doBody>
</div>