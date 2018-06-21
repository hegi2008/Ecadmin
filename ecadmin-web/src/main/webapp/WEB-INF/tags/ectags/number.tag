<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="com.yinhai.sysframework.util.IConstants"%>
<%@tag import="com.yinhai.sysframework.app.domain.jsonmodel.ResultBean"%>
<%@tag import="java.util.Random"%>
<%@tag import="com.yinhai.sysframework.util.TagUtil"%>
<%-- @doc --%>
<%@tag description='数字输入框' display-name="number"%>
<%@attribute name="id"  type="java.lang.String" rtexprvalue="true" required="true" description="组件id页面唯一"%>
<%@attribute name="key" type="java.lang.String" rtexprvalue="true" description="组件的label标签,不支持html标签 "%>
<%@attribute name="cssClass" type="java.lang.String" rtexprvalue="true" description="给该组件添加自定义样式class，如:cssClass='no-padding'"%>
<%@attribute name="cssStyle" type="java.lang.String" rtexprvalue="true" description="给该组件添加自定义样式，如:cssStyle='padding-top:10px'表示容器顶部向内占用10个像素"%>
<%@attribute name="disabled" type="java.lang.String" rtexprvalue="true" description="true/false,设置页面初始化的时候改组件不可用，同时表单提交时不会传值到后台，默认为false"%>
<%@attribute name="readOnly" type="java.lang.String" rtexprvalue="true" description="true/false,设置为只读，默认为false"%>
<%@attribute name="display" type="java.lang.String" rtexprvalue="true" description="true/false,设置是否显示，默认为显示:true"%>
<%@attribute name="required" type="java.lang.String" rtexprvalue="true" description="true/false,设置是否必输，默认false，设置后代小红星"%>
<%@attribute name="labelWidth" type="java.lang.String" rtexprvalue="true" description="label及key占的宽度，如labelWidth='120'"%>
<%@attribute name="labelStyle" type="java.lang.String" rtexprvalue="true" description="label自定义样式"%>
<%@attribute name="cssInput" type="java.lang.String"  rtexprvalue="true" description="单独设置input元素的css样式,例如:cssInput='font-size:20px;color:red'"%>
<%@attribute name="value" type="java.lang.String" rtexprvalue="true" description="组件页面初始化的时候的默认值"%>
<%@attribute name="toolTip" type="java.lang.String" rtexprvalue="true" description="鼠标移过提示文本"%>
<%@attribute name="precision" type="java.lang.String" rtexprvalue="true" description="小数位数，如precision='2',默认小数位为0"%>
<%@attribute name="max" type="java.lang.String" rtexprvalue="true" description="最大值"%>
<%@attribute name="min" type="java.lang.String" rtexprvalue="true" description="最小值 "%>
<%@attribute name="decimalSeparator" type="java.lang.String" rtexprvalue="true" description="使用哪一种十进制字符分隔数字的整数和小数部分,如,. "%>
<%@attribute name="groupSeparator" type="java.lang.String" rtexprvalue="true" description="使用哪一种字符分割整数组，以显示成千上万的数据。(比如：99,999,999.00中的','就是该分隔符设置。)"%>
<%@attribute name="prefix" type="java.lang.String" rtexprvalue="true" description="前缀字符。(比如：金额的$或者￥)"%>
<%@attribute name="suffix" type="java.lang.String" rtexprvalue="true" description="后缀字符。(比如：后置的欧元符号€)"%>
<%@attribute name="onChange" type="java.lang.String" rtexprvalue="true" description="当字段值更改的时候触发,传入两个参数newValue,oldValue"%>

<%
	ResultBean resultBean = (ResultBean)TagUtil.getResultBean();
	if(resultBean != null){
		Object v =  resultBean.getFieldData()==null?null:resultBean.getFieldData().get(id);
 		if(v !=null && !"".equals(v)){
			value= v.toString();
 		}
 	}
	//查找request
 	if(value != null && !"".equals(value) && request.getAttribute(id) != null){
 		value = request.getAttribute(id).toString();
 	}
	//查找session
 	if(value != null && !"".equals(value) && request.getSession().getAttribute(id) != null){
 		value = request.getSession().getAttribute(id).toString();
 	}
%>
<div id="ec-number-${id}" class="fielddiv fielddiv_163 ${cssClass}" 
<% if (cssStyle != null){%>
  style="margin:7px;<%=cssStyle %>" 
<%}%>
<% if (toolTip != null){%>
  title="${toolTip}"
<%}%>
>
<% if (key != null){%>
<label  for="<%=id %>"  class="fieldLabel"
<%}%>
<% if (labelStyle != null){%>
  style="<%=labelStyle %>"
<%}%>
> 
<%if("true".equals(required)){%>
       <span style="color:red">*</span>
<%}%>
${key}
</label>
<div class="fielddiv2 ec-numberbox 
<%if("true".equals(readOnly) || "true".equals(disabled)){%>
	ec-readonly
<%}%>
">
<input id="${id}" name="dto['${id}']" class="easyui-numberbox" data-options="
<%if(onChange != null){%>
	onChange:eval('<%=onChange %>'),
<%}%>
<%if(readOnly != null){%>
	readonly:<%=readOnly%>,
<%}%>
"
<% if (precision != null){%>
  precision="<%=precision %>"
<%}%>
<% if (value != null){%>
  value="<%=value %>"
<%}%>
<% if (required != null){%>
  required="<%=required %>"
<%}%>
<% if (disabled != null && "true".equals(disabled.trim())){%>
  disabled="<%=disabled %>"
<%}%>
<% if (min != null){%>
  min="<%=min %>"
<%}%>
<% if (max != null){%>
  max="<%=max %>"
<%}%>
<% if (decimalSeparator != null){%>
  decimalSeparator="<%=decimalSeparator %>"
<%}%>
<% if (groupSeparator != null){%>
  groupSeparator="<%=groupSeparator %>"
<%}%>
<% if (prefix != null){%>
  prefix="<%=prefix %>"
<%}%>
<% if (suffix != null){%>
  suffix="<%=suffix %>"
<%}%>
></input></div>
</div>
<script type="text/javascript">
	$(function(){
		var obj = $('#ec-number-${id} .ec-numberbox .textbox-text');
		obj.width($('#ec-number-${id}').width()-$('#ec-number-${id} .fieldLabel').width()-25);
		if($('#ec-number-${id}').width() == 0){
			//obj.css("width","auto");
			obj.width(obj.parent().width()-25);
		}
	});
</script>