<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="java.lang.reflect.Method"%>
<%@ tag import="java.beans.PropertyDescriptor"%>
<%
	PropertyDescriptor pd = new PropertyDescriptor("id", getParent().getClass());
	Method method = pd.getReadMethod();
	String id = (String) method.invoke(getParent());
	jspContext.setAttribute("id", id);
%>
<%@ attribute name="width" description="宽度,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认自适应" required="false" type="java.lang.String" %>
<%@ attribute name="region" description="定义布局面板位置，可用的值有：north, south, east, west, center" required="true" type="java.lang.String" %>
<%@ attribute name="title" description="布局面板标题文本" required="false" type="java.lang.String" %>
<%@ attribute name="split" description="为true时用户可以通过分割栏改变面板大小，默认false" required="false" type="java.lang.Boolean" %>
<%@ attribute name="border" description="为true时显示布局面板边框，默认true" required="false" type="java.lang.Boolean" %>
<%@ attribute name="collapsible" description="定义是否显示折叠按钮，默认false（该属性自1.3.3版开始可用）" required="false" type="java.lang.Boolean" %>
<%@ attribute name="minWidth" description="最小面板宽度，默认10px（该属性自1.3.3版开始可用）" required="false" type="java.lang.String" %>
<%@ attribute name="minHeight" description="最小面板高度，默认10px（该属性自1.3.3版开始可用）" required="false" type="java.lang.String" %>
<%@ attribute name="maxWidth" description="最大面板宽度，默认10000px（该属性自1.3.3版开始可用）" required="false" type="java.lang.String" %>
<%@ attribute name="maxHeight" description="最大面板高度，默认10000px（该属性自1.3.3版开始可用）" required="false" type="java.lang.String" %>
<%@ attribute name="headPosition" description="header位置,left;center;right" required="false" type="java.lang.String" %>
<%@ attribute name="headBgColor" description="header背景色如:#DDDDDD" required="false" type="java.lang.String" %>

<%
	if(height == null || "".equals(height.trim())){
		/* height = "auto"; */
		/* jspContext.setAttribute("height", height); */
	}
	if(split == null ){
		jspContext.setAttribute("split", false);
	}
	if(border == null ){
		jspContext.setAttribute("border", true);
	}
	if(collapsible == null ){
		jspContext.setAttribute("collapsible", false);
	}
	if(minWidth == null || "".equals(minWidth.trim())){
		jspContext.setAttribute("minWidth", "10px");
	}
	if(minHeight == null || "".equals(minHeight.trim())){
		jspContext.setAttribute("minHeight", "10px");
	}
	if(maxWidth == null || "".equals(maxWidth.trim())){
		jspContext.setAttribute("maxWidth", "10000px");
	}
	if(maxHeight == null || "".equals(maxHeight.trim())){
		jspContext.setAttribute("maxHeight", "10000px");
	}
%>

<div data-options="region:'${region}',split:${split}" title="${title }" border="${border }" style="height:${height};width:${width}" collapsible="${collapsible}" minWidth="${minWidth}" minHeight="${minHeight}" maxWidth="${maxWidth}" maxHeight="${maxHeight}">
	<jsp:doBody></jsp:doBody>
</div>
<script type="text/javascript">
	$(function(){
		<%if(headPosition != null){%>
			var header = $('#<%=id%>').find('.layout-panel-<%=region%> .panel-header .panel-title');
			if(header.length == 1){
				$(header).css('text-align','<%=headPosition%>');
			}
		<%}%>
		<%if(headBgColor != null){%>
			var header = $('#<%=id%>').find('.layout-panel-<%=region%> .panel-header');
			if(header.length == 1){
				$(header).css('background-color','<%=headBgColor%>');
			}
		<%}%>
	});
</script>