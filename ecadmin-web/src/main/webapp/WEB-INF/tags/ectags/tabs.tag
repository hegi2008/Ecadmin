<%@ tag language="java" pageEncoding="UTF-8" %>
<%@tag description='tab页的容器' display-name='tabs' %>
<%@ attribute name="id" description="容器id" required="true" type="java.lang.String" %>
<%@ attribute name="width" description="宽度,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认自适应" required="false" type="java.lang.String" %>
<%@ attribute name="plain" description="设置为true时，将不显示控制面板背景。" required="false" type="java.lang.String" %>
<%@ attribute name="fit" description="设置为true时，选项卡的大小将铺满它所在的容器。" required="false" type="java.lang.String" %>
<%@ attribute name="border" description="设置为true时，显示选项卡容器边框。" required="false" type="java.lang.String" %>
<%@ attribute name="toolPosition" description="工具栏位置。可用值：'left','right',设置tabtool必须给tab设置ID" required="false" type="java.lang.String" %>
<%@ attribute name="tabPosition" description="选项卡位置。可用值：'top','bottom','left','right'" required="false" type="java.lang.String" %>
<%@ attribute name="headerWidth" description="选项卡标题宽度，在tabPosition属性设置为'left'或'right'的时候才有效" required="false" type="java.lang.String" %>
<%@ attribute name="tabWidth" description="标签条的宽度,默认auto,如:100" required="false" type="java.lang.String" %>
<%@ attribute name="tabHeight" description="标签条的高度,默认27,如:100" required="false" type="java.lang.String" %>
<%@ attribute name="showHeader" description="设置为true时，显示tab页标题,默认true" required="false" type="java.lang.String" %>
<%@ attribute name="onSelect" description="用户在选择一个选项卡面板的时候触发。传入参数title,index,如:ontabselect;" required="false" type="java.lang.String" %>
<%@ attribute name="cssStyle" description="tabs的样式" required="false" type="java.lang.String" %>


<div id="${id}" class="easyui-tabs" style="width:<%if(width!=null){%><%=width%><%}else{%>auto<%}%>;height:<%if(height!=null){%><%=height%><%}else{%>100%<%}%>;<%if(cssStyle!=null){%><%=cssStyle%><%}%>"
<%if(plain != null){%>
	plain="<%=plain %>"
<%}%>
<%if(fit != null){%>
	fit="<%=fit %>"
<%}%>
<%if(border != null){%>
	border="<%=border %>"
<%}%>
<%if(toolPosition != null){%>
	toolPosition="<%=toolPosition %>"
<%}%>
<%if(tabPosition != null){%>
	tabPosition="<%=tabPosition %>"
<%}%>
<%if(headerWidth != null){%>
	headerWidth="<%=headerWidth %>"
<%}%>
<%if(tabWidth != null){%>
	tabWidth="<%=tabWidth %>"
<%}%>
<%if(tabHeight != null){%>
	tabHeight="<%=tabHeight %>"
<%}%>
<%if(showHeader != null){%>
	showHeader="<%=showHeader %>"
<%}%>
>
	<jsp:doBody></jsp:doBody>
</div>
<script type="text/javascript">
$(function(){
	$('#<%=id%>').tabs({
		<%if(onSelect!=null){%>
		onSelect:function(title,index){
			var tabid = '${id}';
			eval('<%=onSelect%>')(title,index,tabid);
		}
		<%}%>
	});
	var hh = $('#${id} .tabs-wrap .tabs').height()+'px';
	$('#${id} .tabs-wrap .tabs .tabs-inner').css({
		'height':hh,
		'line-height':hh
	});
});
</script>