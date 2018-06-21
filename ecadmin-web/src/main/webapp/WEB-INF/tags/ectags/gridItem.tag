<%@tag import="com.yinhai.ec.base.cache.ehcache.AppCodeTemplate"%>
<%@tag language="java" pageEncoding="UTF-8" %>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Locale"%>
<%@ tag import="java.lang.reflect.Method"%>
<%@ tag import="java.beans.PropertyDescriptor"%>
<%
	PropertyDescriptor pd = new PropertyDescriptor("id", getParent().getClass());
	Method method = pd.getReadMethod();
	String id = (String) method.invoke(getParent());
	jspContext.setAttribute("id", id);
%>

<%@ attribute name="itemId" description="itemId,表格内唯一,对应数据的key" required="true" type="java.lang.String" %>
<%@ attribute name="itemName" description="itemName,列标题名称" required="true" type="java.lang.String" %>
<%@ attribute name="width" description="列宽度,如100,支持百分比" required="false" type="java.lang.String" %>
<%@ attribute name="align" description="列内容位置,left,right,center 默认center" required="false" type="java.lang.String" %>
<%@ attribute name="halign" description="列标题位置,left,right,center 默认center" required="false" type="java.lang.String" %>
<%@ attribute name="hidden" description="是否隐藏列" required="false" type="java.lang.String" %>
<%@ attribute name="rowspan" description="指明将占用多少行单元格（合并行）" required="false" type="java.lang.String" %>
<%@ attribute name="colspan" description="指明将占用多少列单元格（合并列）" required="false" type="java.lang.String" %>
<%@ attribute name="formatter" description="单元格formatter(格式化器)函数，带3个参数：value：字段值。rowData：行记录数据。rowIndex: 行索引.只传方法名称如columnformatter" required="false" type="java.lang.String" %>
<%@ attribute name="styler" description="单元格styler(样式)函数，返回如'background:red'这样的自定义单元格样式字符串。该函数带3个参数：value：字段值。rowData：行记录数据。rowIndex: 行索引。只传方法名称如columnStyle" required="false" type="java.lang.String" %>
<%@ attribute name="sortable" description="如果为true，则允许列使用排序。" required="false" type="java.lang.String" %>
<%@ attribute name="editorType" description="字符串，该编辑类型可以使用的类型有：text,textarea,checkbox,numberbox,validatebox,datebox,combobox,combotree.默认是text" required="false" type="java.lang.String" %>
<%@ attribute name="editorOptions" description="object,该编辑器属性对应于编辑类型.配合type的例子:editor:{type:'checkbox',options:{on:'P',off:''}},只需要填入on:'P',off:''即可" required="false" type="java.lang.String" %>
<%@ attribute name='collection' description='设置列的码表转换'  type='java.lang.String' %>
<%@ attribute name='collectionData' description='手动设置Collection的值，当存在collection及collectionData时，优先选择collectionData' type='java.lang.String' %>
<%@ attribute name="showDetail" description="鼠标放上是否显示详细内容,默认false" required="false" type="java.lang.Boolean"%>
<%-- <%@ attribute name="icon" description="设置当前列为显示图标" required="false" type="java.lang.String"%>
<%@ attribute name="iconClick" description="图标点击的方法，带1个参数：rowIndex: 行索引.只传方法名称如fhiconclick" required="false" type="java.lang.String" %> --%>
<%
	if(align == null || "".equals(align.trim())){
		align = "center";
		jspContext.setAttribute("align", align);
	}
	if(halign == null || "".equals(halign.trim())){
		halign = "center";
		jspContext.setAttribute("halign", halign);
	}
	if(width == null || "".equals(width.trim())){
		width = "100";
		jspContext.setAttribute("width", width);
	}
	if(collection != null){
		collection = collection.toUpperCase(Locale.ENGLISH);
	}
 %>
<%if(collection!=null){
    if(collectionData == null){
		collectionData = AppCodeTemplate.getInstance().getCodeListJson(collection);
		jspContext.setAttribute("collectionData", collectionData);
    }
}%>
<script type="text/javascript">
	$(function(){
		var ${itemId}_c = {};
		${itemId}_c.field = '${itemId}';
		${itemId}_c.title = '${itemName}';
		<%if(width.indexOf("%") > -1){%>
			${itemId}_c.width = '${width}';
		<%}else{%>
			${itemId}_c.width = ${width};
		<%}%>
		${itemId}_c.align = '${align}';
		${itemId}_c.halign = '${halign}';
		${itemId}_c.resizable = true;
		<%if(hidden != null){%>
			${itemId}_c.hidden = ${hidden};
		<%}%>
		<%if(rowspan != null){%>
			${itemId}_c.rowspan = ${rowspan};
		<%}%>
		<%if(colspan != null){%>
			${itemId}_c.colspan = ${colspan};
		<%}%>
		<%if(formatter != null){%>
			${itemId}_c.formatter = eval('${formatter}');
		<%}%>
		<%if(styler != null){%>
			${itemId}_c.styler = eval('${styler}');
		<%}%>
		<%if(sortable != null){%>
			${itemId}_c.sortable = ${sortable};
		<%}%>
		<%if(editorType != null){%>
			${itemId}_c.editor = {
				type:'${editorType}',
				options:{${editorOptions}}
			};
		<%}%>
		<%-- <%if(icon!=null && !"".equals(icon.trim())){%>
			${itemId}_c.fixed = true;
			${itemId}_c.formatter = function(value,rowData,rowIndex){
				var desc = '<i class="fa ${icon}"><span>';
				<%if(iconClick != null && !"".equals(iconClick.trim())){%>
					desc = "<span title='单击' class='grid-icon ${icon}' onclick='${iconClick}("+rowIndex+")'><span>";
				<%}%>
				<%if(formatter != null){%>
					desc = eval('${formatter}')(value,rowData,rowIndex);
				<%}%>
				return desc;
			}
		<%}%> --%>
		<%if(collection!=null || collectionData != null){%>
			${itemId}_c.formatter = function(value,rowData,rowIndex){
				var collectionData = ${itemId}_c.collectionData = eval(<%=collectionData%>);
				var desc;
				if(collectionData && collectionData.length>0){
					for(var i=0;i<collectionData.length;i++){
						if(collectionData[i].id == value){
							desc = collectionData[i].name;
							break;
						}
					}
				}else{
					desc = value;
				}
				<%if(formatter != null){%>
					desc = eval('${formatter}')(value,rowData,rowIndex);
				<%}%>
				return desc;
			}
		<%}%>
		<%if(showDetail!=null){%>
			${itemId}_c.showDetail = ${showDetail};
		<%}%>
		<%if(editorType != null && "combobox".equals(editorType.trim()) && collectionData != null){%>
			var collectionData = eval('<%=collectionData%>');
			${itemId}_c.editor.options.data=collectionData;
			${itemId}_c.editor.options.valueField='id';
			${itemId}_c.editor.options.textField='name';
		<%}%>

		<%if(rowspan != null || colspan != null){%>
			${id}_ccolumns.push(${itemId}_c);
		<%} else{%>
			${id}_columns.push(${itemId}_c);
		<%}%>
	});
</script>