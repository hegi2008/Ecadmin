var CommonFormatter = {
    format: function (value, rec, index) {
        return moment(value).format('YYYY-MM-DD HH:mm:ss');
    }
};
if(typeof Base === undefined){
	var Base = {};
}

/********************表格操作****************************/
/**
 * 刷新表格数据,通过指定的地址从后台获取
 * @method loadGridData
 * @param gridId表格ID,必须以string形式传过来
 * @param submitIds 需要提交的组件id（非input 输入框）  只能是一个id ,需要提交多个找liuht
 * @param params 需要提交的数据 建议格式为:{"role_id":11} 
 */
Base.loadGridData = function(gridId,submitIds,params){
	if(typeof gridId != 'string'){return;}
	var param = "";
	var dto = {};
	if(submitIds){
		param = $("#"+submitIds).serializeArray();
		for(var i = 0; i< param.length ;i ++ ){
			dto[param[i].name] = param[i].value;
		}
	}
	if(params){
		dto = $.extend({},dto,params);
	}
	$("#"+gridId).datagrid("load",dto);
}

/**
 * fter changing data to server, refresh the front data.
 * 重载行. 后台修改数据后,刷新前台数据,但是它将保持在当前页
 * @method reloadGridData
 * @param gridId表格ID,必须以string形式传过来
 */
Base.reloadGridData = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("reload");
}

/**
 * 重载表格数据,调用此方法,datagrid不用预先指定URL
 * @method queryEcGrid
 * @param url查询地址,必须以string形式传过来
 * @param gridId表格ID,必须以string形式传过来
 * @param submitIds 需要提交的组件id（非input 输入框）  只能是一个id ,需要提交多个找liuht
 * @param params 需要提交的数据 建议格式为:{"role_id":1} 
 */
Base.queryEcGrid = function(url,gridId,submitIds,params){
	if(typeof url != 'string'){return;}
	if(typeof gridId != 'string'){return;}
	url = (url.indexOf('?')==-1?(url+"?_r="+Math.random()):(url+"&_r="+Math.random()));
	var param = "";
	var dto = {};
	if(submitIds){
		param = $("#"+submitIds).serializeArray();
		for(var i = 0; i< param.length ;i ++ ){
			dto[param[i].name] = param[i].value;
		}
	}
	if(params){
		dto = $.extend({},dto,params);
	}
	$("#"+gridId).datagrid({url:url,queryParams:dto});
	//var options = $("#"+gridId).datagrid("options");
	//if(options === undefined){
	//	throw "获取表格属性options失败";
	//}
	//options.queryParams = dto;
	//options.url = url;
	//$("#"+gridId).datagrid("load",dto);
}

/**
 * 更新指定行数据
 * @method updateDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 * @param row 更新行的新数据,{name: '新名称',note: '新消息'}
 */
Base.updateDatagridRow = function(gridId,index,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("updateRow",{
		index:index,
		row:row
	});
}

/**
 * 追加一个新行。新行将被添加到最后的位置。 
 * @method appendDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param row 新行的新数据,{name: '新名称',note: '新消息'}
 */
Base.appendDatagridRow = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	$("#"+gridId).datagrid("appendRow",row);
	// 获取表格列字段
	var fields = $('#'+gridId).datagrid('getColumnFields');
	for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+gridId).datagrid('getColumnOption', field);
        if(col.editor && col.editor.type){
        	// 具有编辑属性 开启编辑模式
        	if(Base.endDatagridEditing(gridId)){
        		Base.acceptDatagridChanges(gridId);
        		___editIndex = $('#'+gridId).datagrid('getRows').length-1;
        		$("#"+gridId).datagrid('selectRow',___editIndex).datagrid('beginEdit', ___editIndex);
        	}
        	break;
        }
    }
}

/**
 * 插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
 * @method insertDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 * @param row 行的新数据,{name: '新名称',note: '新消息'}
 */
Base.insertDatagridRow = function(gridId,index,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("insertRow",{
		index:index,
		row:row
	});
}

/**
 * 删除行
 * @method deleteDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.deleteDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	if(___editIndex != undefined && ___editIndex == index){
		$("#"+gridId).datagrid('cancelEdit', index);
		___editIndex = undefined;
	}
	$("#"+gridId).datagrid("deleteRow",index);
}

/**
 * 删除多行
 * @method deleteDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 * @param row 行数据
 */
Base.deleteDatagridRows = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row != 'object'){return;}
	for ( var i in row) {
		var index = Base.getDatagridRowIndex(gridId,row[i]);
		Base.deleteDatagridRow(gridId,index);
	}
}

/**
 * 返回指定行的索引号
 * @method getDatagridRowIndex
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.getDatagridRowIndex = function(gridId,row){
	if(typeof gridId != 'string'){return;}
	if(typeof row == 'undefined'){return;}
	return $("#"+gridId).datagrid("getRowIndex",row);
}

/**
 * 从上一次的提交获取改变的所有行。类型参数指明用哪些类型改变的行，可以使用的值有：inserted,deleted,updated等。当类型参数未配置的时候返回所有改变的行。
 * @method getDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 * @param type 可以使用的值有：inserted,deleted,updated等
 */
