<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>相册管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <style type="text/css">
    	.img-responsive{max-height: 145px;}
    </style>
  </head>
  <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="row">
            <div class="col-sm-12">
                <div class="ibox">
                	<div class="ibox-title">
                        <h5>所有相册展示</h5>
                        <div class="ibox-tools">
                            <a href="javascript:void(0);" class="btn btn-primary btn-xs" onclick="createAlbum();">创建新相册</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div class="row">
                    		<c:forEach items="${albums}" var="a">
                    			<div class="col-sm-4">
					                <div class="contact-box m-b-none">
				                        <div class="col-sm-4">
				                            <div class="text-center">
				                            	<c:if test="${empty a.albumCover}">
				                            		<img alt="image" class="img-circle m-t-xs img-responsive" src="<%=basePath %>UI/img/default.jpg">
				                            	</c:if>
				                                <c:if test="${not empty a.albumCover}">
				                            		<img alt="image" class="img-circle m-t-xs img-responsive" src="${imgUrl}${a.albumCoverPath}">
				                            	</c:if>
				                                <div class="m-t-xs font-bold">${a.albumName}</div>
				                            </div>
				                        </div>
				                        <div class="col-sm-8">
			                            	<h3><strong>${a.albumName}</strong></h3>
			                            	<c:if test="${a.albumType == 0}">
			                            		<p><i class="fa fa-globe"></i> 系统相册</p>
			                            	</c:if>
				                            <strong>创建人</strong>
					                        <p>${a.createUserName}</p>
					                        <strong>创建时间</strong>
					                        <p>${a.createTime}</p>
					                        <div>
					                        	<a type="button" href="viewAlbumImgs?album_id=${a.albumId}" title="进入相册" class="btn btn-outline btn-success btn-sm"><i class="fa fa-list-ul"></i></a>
					                        	<a type="button" title="编辑相册" class="btn btn-outline btn-primary btn-sm" onclick="editAlbum(${a.albumId},'${a.albumName}')"><i class="fa fa-edit"></i></a>
					                        	<a type="button" title="删除相册" class="btn btn-outline btn-danger btn-sm" onclick="deleteAlbum(${a.albumId})"><i class="fa fa-remove"></i></a>
					                        </div>
				                        </div>
				                        <div class="clearfix"></div>
					                </div>
					            </div>
                    		</c:forEach>
                    	</div>
                    </div>
                </div>
            </div>
        </div>
	</div>
	<div id="albumBox" class="container"
		style="display:none;width:350px; height:180px; padding: 10px; overflow: hidden;">
		<form class="form-horizontal" action="#">
			<input type="hidden" id="album_id" name="album_id">
			<div class="form-group">
				<label for="album_name" class="col-sm-3 control-label">相册名称</label>
				<div class="col-sm-9">
					<input id="album_name" name="album_name" type="text" class="form-control"
						placeholder="请输入相册名称" >
				</div>
			</div>
			<button id="btn-save-password" onclick="saveAlbum()" type="button"
				class="btn btn-primary center-block">
				<i class="fa fa-check"></i> 提交
			</button>
		</form>
	</div>
    <%@ include file="/UI/themes/hplus/basic.js.inc.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".contact-box").each(function(){
				animationHover(this,"pulse");
			});
		});

		function editAlbum(album_id,album_name) {
			$("#album_name").val(album_name);
			$("#album_id").val(album_id);
			layer.open({
			    type: 1,
			    shade: false,
			    title: '编辑相册', //不显示标题
			    content: $("#albumBox"), //捕获的元素
			    cancel: function(index){
			        layer.close(index);
			        this.content.hide();
			    }
			});
		}

		function deleteAlbum(album_id) {
			Base.confirm("是否确认删除相册?",function(yes){
				if(yes){
					Base.ajax({
						url : Base.globvar.basePath + "system/album/albumManager/deleteAlbum",
						data : {"album_id":album_id},
						type : 'post',
						success : function(data){
							if(!data.error) {
								setTimeout(function(){
									window.location.reload(true);
								}, 3000);
							}
						}
					});
				}
			});
		}

		function createAlbum() {
			$("#album_name").val("");
			$("#album_id").val("");
			layer.open({
			    type: 1,
			    shade: false,
			    title: '创建新相册', //不显示标题
			    content: $("#albumBox"), //捕获的元素
			    cancel: function(index){
			        layer.close(index);
			        this.content.hide();
			    }
			});
		}

		function saveAlbum(){
			var album_name = $("#album_name").val();
			if('' == Base.trimAll(album_name)){
				Base.tips("相册名称不能为空","#album_name");
				return;
			}
			Base.ajax({
				url : Base.globvar.basePath + "system/album/albumManager/saveAlbum",
				data : {"album_name": album_name,"album_id":$("#album_id").val()},
				type : 'post',
				success : function(data){
					if(!data.error) {
						$("#album_name").val("");
						setTimeout(function(){
							window.location.reload(true);
						}, 3000);
					}
				}
			});
		}
	</script>
  </body>
</html>
