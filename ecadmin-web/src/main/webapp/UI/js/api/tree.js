/********************tree操作****************************/
/**
 * 获取所有选中的节点
 * @method getEcTreeChecked
 * @param id treeID,参数String
 * @param state 可用值有：'checked','unchecked','indeterminate'。如果'state'未指定，将返回'checked'节点,参数String
 */
Base.getEcTreeChecked = function(id,state){
	if(typeof id != 'string'){return;}
	return $('#'+id).tree('getChecked',state);
}

/********************combotree操作****************************/

/**
 * 获取选择的节点,返回node
 * 注意:针对单选
 * @method getEcComboTreeSelected
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeSelected = function(id){
	if(typeof id != 'string'){return;}
	var t = $("#"+id).combotree("tree");
	return t.tree('getSelected');
}

/**
 * 获取所有选择的节点,返回node数组
 * 'state'可用值有：'checked 选择的节点(默认)','unchecked 未选择的节点','indeterminate 不确定的节点'
 * 注意:针对多选
 * @method getEcComboTreeSelected
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeChecked = function(id,state){
	if(typeof id != 'string'){return;}
	var t = $("#"+id).combotree("tree");
	if(state){
		return t.tree('getChecked',state);
	}else{
		return t.tree('getChecked');
	}
}

/**
 * 获取所有选中的节点,针对ec:combotree标签,获取到的ID值数组,["11", "111", "112", "113", "121"]
 * 注意:适用于多选
 * @method getEcComboTreeValues
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeValues = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("getValues");
}

/**
 * 获取选中的节点,针对ec:combotree标签,获取到的ID值,"11"
 * 注意:适用于单选
 * @method getEcComboTreeValue
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeValue = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("getValue");
}

/**
 * 获取返回文本框的值,针对ec:combotree标签,获取到值,"Program Files,Intel,Java,Microsoft Office,Games"
 * 注意:单选,多选都可以
 * @method getEcComboTreeText
 * @param id combotree的ID,参数String
 */
Base.getEcComboTreeText = function(id){
	if(typeof id != 'string'){return;}
	return $("#"+id).combotree("textbox").val();
}

/**
 * 设置combotree的值 单选 
 * value 如6
 */
Base.setEcComboTreeValue = function(id,value){
	if(typeof id != 'string'){return;}
	if(value){
		$("#"+id).combotree("setValue",value);
	}
}

/**
 * 设置combotree的值 多选 
 * value 如[5,6,1]
 */
Base.setEcComboTreeValues = function(id,value){
	if(typeof id != 'string'){return;}
	if(value){
		$("#"+id).combotree("setValues",value);
	}
}