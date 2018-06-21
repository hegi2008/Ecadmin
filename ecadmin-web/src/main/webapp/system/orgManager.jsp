<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>部门管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <SCRIPT type="text/javascript">
        var setting = {
            async: {
                enable: true,
                url:"../asyncData/getNodes.php",
                <%--url:"${basePath}system/org/orgManager/queryOrgList",--%>
                autoParam:["id=0", "name=n", "level=lv"],
                otherParam:{"otherParam":"zTreeAsyncTest"},
                dataFilter: filter
            },
            view: {
                expandSpeed:"",
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                drag:{
                    isCopy : false,
                    isMove : true,
                    rev : true,
                    inner : true,
                    next : true
                }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId"
                }
            },
            callback: {
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
                onDrop: zTreeOnDrop
            }
        };
        var zNodes=${list};
        function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i=0, l=childNodes.length; i<l; i++) {
                childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            }
            return childNodes;
        }
        function beforeRemove(treeId, treeNode){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.selectNode(treeNode);
            var msg="确认删除 节点 --"+ treeNode.name + " 吗？";
            if(treeNode.isParent){
                msg="该节点存在子节点，确认删除 节点 --"+ treeNode.name + "及其所有子节点吗？";
            }
            Base.confirm(msg,function(yes){
                if(yes){
                    Base.ajax({
                        url:Base.globvar.basePath + "system/org/orgManager/deleteOrg",
                        type:'post',
                        data:{orgId:treeNode.id,treeNode:treeNode},
                        success:function(data, status, xhr){
                            if(!data.error){
                                Base.alert("删除部门成功");
                                zTree.removeNode(treeNode);
                            }
                        }
                    });
                }
            });
            return false;
        }
//        function zTreeBeforeDrag(treeId, treeNodes) {
//            if(confirm("确认移动 节点 -- " + treeNodes[0].name + " 吗？")){
//                return true;
//            }else{
//                return false;
//            }
//        };
        function beforeRename(treeId, treeNode, newName) {
            if (newName.length == 0) {
                Base.alert("节点名称不能为空.");
                return false;
            }else{
                Base.ajax({
                    url:Base.globvar.basePath + "system/org/orgManager/saveOrg",
                    type:'post',
                    data:{pId:treeNode.pId,orgId:treeNode.id,orgName:newName,treeNode:treeNode},
                    success:function(data, status, xhr){
                        if(!data.error){
                            Base.alert("修改部门信息成功");
                        }
                    }
                });
            }
            return true;
        }
        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var name ="new node" + (newCount++);
                Base.ajax({
                    url:Base.globvar.basePath + "system/org/orgManager/saveOrg",
                    type:'post',
                    data:{pId:treeNode.id, orgName:name,treeNode:treeNode},
                    success:function(data, status, xhr){
                        zTree.addNodes(treeNode, {id:data.lists.newId, pId:treeNode.id, name:name});
                        if(!data.error){
                            Base.alert("保存部门信息成功");
                        }
                    }
                });
                return false;
            });
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
        function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
//            Base.alert(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ));
            Base.ajax({
                url:Base.globvar.basePath + "system/org/orgManager/saveOrg",
                type:'post',
                data:{orgId:treeNodes[0].id,pId:treeNodes[0].pId,orgName:treeNodes[0].name,treeNode:treeNodes},
                success:function(data, status, xhr){
                    if(!data.error){
                        Base.alert("挪动部门位置成功");
                    }
                }
            });
        };
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting,zNodes);
        });
        //-->
    </SCRIPT>
    <%--<style type="text/css">--%>
        <%--.datagrid-cell{line-height: 1;}--%>
    <%--</style>--%>
    <%--ztree相关--%>
    <%--<link rel="stylesheet" href="<%=basePath%>UI/css/plugins/ztree/demo.css" type="text/css">--%>
    <link rel="stylesheet" href="<%=basePath%>UI/plugins/ztree/css/metroStyle/metroStyle.css" type="text/css">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>部门列表</h5>
                </div>
                <div class="ibox-content">
                            <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<%--ztree相关--%>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/ztree/js/jquery.ztree.exedit.js"></script>
</body>
</html>
