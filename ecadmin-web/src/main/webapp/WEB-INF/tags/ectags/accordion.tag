<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag description='accordion,一组内部可伸缩的panel' display-name='accordion' %>
<%@ attribute name="id" description="accordion的id,分类ID" required="false" type="java.lang.String" %>
<%@ attribute name="cssStyle" description="分类容器style样式:padding:5px;border:none;" required="false" type="java.lang.String" %>
<%@ attribute name="title" description="选项卡面板的标题文本" required="false" type="java.lang.String" %>
<%@ attribute name="content" description="选项卡面板的内容" required="false" type="java.lang.String" %>
<%@ attribute name="iconCls" description="定义了一个图标的CSS类ID显示到选项卡面板标题" required="false" type="java.lang.String" %>
<%@ attribute name="selected" description="在设置为true的时候，选项卡面板会被选中" required="false" type="java.lang.String" %>
<%@ attribute name="href" description="从URL加载远程数据内容填充到选项卡面板,与content不要合用" required="false" type="java.lang.String" %>
<%@ attribute name="collapsed" description="是否展开,false并且collapsible也是false将固定容器" required="false" type="java.lang.String" %>
<%@ attribute name="collapsible" description="是否支持展开,false将不显示展开按钮" required="false" type="java.lang.String" %>

<div
<%if(cssStyle != null){
	if(!cssStyle.endsWith(";")){
		cssStyle += ";";
}%>
	style="<%=cssStyle %>"
<%}%>
<%if(id != null){%>
	id="<%=id %>"
<%}%>
<%if(title != null){%>
	title="<%=title %>"
<%}%>
data-options="
<%if(iconCls != null){%>
	iconCls:'<%=iconCls %>',
<%}%>
<%if(selected != null){%>
	selected:<%=selected %>,
<%}%>
<%if(href != null){%>
	href:'<%=href %>',
<%}%>
<%if(collapsed != null){%>
	collapsed:<%=collapsed %>,
<%}%>
<%if(collapsible != null){%>
	collapsible:<%=collapsible %>,
<%}%>
">
	<%if(content!=null){%>
		<%=content %>
	<%}%>
	<jsp:doBody></jsp:doBody>
</div>