<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="id" description="组件ID" required="true" type="java.lang.String" %>
<%@ attribute name="url" description="异步加载数据地址" required="false" type="java.lang.String" %>
<%@ attribute name="key" description="组件label标签" required="false" type="java.lang.String" %>
<%@ attribute name="checkbox" description="下拉框是否多选" required="false" type="java.lang.String" %>
<%@ attribute name="lines" description="定义是否显示树控件上的虚线" required="false" type="java.lang.String" %>
<%@ attribute name="dnd" description="定义是否启用拖拽功能" required="false" type="java.lang.String" %>
<%@ attribute name="data" description="设置节点数据，数组格式，参照easyui-tree标准格式" required="false" type="java.lang.String"%>
<%@ attribute name="queryParams" description="在请求远程数据的时候增加查询参数并发送到服务器." required="false" type="java.lang.String"%>
<%@ attribute name="onlyLeafCheck" description="定义是否只在末级节点之前显示复选框" required="false" type="java.lang.String" %>
<%@ attribute name="animate" description="定义节点在展开或折叠的时候是否显示动画效果。" required="false" type="java.lang.String" %>
<%@ attribute name="formatter" description="定义如何渲染节点的文本。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onClick" description="在用户点击一个节点的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeLoad" description="在请求加载远程数据之前触发，返回false可以取消加载操作。传入参数node,param" required="false" type="java.lang.String" %>
<%@ attribute name="onLoadSuccess" description="在数据加载成功以后触发。传入参数node,data" required="false" type="java.lang.String" %>
<%@ attribute name="onLoadError" description="在数据加载失败的时候触发，arguments参数和jQuery的$.ajax()函数里面的'error'回调函数的参数相同。传入参数arguments" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeExpand" description="在节点展开之前触发，返回false可以取消展开操作。。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onExpand" description="在节点展开的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeCollapse" description="在节点折叠之前触发，返回false可以取消折叠操作。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onCollapse" description="在节点折叠的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeCheck" description="在用户点击勾选复选框之前触发，返回false可以取消选择动作。传入参数node,checked" required="false" type="java.lang.String" %>
<%@ attribute name="onCheck" description="在用户点击勾选复选框的时候触发。传入参数node,checked" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeSelect" description="在用户选择一个节点之前触发，返回false可以取消选择动作。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onSelect" description="在用户选择节点的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeDrag" description="在开始拖动节点之前触发，返回false可以拒绝拖动。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onStartDrag" description="在开始拖动节点的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onStopDrag" description="在停止拖动节点的时候触发。传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onDragEnter" description="在拖动一个节点进入到某个目标节点并释放的时候触发，返回false可以拒绝拖动。传入参数target,source(源节点)" required="false" type="java.lang.String" %>
<%@ attribute name="onDragOver" description="在拖动一个节点经过某个目标节点并释放的时候触发，返回false可以拒绝拖动。传入参数target,source(源节点)" required="false" type="java.lang.String" %>
<%@ attribute name="onDragLeave" description="在拖动一个节点离开某个目标节点并释放的时候触发，返回false可以拒绝拖动。传入参数target,source(源节点)" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeDrop" description="在拖动一个节点之前触发，返回false可以拒绝拖动。传入参数target,source(源节点),point：表示哪一种拖动操作，可用值有：'append','top' 或 'bottom'" required="false" type="java.lang.String" %>
<%@ attribute name="onDrop" description="当节点位置被拖动时触发，返回false可以拒绝拖动。传入参数target,source(源节点),point：表示哪一种拖动操作，可用值有：'append','top' 或 'bottom'" required="false" type="java.lang.String" %>
<%@ attribute name="onBeforeEdit" description="在编辑节点之前触发,传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onAfterEdit" description="在编辑节点之后触发,传入参数node" required="false" type="java.lang.String" %>
<%@ attribute name="onCancelEdit" description="在取消编辑操作的时候触发,传入参数node" required="false" type="java.lang.String" %>
<ul id="<%=id %>" class="easyui-tree" style="height: 100%;" data-options="url:'<%=url %>'
	<%if(checkbox != null){%>
	,checkbox:<%=checkbox %>
	<%}%>
	<%if(lines != null){%>
	,lines:<%=lines %>
	<%}%>
	<%if(dnd != null){%>
	,dnd:<%=dnd %>
	<%}%>
	<%if(data != null){%>
	,data:eval(<%=data %>)
	<%}%>
	<%if(queryParams != null){%>
	,queryParams:eval(<%=queryParams %>)
	<%}%>
	<%if(onlyLeafCheck != null){%>
	,onlyLeafCheck:<%=onlyLeafCheck %>
	<%}%>
	<%if(animate != null){%>
	,animate:<%=animate %>
	<%}%>
	<%if(formatter != null){%>
	,formatter:eval(<%=formatter %>)
	<%}%>
	<%if(onClick != null){%>
	,onClick:eval(<%=onClick %>)
	<%}%>
	<%if(onBeforeLoad != null){%>
	,onBeforeLoad:eval(<%=onBeforeLoad %>)
	<%}%>
	<%if(onLoadSuccess != null){%>
	,onLoadSuccess:eval(<%=onLoadSuccess %>)
	<%}%>
	<%if(onLoadError != null){%>
	,onLoadError:eval(<%=onLoadError %>)
	<%}%>
	<%if(onBeforeExpand != null){%>
	,onBeforeExpand:eval(<%=onBeforeExpand %>)
	<%}%>
	<%if(onExpand != null){%>
	,onExpand:eval(<%=onExpand %>)
	<%}%>
	<%if(onBeforeCollapse != null){%>
	,onBeforeCollapse:eval(<%=onBeforeCollapse %>)
	<%}%>
	<%if(onCollapse != null){%>
	,onCollapse:eval(<%=onCollapse %>)
	<%}%>
	<%if(onBeforeCheck != null){%>
	,onBeforeCheck:eval(<%=onBeforeCheck %>)
	<%}%>
	<%if(onCheck != null){%>
	,onCheck:eval(<%=onCheck %>)
	<%}%>
	<%if(onBeforeSelect != null){%>
	,onBeforeSelect:eval(<%=onBeforeSelect %>)
	<%}%>
	<%if(onSelect != null){%>
	,onSelect:eval(<%=onSelect %>)
	<%}%>
	<%if(onBeforeDrag != null){%>
	,onBeforeDrag:eval(<%=onBeforeDrag %>)
	<%}%>
	<%if(onStartDrag != null){%>
	,onStartDrag:eval(<%=onStartDrag %>)
	<%}%>
	<%if(onStopDrag != null){%>
	,onStopDrag:eval(<%=onStopDrag %>)
	<%}%>
	<%if(onDragEnter != null){%>
	,onDragEnter:eval(<%=onDragEnter %>)
	<%}%>
	<%if(onDragOver != null){%>
	,onDragOver:eval(<%=onDragOver %>)
	<%}%>
	<%if(onDragLeave != null){%>
	,onDragLeave:eval(<%=onDragLeave %>)
	<%}%>
	<%if(onBeforeDrop != null){%>
	,onBeforeDrop:eval(<%=onBeforeDrop %>)
	<%}%>
	<%if(onDrop != null){%>
	,onDrop:eval(<%=onDrop %>)
	<%}%>
	<%if(onBeforeEdit != null){%>
	,onBeforeEdit:eval(<%=onBeforeEdit %>)
	<%}%>
	<%if(onAfterEdit != null){%>
	,onAfterEdit:eval(<%=onAfterEdit %>)
	<%}%>
	<%if(onCancelEdit != null){%>
	,onCancelEdit:eval(<%=onCancelEdit %>)
	<%}%>
	">
</ul>