/**
 * 禁用指定的选项卡面板
 * @method disableEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.disableEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('disableTab', title);
}
/**
 * 启用指定的选项卡面板
 * @method enableEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.enableEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('enableTab', title);
}

/**
 * 页面跳到指定的选项卡面板
 * @method selectEcTab
 * @param id 选项卡tabs的ID,参数String
 * @param title 选项卡tab的title,参数String
 */
Base.selectEcTab = function(id,title){
	if(typeof id != 'string'){return;}
	if(typeof title != 'string'){return;}
	$('#'+id).tabs('select', title);
}