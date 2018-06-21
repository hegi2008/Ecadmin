<%@ tag language="java" pageEncoding="UTF-8" %>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Random"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.Enumeration"%>

<%@ attribute name="id" description="表格id,唯一标识" required="true" type="java.lang.String" %>
<%@ attribute name="data" description='直接指定表格数据格式为json格式对象组，例如data="[{‘a’:‘1111’,‘b’:’32‘},{’a‘:’3234‘,’b‘:’311‘}]"' type='java.lang.String' %>
<%@ attribute name="title" description="表格标题" required="false" type="java.lang.String" %>
<%@ attribute name="height" description="表格高度,如400px,支持百分比,默认auto" required="false" type="java.lang.String" %>
<%@ attribute name="width" description="表格宽度" required="false" type="java.lang.String" %>
<%@ attribute name="striped" description="表格奇偶行样式区分,默认开启true,关闭false" required="false" type="java.lang.String" %>
<%@ attribute name="rowBorder" description="表格行border样式,分别有lines-both/lines-no/lines-right/lines-bottom,默认lines-both" required="false" type="java.lang.String" %>
<%@ attribute name="showRowno" description="是否显示行号" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showck" description="是否显示checkbox" required="false" type="java.lang.Boolean" %>
<%@ attribute name="dataurl" description="查询数据的url,返回json,一般不设置" required="false" type="java.lang.String" %>
<%@ attribute name="showBorder" description="是否有外边框,默认true" required="false" type="java.lang.Boolean" %>
<%@ attribute name="frozenck" description="是否冻结checkbox" required="false" type="java.lang.Boolean" %>
<%@ attribute name="singleSelect" description="单选还是多选,true是单选/false是多选,默认false多选" required="false" type="java.lang.Boolean" %>
<%@ attribute name="collapsible" description="表格是否支持折叠,默认true" required="false" type="java.lang.Boolean" %>
<%@ attribute name="fitColumns" description="真正的自动展开/收缩列的大小,适应表格宽度,开启会影响加载速度" required="false" type="java.lang.Boolean"%>
<%@ attribute name="frozenColumn" description="冻结列的ID,这里添加了冻结列,就不需要再添加相应的gridItem了" required="false" type="java.lang.String"%>
<%@ attribute name="frozenColumnName" description="冻结列的名称" required="false" type="java.lang.String"%>
<%@ attribute name="frozenColumnWidth" description="冻结列的宽度默认200" required="false" type="java.lang.String"%>
<%@ attribute name="checkOnSelect" description="如果为true，当用户点击行的时候该复选框就会被选中或取消选中,如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。默认true" required="false" type="java.lang.Boolean"%>
<%@ attribute name="selectOnCheck" description="如果为true，单击复选框将永远选择行。如果为false，选择行将不选中复选框。默认true" required="false" type="java.lang.Boolean"%>
<%@ attribute name="view" description="定义DataGrid的视图,groupview分组视图;detailview详细视图;scrollview懒加载数据的视图,会大大提高不分页时大数据造成的前台性能问题.使用scrollview必须设置pagesize,多少条之后再加载剩余的数据" required="false" type="java.lang.String"%>
<%@ attribute name="viewAsIframe" description="view的代开方式,为true时将以iframe的方式打开" required="false" type="java.lang.Boolean"%>
<%@ attribute name="groupField" description="分组视图时,按那一列分组的ID" required="false" type="java.lang.String"%>
<%@ attribute name="groupTitle" description="分组视图,要显示的分组标题" required="false" type="java.lang.String"%>
<%-- <%@ attribute name="groupFormatter" description="分组formatter,参数value,rows.返回分组表头内容" required="false" type="java.lang.String"%> --%>
<%@ attribute name="onClickRow" description="在用户点击一行的时候触发，参数包括：rowIndex：点击的行的索引值，该索引值从0开始。rowData：对应于点击行的记录。" required="false" type="java.lang.String"%>
<%@ attribute name="onDblClickRow" description="在用户双击一行的时候触发，参数包括：rowIndex：点击的行的索引值，该索引值从0开始。rowData：对应于点击行的记录。" required="false" type="java.lang.String"%>
<%@ attribute name="onSelect" description="在用户选择一行的时候触发，参数包括：rowIndex：选择的行的索引值，索引从0开始。rowData：对应于所选行的记录" required="false" type="java.lang.String"%>
<%@ attribute name="onUnselect" description="在用户取消选择一行的时候触发，参数包括：rowIndex：选择的行的索引值，索引从0开始。rowData：对应于所选行的记录" required="false" type="java.lang.String"%>
<%@ attribute name="onSelectAll" description="在用户选择所有行的时候触发。参数:rows" required="false" type="java.lang.String"%>
<%@ attribute name="onUnselectAll" description="在用户取消选择所有行的时候触发。参数:rows" required="false" type="java.lang.String"%>
<%@ attribute name="onBeforeEdit" description="在用户开始编辑一行的时候触发，参数包括：rowIndex：编辑行的索引，索引从0开始。rowData：对应于编辑行的记录。" required="false" type="java.lang.String"%>
<%@ attribute name="onAfterEdit" description="在用户完成编辑一行的时候触发，参数包括：rowIndex：编辑行的索引，索引从0开始。rowData：对应于完成编辑的行的记录。changes：更改后的字段(键)/值对。" required="false" type="java.lang.String"%>
<%@ attribute name="onLoadSuccess" description="在数据加载成功的时候触发,参数data" required="false" type="java.lang.String"%>
<%@ attribute name="detailviewurl" description="在使用view=detailview的时候,需要load的地址" required="false" type="java.lang.String"%>
<%@ attribute name="detailviewheight" description="在使用view=detailview的时候,视图的高度,默认auto自适应" required="false" type="java.lang.String"%>
<%@ attribute name="detailviewparams" description="在使用view=detailview的时候,需要传递的参数key值,key值必须包含于columns当中,如:'goods_id,store_goods_id'" required="false" type="java.lang.String"%>
<%@ attribute name="hasComplicateHeader" description="是否包含复杂表头,默认false" required="false" type="java.lang.Boolean"%>
<%@ attribute name="onCheck" description="在用户勾选一行的时候触发，参数包括：rowIndex：选择的行的索引值，索引从0开始。rowData：对应于所选行的记录" required="false" type="java.lang.String"%>
<%@ attribute name="onUncheck" description="在用户取消勾选一行的时候触发，参数包括：rowIndex：选择的行的索引值，索引从0开始。rowData：对应于所选行的记录" required="false" type="java.lang.String"%>
<%@ attribute name="fit" description="为true时,将使表格充满父容器" required="false" type="java.lang.Boolean"%>
<%@ attribute name="rowStyler" description="返回样式如'background:red'。带2个参数的函数：rowIndex：该行索引从0开始,rowData：与此相对应的记录行" required="false" type="java.lang.String" %>
<%@ attribute name="pagination" description="为true开启分页" required="false" type="java.lang.String" %>
<%@ attribute name="pagePosition" description="定义分页工具栏的位置。可用的值有：'top','bottom','both' 默认bottom" required="false" type="java.lang.String" %>
<%@ attribute name="pageNumber" description="在设置分页属性的时候初始化页码,默认1" required="false" type="java.lang.Integer" %>
<%@ attribute name="pageSize" description="在使用scrollview视图的时候,默认10;在设置分页属性的时候初始化页面大小,默认10" required="false" type="java.lang.Integer" %>
<%@ attribute name="pageList" description="在设置分页属性的时候 初始化页面大小选择列表.默认[10,20,30,40,50]" required="false" type="java.lang.String" %>
<%@ attribute name="maximizable" description="为true时,允许展开最大化" required="false" type="java.lang.String" %>
<%@ attribute name="minimizable" description="为true时,允许最小化" required="false" type="java.lang.String" %>
<%@ attribute name="showHeader" description="定义是否显示行头" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showFooter" description="定义是否显示行脚" required="false" type="java.lang.Boolean" %>
<%@ attribute name="celleditable" description="定义是否开启单元格编辑模式.注意:行编辑和列单元格编辑只能开启一个" required="false" type="java.lang.Boolean" %>
<%@ attribute name="roweditable" description="定义是否开启行编辑模式.注意:行编辑和列单元格编辑只能开启一个" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showHeaderContextMenu" description="定义是否开启表头右键功能,可以隐藏显示表格列" required="false" type="java.lang.Boolean" %>
<%@ attribute name="queryParams" description="第一次请求远程数据的时候发送额外的参数。请传入标准的json格式" required="false" type="java.lang.String" %>

