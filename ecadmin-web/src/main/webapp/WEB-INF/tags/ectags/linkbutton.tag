<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="id" description="组件的ID属性" required="true" type="java.lang.String" %>
<%@ attribute name="text" description="按钮文字" required="true" type="java.lang.String" %>
<%@ attribute name="disabled" description="为true时禁用按钮" required="false" type="java.lang.String" %>
<%@ attribute name="toggle" description="为true时允许用户切换其状态是被选中还是未选择，可实现checkbox复选效果" required="false" type="java.lang.String" %>
<%@ attribute name="selected" description="定义按钮初始的选择状态，true为被选中，false为未选中" required="false" type="java.lang.String" %>
<%@ attribute name="group" description="指定相同组名称的按钮同属于一个组，可实现radio单选效果" required="false" type="java.lang.String" %>
<%@ attribute name="plain" description="为true时显示简洁效果,没有边框和背景" required="false" type="java.lang.String" %>
<%@ attribute name="icon" description="显示在按钮文字左侧的图标(16x16)的CSS类ID" required="false" type="java.lang.String" %>
<%@ attribute name="iconAlign" description="按钮图标位置。可用值有:left,right,top,bottom" required="false" type="java.lang.String" %>
<%@ attribute name="size" description="按钮大小。可用值有：'small','large'" required="false" type="java.lang.String" %>
<%@ attribute name="width" description="按钮宽度,如:28px,支持百分比.使用百分比的时候需要父级做参照" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="按钮高度,如:28px" required="false" type="java.lang.String" %>
<%@ attribute name="onclick" description="按钮点击事件,例如:onClick='fnOnClick()',在javascript中，function fnOnClick(){alert(111)}" required="false" type="java.lang.String" %>

<a 
<%if(id != null){%>
	id="<%=id %>"
<%}%>
<%if(disabled != null){%>
	disabled="<%=disabled %>"
<%}%>
<%if(toggle != null){%>
	toggle="<%=toggle %>"
<%}%>
<%if(selected != null){%>
	selected="<%=selected %>"
<%}%>
<%if(group != null){%>
	group="<%=group %>"
<%}%>
<%if(plain != null){%>
	plain="<%=plain %>"
<%}%>
<%if(icon != null){%>
	iconCls="<%=icon %>"
<%}%>
<%if(iconAlign != null){%>
	iconAlign="<%=iconAlign %>"
<%}%>
<%if(size != null){%>
	size="<%=size %>"
<%}%>
href="javascript:void(0);" class="easyui-linkbutton" style="
<%if(width != null){%>width:<%=width%>;<%}%>
<%if(height != null){%>height:<%=height%>;<%}%>"

<%if(onclick != null){%>
	onclick="<%=onclick %>"
<%}%>
><%=text %></a>
