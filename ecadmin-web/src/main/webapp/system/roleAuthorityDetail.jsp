<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>角色权限详情</title>
	<%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	<link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
  </head>
  <body class="gray-bg">
  	<input type="hidden" id="role_id" name="role_id" value="${role.roleId}">
  	<div class="wrapper wrapper-content animated fadeInUp" style="padding:10px;">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox" style="margin-bottom: 0;">
  					<div class="ibox-title">
	                    <h5>勾选菜单给角色授权</h5>
	                    <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div>
	                </div>
	                <div class="ibox-content" style="padding-bottom:0;">
	                	<div class="btn-toolbar" role="toolbar" aria-label="<%=basePath%>UI.">
							<div class="btn-group" role="group" aria-label="<%=basePath%>UI.">
							  <button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#roleAuthorityTree').tree('expandAll');">展开</button>
							  <button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#roleAuthorityTree').tree('collapseAll');">收起</button>
							  <button type="button" class="btn btn-outline btn-default btn-sm" onclick="checkAll(true)">全选</button>
							  <button type="button" class="btn btn-outline btn-default btn-sm" onclick="checkAll(false)">取消</button>
							</div>
							<div class="btn-group" role="group" aria-label="<%=basePath%>UI.">
							  <button type="button" class="btn btn-primary btn-sm" onclick="saveRoleAuthority();">提交</button>
							</div>
						</div>
						<div style="height: 440px;">
							<ec:tree id="roleAuthorityTree" animate="true" url="queryMenuAuthorityTree" lines="true" checkbox="true" onLoadSuccess="fnLoad"></ec:tree>
						</div>
	                </div>
  				</div>
  			</div>
  		</div>
  	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
	<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
  </body>
</html>
<script type="text/javascript">
	function fnLoad(node,data){
		var role_id = $("#role_id").val();
		Base.ajax({
			url:"queryMenuAuthorityIds",
			type:'post',
			mask:false,
			data:{role_id:role_id},
			success:function(data, status, xhr){
				var ids = data.ids;
				var arr = ids.split(',');
				for ( var i in arr) {
					var id = arr[i];
					var node = $('#roleAuthorityTree').tree('find',id);
					$('#roleAuthorityTree').tree('check', node.target);
				}
			}
		});
	}
	
	function checkAll(all) {
		var roots = $('#roleAuthorityTree').tree('getRoots');
		var str;
		if(all){
			str = "check";
		}else{
			str = "uncheck";
		}
		for ( var i in roots) {
			var root = roots[i];
			$('#roleAuthorityTree').tree(str,root.target);
			checkAllChildren(str,root);
		}
	}
	
	function checkAllChildren(str,node) {
		var children = $('#roleAuthorityTree').tree('getChildren',node.target);
		if(children && children.length > 0){
			for ( var j in children) {
				var no = children[j];
				$('#roleAuthorityTree').tree(str,no.target);
				checkAllChildren(str,no);
			}
		}
	}
	
	function saveRoleAuthority(){
		var nodes = $('#roleAuthorityTree').tree('getChecked');
		var role_id = $("#role_id").val();
		var parent_ids = [];
		var menu_ids = [];
		var menu_urls = [];
		for ( var i in nodes) {
			var node = nodes[i];
			if(node.children && node.children.length > 0){
				continue;
			}
			menu_ids.push(node.id);
			parent_ids.push(node.parent_id);
			if(node.menu_url){
				menu_urls.push(node.menu_url);
			}else{
				menu_urls.push('');
			}
		}
		Base.ajax({
			url:"saveRoleAuthority",
			type:'post',
			mask:false,
			data:{role_id:role_id,parent_ids:parent_ids.join(','),menu_ids:menu_ids.join(','),menu_urls:menu_urls.join(',')},
			success:function(data, status, xhr){
				setTimeout(function(){
					top.layer.closeAll();
				}, 2000)
			}
		});
	}
	
</script>