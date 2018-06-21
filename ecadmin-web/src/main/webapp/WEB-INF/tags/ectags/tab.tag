<%@ tag language="java" pageEncoding="UTF-8" %>
<%@tag description='tab页' display-name='tab' %>
<%@ attribute name="id" description="tabid" required="false" type="java.lang.String" %>
<%@ attribute name="width" description="宽度,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="高度,支持百分比,默认自适应" required="false" type="java.lang.String" %>
<%@ attribute name="title" description="选项卡面板的标题文本" required="false" type="java.lang.String" %>
<%@ attribute name="content" description="选项卡面板的内容" required="false" type="java.lang.String" %>
<%@ attribute name="href" description="从URL加载远程数据内容填充到选项卡面板,与content不要合用" required="false" type="java.lang.String" %>
<%@ attribute name="cache" description="如果为true，在'href'属性设置了有效值的时候缓存选项卡面板" required="false" type="java.lang.String" %>
<%@ attribute name="iconCls" description="定义了一个图标的CSS类ID显示到选项卡面板标题" required="false" type="java.lang.String" %>
<%@ attribute name="collapsible" description="如果为true，则允许选项卡摺叠" required="false" type="java.lang.String" %>
<%@ attribute name="closable" description="在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板" required="false" type="java.lang.String" %>
<%@ attribute name="selected" description="在设置为true的时候，选项卡面板会被选中" required="false" type="java.lang.String" %>
<%@ attribute name="cssStyle" description="tab的样式" required="false" type="java.lang.String" %>


<div 
<%if(id != null){%>
	id="<%=id %>"
<%}%>
<%if(title != null){%>
	title="<%=title %>"
<%}%>
<%if(href != null){%>
	href="<%=href %>"
<%}%>
<%if(cache != null){%>
	cache="<%=cache %>"
<%}%>
<%if(iconCls != null){%>
	iconCls="<%=iconCls %>"
<%}%>
<%if(collapsible != null){%>
	collapsible="<%=collapsible %>"
<%}%>
<%if(closable != null){%>
	closable="<%=closable %>"
<%}%>
<%if(selected != null){%>
	selected="<%=selected %>"
<%}%>

style="width:<%if(width!=null){%><%=width%><%}else{%>100%<%}%>;height:<%if(height!=null){%><%=height%><%}else{%>auto<%}%>;<%if(cssStyle!=null){%><%=cssStyle%><%}%>">
	<%if(content!=null){%>
		<%=content %>
	<%}%>
	<jsp:doBody></jsp:doBody>
</div>