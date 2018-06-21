<%@ tag language="java" pageEncoding="UTF-8" %>
<%@tag description='进度条' display-name='progressbar' %>
<%@ attribute name="id" description="进度条id" required="true" type="java.lang.String" %>
<%@ attribute name="width" description="宽度,支持百分比,默认100%" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认22px" required="false" type="java.lang.String" %>
<%@ attribute name="value" description="The percentage value.百分比的值,如60,60.5..." required="false" type="java.lang.String" %>
<%@ attribute name="text" description="The text template to be displayed on component.默认{value}%" required="false" type="java.lang.String" %>
<%@ attribute name="backgroundColor" description="进度条背景颜色,全部的的背景色,传入:#FFFFFF...,默认#F2F2F2" required="false" type="java.lang.String" %>
<%@ attribute name="valueColor" description="进度条value部分的背景颜色" required="false" type="java.lang.String" %>
<%@ attribute name="border" description="进度条边框,默认没有边框,传入:1px solid #ddd..." required="false" type="java.lang.String" %>

<%
	if(width == null || "".equals(width.trim())){
		width = "100%";
	}
	if(backgroundColor == null || "".equals(backgroundColor.trim())){
		backgroundColor = "#F2F2F2";
	}
	if(border == null || "".equals(border.trim())){
		border = "0px";
	}
%>

<div class="easyui-progressbar"
<%if(id != null){%>
	id="<%=id %>"
<%}%>
<%if(value != null){%>
	value="<%=value %>"
<%}%>
<%if(text != null){%>
	text="<%=text %>"
<%}%>
style="<%if(width != null){%>width:<%=width %>;<%}%><%if(height != null){%>height:<%=height %>;<%}%>
	background-color:<%=backgroundColor %>;border:<%=border%>;">
</div>

<%if(valueColor != null && !"".equals(valueColor.trim())){%>
	<script>
		$(function(){
			$('#<%=id%>').find('.progressbar-value .progressbar-text').css("background-color","<%=valueColor%>");
		});
	</script>
<%}%>