Base.getDatagridChanges = function(gridId,type){
	if(typeof gridId != 'string'){return;}
	if(typeof type != 'string'){
		type = undefined;
	}
	if(Base.endDatagridEditing(gridId)){
		return $("#"+gridId).datagrid("getChanges",type);
	}
	return [];
}

/**
 * 提交所有从加载或者上一次调用acceptChanges函数后更改的数据。
 * @method acceptDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.acceptDatagridChanges = function(gridId){
	if(typeof gridId != 'string'){return;}
	if(Base.endDatagridEditing(gridId)){
		$("#"+gridId).datagrid("acceptChanges");
		Base.rejectDatagridChanges(gridId);
	}
}

/**
 * 回滚所有从创建或者上一次调用acceptChanges函数后更改的数据。
 * @method rejectDatagridChanges
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.rejectDatagridChanges = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("rejectChanges");
	___editIndex = undefined;
}

/**
 * 获取指定行数据,为分页时不建议使用该方法,前台较慢
 * @method getDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.getDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	var rows = $("#"+gridId).datagrid("getRows");
	return rows[index];
}

/**
 * 返回加载完毕后的数据,分页的话依然是当前页的数据
 * @method getDatagridAllRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridAllRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getData");
}

/**
 * 返回页脚行
 * @method getDatagridFooterRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridFooterRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getFooterRows");
}

/**
 * 做调整和布局
 * @method resizeDatagrid
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.resizeDatagrid = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("resize");
}

/**
 * 加载本地数据，旧的行将被移除。
 * @method setDatagridData
 * @param gridId 表格ID,必须以string形式传过来
 * @param rows 加载的数据 按照标准json格式{"total":10,"rows":[{"":"",...},{"":"",...}]}
 */
Base.setDatagridData = function(gridId,data){
	if(typeof gridId != 'string'){return;}
	if(data.list){
		data.rows = data.list;
	}
	$("#"+gridId).datagrid("loadData",data);
}

/**
 * 返回第一个被选中的行或如果没有选中的行则返回null。
 * @method getDatagridSelected
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridSelected = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getSelected");
}

/**
 * 返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
 * @method getDatagridSelections
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.getDatagridSelections = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("getSelections");
}

/**
 * 清除所有选择的行。
 * @method clearDatagridSelections
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.clearDatagridSelections = function(gridId){
	if(typeof gridId != 'string'){return;}
	return $("#"+gridId).datagrid("clearSelections");
}

/**
 * 滚动到指定的行
 * @method scrollToDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.scrollToDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("scrollTo",index);
}

/**
 * 高亮指定行
 * @method highlightDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.highlightDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("highlightRow",index);
}

/**
 * 勾选当前页中的所有行
 * @method checkAllDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.checkAllDatagridRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("checkAll");
}

/**
 * 取消勾选当前页中的所有行
 * @method uncheckAllDatagridRows
 * @param gridId 表格ID,必须以string形式传过来
 */
Base.uncheckAllDatagridRows = function(gridId){
	if(typeof gridId != 'string'){return;}
	$("#"+gridId).datagrid("uncheckAll");
}

/**
 * 勾选一行，行索引从0开始
 * @method checkDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.checkDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("checkRow",index);
}

/**
 * 取消勾选一行，行索引从0开始
 * @method uncheckDatagridRow
 * @param gridId 表格ID,必须以string形式传过来
 * @param index 行索引
 */
Base.uncheckDatagridRow = function(gridId,index){
	if(typeof gridId != 'string'){return;}
	if(typeof index == 'undefined'){return;}
	if(typeof index != 'number'){
		index = Number(index);
	}
	$("#"+gridId).datagrid("uncheckRow",index);
}

/**
 * 合并单元格
 * @method mergeDatagridCells
 * @param gridId 表格ID,必须以string形式传过来
 * @param options包含以下属性：
 * index：行索引。
 * field：字段名称。
 * rowspan：合并的行数。
 * colspan：合并的列数。如:{index:2,rowspan:2,field:"user_id"}
 */
Base.mergeDatagridCells = function(gridId,options){
	if(typeof gridId != 'string'){return;}
	if(typeof options != 'object'){return;}
	$("#"+gridId).datagrid("mergeCells",{
		index:options.index,
		field:options.field,
		rowspan:options.rowspan,
		colspan:options.colspan
	});
}

/**
 * 显示指定的列
 * @method showDatagridColumn
 * @param gridId 表格ID,必须以string形式传过来
 * @param field 列名
 */
Base.showDatagridColumn = function(gridId,field){
	if(typeof gridId != 'string'){return;}
	if(typeof field != 'string'){return;}
	$("#"+gridId).datagrid("showColumn",field);
}

/**
 * 隐藏指定的列
 * @method hideDatagridColumn
 * @param gridId 表格ID,必须以string形式传过来
 * @param field 列名
 */
Base.hideDatagridColumn = function(gridId,field){
	if(typeof gridId != 'string'){return;}
	if(typeof field != 'string'){return;}
	$("#"+gridId).datagrid("hideColumn",field);
}