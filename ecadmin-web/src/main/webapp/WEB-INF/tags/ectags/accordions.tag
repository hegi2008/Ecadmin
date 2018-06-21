<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag description='accordion,一组内部可伸缩的panel' display-name='accordion' %>
<%@ attribute name="id" description="accordion的id,组ID" required="false" type="java.lang.String" %>
<%@ attribute name="width" description="宽度,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认自适应" required="false" type="java.lang.String" %>
<%@ attribute name="fit" description="设置为true时，选项卡的大小将铺满它所在的容器。" required="false" type="java.lang.String" %>
<%@ attribute name="border" description="设置为true时，显示选项卡容器边框。" required="false" type="java.lang.String" %>
<%@ attribute name="animate" description="定义在展开和折叠的时候是否显示动画效果。" required="false" type="java.lang.String" %>
<%@ attribute name="multiple" description="如果为true时，同时展开多个面板,默认false" required="false" type="java.lang.String" %>

<div class="easyui-accordion" style="width:<%if(width!=null){%><%=width%><%}else{%>auto<%}%>;height:<%if(height!=null){%><%=height%><%}else{%>auto<%}%>;"
<%if(id != null){%>
	id="<%=id %>"
<%}%>
data-options="
<%if(fit != null){%>
	fit:<%=fit %>,
<%}%>
<%if(border != null){%>
	border:<%=border %>,
<%}%>
<%if(animate != null){%>
	animate:<%=animate %>,
<%}%>
<%if(multiple != null){%>
	multiple:<%=multiple %>,
<%}%>
">
	<jsp:doBody></jsp:doBody>
</div>