<%
	//优先 查找resultBean
	/* ResultBean resultBean = (ResultBean)TagUtil.getResultBean();
	if(resultBean != null){
		Map<String, PageBean> map = resultBean.getLists();
		if(map !=null){
			PageBean pageBean = map.get(this.id);
			if(pageBean != null){
				List list = pageBean.getList();
				Integer total = pageBean.getTotal();
				Map all = new HashMap();
				all.put("rows", list);
				all.put("total", total);
	 			data = JSonFactory.bean2json(all);
			}
		}
	} */
	if(data != null && !"".equals(data.trim())){
		jspContext.setAttribute("data", data);
	}
	
	if(height == null || "".equals(height.trim())){
		height = "auto";
		jspContext.setAttribute("height", height);
	}
	
	if(width == null || "".equals(width.trim())){
		width = "100%";
		jspContext.setAttribute("width", width);
	}
	
	if(striped == null || "".equals(striped.trim())){
		striped = "false";
		jspContext.setAttribute("striped", striped);
	}
	
	if(rowBorder == null || "".equals(rowBorder.trim())){
		rowBorder = "lines-both";
		jspContext.setAttribute("rowBorder", rowBorder);
	}
	
	if(showRowno == null){
		showRowno = false;
		jspContext.setAttribute("showRowno", showRowno);
	}
	
	if(showBorder == null){
		showBorder = true;
		jspContext.setAttribute("showBorder", showBorder);
	}
	
	if(fit == null){
		fit = false;
		jspContext.setAttribute("fit", fit);
	}
	
	if(singleSelect == null){
		singleSelect = true;
		jspContext.setAttribute("singleSelect", singleSelect);
	}
	
	if(collapsible == null){
		collapsible = true;
		jspContext.setAttribute("collapsible", collapsible);
	}
	
	if(fitColumns == null){
		fitColumns = false;
		jspContext.setAttribute("fitColumns", fitColumns);
	}
	
	if(frozenColumnWidth == null || "".equals(frozenColumnWidth.trim())){
		frozenColumnWidth = "200";
		jspContext.setAttribute("frozenColumnWidth", frozenColumnWidth);
	}
	
	if(checkOnSelect == null){
		checkOnSelect = true;
		jspContext.setAttribute("checkOnSelect", checkOnSelect);
	}
	
	if(selectOnCheck == null){
		selectOnCheck = true;
		jspContext.setAttribute("selectOnCheck", selectOnCheck);
	}
	
	if(showck == null){
		showck = false;
		jspContext.setAttribute("showck", showck);
	}
	
	if(pagePosition == null || "".equals(pagePosition.trim())){
		pagePosition = "bottom";
		jspContext.setAttribute("pagePosition", pagePosition);
	}
	if(pageNumber == null){
		pageNumber = 1;
		jspContext.setAttribute("pageNumber", pageNumber);
	}
	if(pageSize == null){
		pageSize = 10;
		jspContext.setAttribute("pageSize", pageSize);
	}
	if(pageList == null || "".equals(pageList.trim())){
		pageList = "[10,20,30,40,50]";
		jspContext.setAttribute("pageList", pageList);
	}
	if(showFooter == null){
		showFooter = false;
		jspContext.setAttribute("showFooter", showFooter);
	}
	if(showHeader == null){
		showHeader = true;
		jspContext.setAttribute("showHeader", showHeader);
	}
