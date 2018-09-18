var introduction_keditor;
var treatment_keditor;
var diagnosis_keditor;
var cause_keditor;
$(document).ready(function() {
	var  u = new useKindEditor();
	u.main();
	addSymptoms.init();
});
var addSymptoms = {
		init: function() {
			this.formHandle();
			this.loadCategorys();
		},
		loadCategorys: function() {
            $('#cate_name').combotree({
                url:Base.globvar.basePath + "shh/symptoms/symptomsManager/getBodys",
                onChange :function(){
                    $val =  $("#cate_name").combotree('getValue');
                    $("#cate_id").val($val);
                }
            });
		},
		formHandle: function() {
			$("#form_inline").bootstrapValidator({
				submitHandler: function(validator, form, submitButton){
					debugger;
					var cate_id = $("#cate_id").val();
					if(cate_id == "") {
						Base.alert("请选择部位！");
						return;
					}
					var symptoms_id = $("#symptoms_id").val();
					var url = Base.globvar.basePath + "shh/symptoms/symptomsManager/saveSymptoms";
					if(symptoms_id != "") {
						url = Base.globvar.basePath + "shh/symptoms/symptomsManager/updateSymptoms";
					}
		        	var paramString = $("#form_inline").serializeArray();
		        	paramString.push({
		        		name: "introduction",
		        		value: introduction_keditor.html()
		        	});
					paramString.push({
		        		name: "treatment",
		        		value: treatment_keditor.html()
		        	});
                    paramString.push({
                        name: "diagnosis",
                        value: diagnosis_keditor.html()
                    });
                    paramString.push({
                        name: "cause",
                        value: cause_keditor.html()
                    });
		        	Base.ajax({
		        		url: url,
		        		data: paramString,
		        		type: 'post',
		        		dataType : 'json',
		        		success : function(data, textStatus) {
		        			if(data.error == false && data.type == "add") {
		        				Base.alert("新增症状成功， 你可以继续添加！");
		        				$("#form_inline")[0].reset();
		        				$("#cate_id").val("");
                                introduction_keditor.html("");
                                treatment_keditor.html("");
                                diagnosis_keditor.html("");
                                cause_keditor.html("");
		        				$('#form_inline').bootstrapValidator('resetForm', true);
		        			} else if(data.error == false && data.type == "edit") {
		        				Base.alert("编辑症状成功!");
		        			}
						}
		        	});
		        },
				fields: {
		            title: {
		            	validators: {
		            		notEmpty: {
		            			message: '请输入症状'
		            		},
		            		stringLength: {
		            			max: 50,
		            			message: '症状的最大长度为50'
		            		}
		            	}
		            },
                    recommend_deptid: {
		            	validators: {
		            		stringLength: {
		            			max: 15,
		            			message: '科室编号的最大长度为15'
		            		}
		            	}
		            },
                    recommend_dept: {
		            	validators: {
		            		stringLength: {
		            			max: 25,
		            			message: '标题的最大长度为25'
		            		}
		            	}
		            },
                    sort_order: {
                        notEmpty: {
                            message: '请输入序号'
                        },
		            	validators: {
		            		stringLength: {
		            			max: 10,
		            			message: '序号最大长度为10'
		            		}
		            	}
		            },
		        },
				
			});
		}
}
//kindeditor
function useKindEditor() {};
useKindEditor.prototype = function() {
	function main() {
		KindEditor.ready(function(K) {
            introduction_keditor = K.create('#introduction', {
                allowFlashUpload:false,
                allowImageUpload:false,
                allowMediaUpload:false,
				keepNbsp: true,
			});
            treatment_keditor = K.create('#treatment', {
                allowFlashUpload:false,
                allowImageUpload:false,
                allowMediaUpload:false,
				keepNbsp: true,
			});
            diagnosis_keditor = K.create('#diagnosis', {
                allowFlashUpload:false,
                allowImageUpload:false,
                allowMediaUpload:false,
                keepNbsp: true,
            });
            cause_keditor = K.create('#cause', {
                allowFlashUpload:false,
                allowImageUpload:false,
                allowMediaUpload:false,
                keepNbsp: true,
            });
		});
	}
	return {
		main: main
	}
}();
