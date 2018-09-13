var keditor;
$(document).ready(function() {
	var  u = new useKindEditor();
	u.main();
	addArticle.init();
});
var addArticle = {
		init: function() {
			this.formHandle();
			this.loadCategorys();
			this.determineOperate();
			this.auditArticle();
		},
		determineOperate: function() {
			var type = $("#type_").val();
			var status = $("#type_").attr("status");
			if(type == "audit") {
				$(".audit-btn").show();
				$(".audit-btn[status='"+status+"']").hide();
				$("#submit_btn").hide();
			}
		},
		loadCategorys: function() {
			var url = Base.globvar.basePath + "shh/article/articleManager/getCategorys";
			$('#cate_tree').tree({    
			    url: url,
			    onClick: function(node){
					$("#cate_name").val(node.text);
					$("#cate_id").val(node.id);
					$("#cate_tree").hide();
				}
			}); 
			//选择文章分类
			$("#cate_name").click(function() {
				if($("#cate_tree").css("display") != "block") {
					$("#cate_tree").show();
				} else {
					$("#cate_tree").hide();
				}
			});
		},
		//审核文章
		auditArticle: function() {
			var url = Base.globvar.basePath + "shh/article/articleManager/updateArticle";
			$(".audit-btn").click(function() {
				var status = $(this).attr("status");
				Base.ajax({
	        		url: url,
	        		data: {article_id: $("#article_id").val(), status: status},
	        		type: 'post',
	        		dataType : 'json',
	        		success : function(data, textStatus) {
	        			if(data.error == false) {
	        				if(status == 2) {
	        					Base.alert("审核文章成功！");
	        				} else if(status == 4) {
	        					Base.alert("文章已经置为无效！");
	        				}
	        				Base.closeIframe();
	        			}
					}
	        	});
			});
		},
		formHandle: function() {
			$("#form_inline").bootstrapValidator({
				submitHandler: function(validator, form, submitButton){
					var cate_id = $("#cate_id").val();
					if(cate_id == "") {
						Base.alert("请选择文章分类！");
						return;
					}
					if($("#publish_time").val() == "") {
						Base.alert("请选择发布时间！");
						return;
					}
					var article_id = $("#article_id").val();
					var url = Base.globvar.basePath + "shh/article/articleManager/saveArticle";
					if(article_id != "") {
						url = Base.globvar.basePath + "shh/article/articleManager/updateArticle";
					}
		        	var paramString = $("#form_inline").serializeArray();
		        	paramString.push({
		        		name: "content",
		        		value: keditor.html()
		        	})
		        	//$("#kind_content").val(KindEditor('#kind_content').val());
		        	Base.ajax({
		        		url: url,
		        		data: paramString,
		        		type: 'post',
		        		dataType : 'json',
		        		success : function(data, textStatus) {
		        			if(data.error == false && data.type == "add") {
		        				Base.alert("新增文章成功， 你可以继续添加！");
		        				$("#form_inline")[0].reset();
		        				$("#cate_id").val("");
		        				keditor.html("");
		        				$('#form_inline').bootstrapValidator('resetForm', true);
		        			} else if(data.error == false && data.type == "edit") {
		        				Base.alert("编辑文章成功!");
		        			}
						}
		        	});
		        },
				fields: {
		            title: {
		            	validators: {
		            		notEmpty: {
		            			message: '请输入文章标题'
		            		},
		            		stringLength: {
		            			max: 50,
		            			message: '标题的最大长度为50'
		            		}
		            	}
		            },
		            keywords: {
		            	validators: {
		            		stringLength: {
		            			max: 50,
		            			message: '标题的最大长度为50'
		            		}
		            	}
		            },
		            link: {
		            	validators: {
		            		stringLength: {
		            			max: 100,
		            			message: '标题的最大长度为100'
		            		}
		            	}
		            },
		            fromto: {
		            	validators: {
		            		stringLength: {
		            			max: 100,
		            			message: '文章摘自于的最大长度为100'
		            		}
		            	}
		            },
		        },
				
			});
			//日期组件初始化
			$('#publish_time').datetimepicker({
				format: "yyyy-mm-dd",
                language: 'zh-CN',
                weekStart: 1,
                todayBtn: 1,//显示‘今日’按钮
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,  //Number, String. 默认值：0, 'hour'，日期时间选择器所能够提供的最精确的时间选择视图。
                clearBtn:true,//清除按钮
                forceParse: 0
			}).on('dp.change dp.show', function(e) {
		    });
		}
}
//kindeditor
function useKindEditor() {};
useKindEditor.prototype = function() {
	function main() {
		KindEditor.ready(function(K) {
			keditor = K.create('#kind_content', {
				// uploadJson: Base.globvar.basePath + "shh/article/articleManager/upLoadFile",
				// afterUpload : function(url) {
                 //    Base.alert("上传图片成功！");
				// },
                allowFlashUpload:false,
                allowImageUpload:false,
                allowMediaUpload:false,
				keepNbsp: true,
			});
			//上传附件
			// var uploadbutton = K.uploadbutton({
			//         button : K('#file_url_button')[0],
			//         fieldName : 'imgFile',
			//         url : Base.globvar.basePath + "shh/article/articleManager/upLoadFuJian",
			//         afterUpload : function(data) {
			//         		Base.removeMask();
			//                 if (data.error === 0) {
			//                         $("#file_url_value").val(data.url);
			//                         $("#file_url").val(data.file_url);
			//                 } else {
			//                         Base.alert(data.message);
			//                 }
			//         }
			// });
			// uploadbutton.fileBox.change(function(e) {
			// 	Base.showMask();
		     //    uploadbutton.submit();
			// });
		});
	}
	return {
		main: main
	}
}();
