<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>新增文章</title>
    <%@ include file="/ec/inc_base.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=basePath%>ec/jslib/kindeditor/themes/simple/simple.css" />
  </head>
  <body>
	<div class="container-fluid ec-form">
		<form id="form_inline"  method="post" class="form-horizontal">
			<input type="hidden" name="article_id" id="article_id" value="${ae.article_id }">
			<div class="row">
				<div class="form-group col-sm-6 col-lg-6 ">
					<label for="cate_id" class="control-label">文章类别</label>
	               	<input id="cate_name" name="cate_name" type="text" class="form-control" value="${ae.cate_name }" placeholder="请选择文章类别"  readonly="readonly"/>
	               	<input id="cate_id" name="cate_id" type="hidden" value="${ae.cate_id }"  readonly="readonly"/>
	               	<ul class="easyui-tree ec-tree" id="cate_tree" ></ul> 
				</div>
				<div class="form-group col-sm-6 col-lg-6">
					<label for="title" class="control-label">文章标题</label>
               		<input id="title" name="title" type="text" value="${ae.title}" class="form-control" placeholder="请输入文章标题"/>
				</div>
			</div>
			<div class="row"> 
				<div class="form-group col-sm-6 col-lg-6">
					<label for="keywords" class="control-label">关键字</label>
               		<input id="keywords" type="text" class="form-control" value="${ae.keywords}" name="keywords" placeholder="请输入关键字" />
				</div>
				<div class="form-group col-sm-6 col-lg-6">
					<label for="link" class="control-label">外部链接</label>
               		<input id="link" type="text" class="form-control" name="link" value="${ae.link}" placeholder="请输入外部链接" value="http://" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6 col-lg-6">
					<label for="fromto" class="control-label">文章摘自于(招聘位置)</label>
               		<input id="fromto" type="text" class="form-control" name="fromto" value="${ae.fromto}" placeholder="请输入文章摘自于" />
				</div>
				<div class="form-group col-sm-6 col-lg-6">
					<label class="control-label">是否置顶</label>
					<div class="row">
						<div class="radio pull-left">
	                        <label>
	                            <input type="radio" name="article_top" value="0" checked />否
	                        </label>
	                    </div>
	                    <div class="radio pull-left">
	                        <label>
	                            <input type="radio" name="article_top" value="1" />是
	                        </label>
	                    </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6 col-lg-6">
					<div class="form-group col-sm-6 col-lg-6" style="padding-left: 0px;">
						<label class="control-label"><span class="required"></span>发布日期</label>
						<div class="input-group date">
						    <input name="publish_time" id="publish_time" class="form-control" size="16" type="text" value="${ae.publish_time }">
						    <span class="input-group-addon">
						    	<span class="glyphicon glyphicon-calendar"></span>
						    </span>
						</div>
					</div>
				</div>
				<div class="form-group col-sm-6 col-lg-6">
					<label class="control-label">文章排序号</label>
					<input id="sort_order" type="text" class="form-control" name="sort_order" value="${ae.sort_order}" placeholder="请输入文章排序号" />
				</div>
			</div>	
			<div class="row">
				<div class="form-group col-sm-12 col-lg-12">
					<label class="control-label">文章摘要</label>
					<textarea class="form-control" rows="3" name="abstract">${ae.abstract_value}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6 col-lg-6">
					<label for="file_url" class="control-label">附件</label>
					<div class="input-group ku-wrap">
	               		<input id="file_url_value" type="text" class="form-control"  value="${ae.file_url_value }"  />
	               		<input id="file_url" type="hidden"  name="file_url" value="${ae.file_url }"  />
						<span class="input-group-btn ku-upbutton" >
					        <button class="btn btn-default" type="button" id="file_url_button" value="上传附件">上传附件</button>
					    </span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-12 col-lg-12">
					<label class="control-label">文章内容</label>
					<textarea id="kind_content" style="width: 100%; height: 600px; visibility: hidden;">${ae.content }</textarea>
				</div>
			</div>	
			<div class="row">
				<div class="col-sm-12 col-lg-12 text-center">
					<input type="hidden" id="type_" value="${type}" status="${ae.status }">
					<button type="button" status="2" class="btn btn-primary audit-btn" style="display: none">通过审核</button>
					<button type="button" status="4" class="btn btn-primary audit-btn" style="display: none">置为无效</button>
					<button type="submit" id="submit_btn" class="btn btn-primary">提交</button>
					<button type="reset" class="btn btn-default">重置表单</button>
				</div>
			</div>
		</form>
	</div>
  </body>
</html>
<script type="text/javascript" src="<%=basePath%>ec/jslib/kindeditor/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ec/jslib/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="<%=staticsPath%>js/article/editarticle.js"></script>
<%@ include file="/ec/incfooter.jsp"%>