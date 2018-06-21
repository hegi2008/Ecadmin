// 新定义一个单元格编辑的方法
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var ___editIndex = undefined;
Base.endDatagridEditing = function (id){
	if (___editIndex == undefined){return true}
	if ($('#'+id).datagrid('validateRow', ___editIndex)){
		$('#'+id).datagrid('endEdit', ___editIndex);
		___editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
Base.onClickCell = function (index,field,value,id){
	if (Base.endDatagridEditing(id)){
		$('#'+id).datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		___editIndex = index;
	}
}

Base.onClickRowEdit = function (index,field,value,id){
	if (___editIndex != index){
        if (Base.endDatagridEditing(id)){
            $('#'+id).datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#'+id).datagrid('getEditor', {index:index,field:field});
            if (ed){
            	// 第一个focus
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            ___editIndex = index;
        } else {
        	$('#'+id).datagrid('selectRow', ___editIndex);
        }
    }
}
/*创建datagrid表头右键简单的menu 暂时放置在这里
 * cjh
 * */
function ___createDatagridColumnMenu(cmenu,id){
    cmenu = $('<div/>').appendTo('body');
    cmenu.menu({
        onClick: function(item){
            if (item.iconCls == 'fa-check'){
                $('#'+id).datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                $('#'+id).datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'fa-check'
                });
            }
        }
    });
    var fields = $('#'+id).datagrid('getColumnFields');
    for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+id).datagrid('getColumnOption', field);
        cmenu.menu('appendItem', {
            text: col.title,
            name: field,
            iconCls: 'fa-check'
        });
    }
    return cmenu;
}