%>
<script type="text/javascript">
	var ${id}_columns = [];
	var ${id}_ccolumns = [];// 复杂表头的上半部分
	var ${id}_toolbar;
</script>
<table id="${id }" style="height:${height};width:<%=width%>;">
	<%-- <thead>
		<%if(hasComplicateHeader != null && hasComplicateHeader){%>
			<jsp:doBody></jsp:doBody>
		<%}else{%>
			<tr>
				<jsp:doBody></jsp:doBody>
			</tr>
		<%}%>
	</thead> --%>
	<jsp:doBody></jsp:doBody>
</table>

<script type="text/javascript">
	<%if(showHeaderContextMenu != null && showHeaderContextMenu){%>
		var ${id}_HeaderContextMenu;
	<%}%>
	$(function(){
		var showck = ${showck};
		var frozenColumn = '${frozenColumn}';
		var frozenColumnName = '${frozenColumnName}';
		var frozenColumnWidth = '${frozenColumnWidth}';
		var frozenColumns = new Array();
		if(!showck && frozenColumn == ''){
			// 不显示checkBox 没有冻结列
		}else if(showck && frozenColumn == ''){
			frozenColumns.push([{field:'ck',checkbox:true}]);
		}else if(!showck && frozenColumn != ''){
			frozenColumns.push([{field:''+frozenColumn+'',title:''+frozenColumnName+'',width:frozenColumnWidth,editor:'text'}]);
		}else{
			frozenColumns.push([{field:'ck',checkbox:true},{field:''+frozenColumn+'',title:''+frozenColumnName+'',width:frozenColumnWidth,editor:'text'}]);
		}
		
		var toolbar = null;
		if(${id}_toolbar){
			toolbar = ${id}_toolbar;
		}
		
		var columns = [];
		if(${id}_ccolumns.length > 0 && ${id}_columns.length > 0){
			columns.push(${id}_ccolumns);
			columns.push(${id}_columns);
		}else if(${id}_ccolumns.length == 0 && ${id}_columns.length > 0){
			columns.push(${id}_columns);
		}else if(${id}_ccolumns.length > 0 && ${id}_columns.length == 0){
			columns.push(${id}_ccolumns);
		}
		$('#${id}').datagrid({
			title:"${title}",
			striped:${striped},
			/* idField:'${id}', */
			border:${showBorder},
			toolbar:toolbar,
			columns:columns,
			<%if(queryParams != null){%>queryParams:${queryParams},<%}%>
			<%if(dataurl != null){%>url:"${dataurl}",<%}%>
			<%if(data != null){%>data:eval(${data}),<%}%>
			rownumbers:${showRowno},
			singleSelect:${singleSelect},
			collapsible:${collapsible},
			checkOnSelect:${checkOnSelect},
			selectOnCheck:${selectOnCheck},
			frozenColumns:frozenColumns,
			remoteSort:false, //确保排序可用
			autoRowHeight:false,
			<%if(maximizable != null){%>maximizable:<%=maximizable%>,<%}%>
			<%if(minimizable != null){%>minimizable:<%=minimizable%>,<%}%>
			<%if(pagination != null){%>pagination:<%=pagination%>,<%}%>
			pagePosition:'${pagePosition}',
			pageNumber:${pageNumber},
			pageSize:${pageSize},
			pageList:eval('${pageList}'),
			showHeader:${showHeader},
			showFooter:${showFooter},
			fit:${fit},
			fitColumns:${fitColumns},
			onLoadSuccess:function(data){
				var options = $('#${id}').datagrid('options');
				<%if(onLoadSuccess != null){%>
					eval(<%=onLoadSuccess%>)(data);
				<%}%>
				$('#${id}').datagrid('resize');
				var cs = ${id}_columns;
				for(var i in cs){
					var item = cs[i];
					if(item.showDetail){
						$('#${id}').datagrid('doCellTip', {
							itemid : item.field,
					        onlyShowInterrupt : true,
					        position : 'top',
					        maxWidth : '300px',
					    });
					}
				}
				<%if( celleditable != null && celleditable){%>
					options.onClickCell = function(index,field,value){
						Base.onClickCell(index,field,value,'${id}');
					}
				<%}%>
				<%if( roweditable != null && roweditable){%>
					options.onClickCell = function(index,field,value){
						Base.onClickRowEdit(index,field,value,'${id}');
					}
				<%}%>
			},
			onClickRow:eval('${onClickRow}'),
			onSelect:eval('${onSelect}'),
			onDblClickRow:eval('${onDblClickRow}'),
			onUnselect:eval('${onUnselect}'),
			onSelectAll:eval('${onSelectAll}'),
			onUnselectAll:eval('${onUnselectAll}'),
			onBeforeEdit:eval('${onBeforeEdit}'),
			rowStyler:eval('${rowStyler}'),
			onHeaderContextMenu:function(e, field){
				e.preventDefault();
				<%if(showHeaderContextMenu != null && showHeaderContextMenu){%>
					if(!${id}_HeaderContextMenu){
						${id}_HeaderContextMenu = ___createDatagridColumnMenu(${id}_HeaderContextMenu,'${id}');
					}
					${id}_HeaderContextMenu.menu('show', {
                        left:e.pageX,
                        top:e.pageY
                    });
				<%}%>
			},
			onAfterEdit:function(rowIndex,rowData,changes){
				<%if(onAfterEdit == null){%>return false;<%}%>
				var change = $('#${id}').datagrid('getChanges');
		      	if(change.length>0){
		       		eval('${onAfterEdit}')(rowIndex,rowData,changes);
					$('#${id}').datagrid('acceptChanges');
		       	}
			},
			onCheck:eval('${onCheck}'),
			onUncheck:eval('${onUncheck}')
			<%if(view != null && !"".equals(view.trim())){
				if("groupview".equals(view)){
			%>
			,view:${view},
			groupField:'${groupField}',
			groupFormatter:function(value,rows){
				return '${groupTitle}='+value+'('+rows.length+')';
			}
			<%
				}else if("detailview".equals(view)){
			%>
			,view:${view},
			detailFormatter:function(index,row){
                    return '<div class="ddv" style="padding:10px;"></div>';
            },
            onExpandRow: function(index,row){
            	var params = '${detailviewparams}';
            	var paramArray = params.split(',');
            	var paramJson = '';
            	if(paramArray.length=1){
            		paramJson = params+'='+row[params];
            	}else{
            		for(var i in paramArray){
	            		var ps = paramArray[i];
	            		paramJson += ps+'='+row[ps];
	            		if(i != paramArray.length-1){
	            			paramJson += '&';
	            		}
	            	}
            	}
                var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                var href = '${detailviewurl}?'+paramJson;
                <%if(viewAsIframe != null && viewAsIframe){%>
               		var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';
                <%}%>
                ddv.panel({
                	<%if(detailviewheight !=null){%>height:${detailviewheight},<%}else{%>height:'auto',<%}%>
                    border:false,
                    cache:false,
                    <%if(viewAsIframe != null && viewAsIframe){%>
                    	content:content,
                	<%}else{%>
                		href:'${detailviewurl}?'+paramJson,
                	<%}%>
                    onLoad:function(){
                        $('#${id}').datagrid('fixDetailRowHeight',index);
                    }
                });
                $('#${id}').datagrid('fixDetailRowHeight',index);
            }
			<%	
				}else if("scrollview".equals(view)){%>
				,view:${view}
				<%}
			}
			%>
		});
		// 表格行border样式设置
		var rowBorder = '${rowBorder}';
		if(rowBorder == '' || rowBorder == null || rowBorder == undefined){
			rowBorder = 'lines-both';
		}
		$('#${id}').datagrid('getPanel').removeClass('lines-both lines-no lines-right lines-bottom').addClass(rowBorder);
		
	});
</script>