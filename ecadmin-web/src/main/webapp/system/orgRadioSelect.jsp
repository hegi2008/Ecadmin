<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<link rel="stylesheet" href="<%=basePath%>UI/plugins/ztree/css/metroStyle/metroStyle.css" type="text/css">
<ul id="orgTree" class="ztree"></ul>
<%--ztree相关--%>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.exedit.js"></script>
<!-- 
	使用说明
	引用此jsp的界面,只要保证有afterOrgRadioSelected(treeId, treeNode)
	,就能够调用选择后的回调 传入treeId, treeNode
-->
<script type="text/javascript">
	$(document).ready(function(){
		 $.fn.zTree.init($("#orgTree"),setting,null);
	});
	var setting = {
		async: {
			enable: true,
			contentType: "application/json",
			url: Base.globvar.basePath + "system/common/queryOrgJson",
			dataType:"json"
		},
		view: {
			showIcon: false
		},
		data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId"
            }
        },
		check: {
			enable: true,
			chkStyle: "radio"
		},
		callback: {
			beforeCheck: beforeRadioOrgCheck,
			onCheck: onRadioOrgCheck
		}
	}
	
	/**
		勾选之前的回调
	*/
	function beforeRadioOrgCheck(treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodes = treeObj.getCheckedNodes(true);
		if(!treeNode.checked){
			for(var i in nodes){
				treeObj.checkNode(nodes[i]);
			}
		}
		return true;
	}
	
	function onRadioOrgCheck(event, treeId, treeNode) {
		if(treeNode.checked){
			if(afterOrgRadioSelected && Base.isFunction(afterOrgRadioSelected)){
				afterOrgRadioSelected(treeId,treeNode);
			}
		}
	}
</script>