<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>新增账户</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
</head>
<body>
<div class="wrapper wrapper-content animated fadeInUp" style="padding-left: 30px;padding-right: 30px;">
    <form id="form1" method="post" class="form-horizontal">
        <input id="edit" name="edit" type="hidden" value="${edit}">
        <div class="form-group">
            <label for="yad901" class="control-label"><span style="color: red;">*</span>账户ID</label>
            <input id="yad901" name="yad901" type="text" class="form-control" placeholder="请输入账户ID"
                   value="${account.yad901}"/>
        </div>
        <div class="form-group">
            <label for="yad961" class="control-label"><span style="color: red;">*</span>渠道</label>
            <select class="form-control" id="yad961" name="yad961">
                <c:forEach var="type" items="${qd_type}">
                    <c:if test="${type.codeValue == account.yad961}">
                        <option value="${type.codeValue}" selected="true">${type.codeValueName}</option>
                    </c:if>
                    <c:if test="${type.codeValue != account.yad961}">
                        <option value="${type.codeValue}">${type.codeValueName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="des" class="control-label"><span style="color: red;">*</span>账号描述</label>
            <input id="des" name="des" type="text" class="form-control" placeholder="请输入账号描述" value="${account.des}"/>
        </div>
        <div class="col-sm-12 col-lg-12 text-center">
            <button type="submit" class="btn btn-primary">提交</button>
            <button type="reset" class="btn btn-outline btn-default" onclick="resetForm();">重置</button>
        </div>
    </form>
</div>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        initForm();
    });


    function resetForm() {
        $('#form1').data('bootstrapValidator').resetForm();
    }

    function initForm() {
        // 表单验证初始化
        $("#form1").bootstrapValidator({
            excluded: ':disabled',
            message: '该项有错,请检查',
            submitHandler: function (validator, form, submitButton) {
                var param = form.serializeArray();
                Base.ajax({
                    url: Base.globvar.basePath + "shh/account/accountManager/saveAccount",
                    type: 'post',
                    data: param,
                    success: function (data, status, xhr) {
                    }
                });
            },
            fields: {
                yad901: {
                    validators: {
                        notEmpty: {
                            message: "账号ID不能为空"
                        },
                        stringLength: {
                            max: 50,
                            message: "最多输入50个字"
                        }
                    }
                },
                yad961: {
                    validators: {
                        notEmpty: {
                            message: "渠道不能为空"
                        },
                        stringLength: {
                            max: 5,
                            message: "最多输入5个字"
                        }
                    }
                }
            }
        });
    }
</script>