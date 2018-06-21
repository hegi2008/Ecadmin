<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>相册管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>UI/plugins/fancybox/jquery.fancybox.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>UI/plugins/webuploader/webuploader.css">
    <style>
    	.lightBoxGallery .fancybox-box {width: 172px;max-width: 172px;height: 150px;position: relative;float: left;}
    	.lightBoxGallery .fancybox-box .tool{position: absolute;bottom: 5px;left: 0;z-index: 200;width: 162px;padding:5px 0;margin:0 5px;background: rgba( 0, 0, 0, 0.5 );display: none;}
    	.lightBoxGallery .fancybox-box .tool i{margin-left: 5px;font-size: 18px;color: #ccc;cursor: pointer;}
    	.lightBoxGallery .fancybox-box .tool i:HOVER{color: #ed5565;}
    	.lightBoxGallery .fancybox {width: 100%;height: 100%;display: inline-block;}
        .lightBoxGallery .fancybox img {padding: 5px;width: 100%;height: 100%;}
    </style>
  </head>
  <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="row">
            <div class="col-sm-12">
                <div class="ibox ibox float-e-margins">
                    <div class="ibox-content">
                    	<h2>${album.albumName}</h2>
                    	<p>
                    		本相册【<strong>${album.albumName}</strong>】，由&nbsp;<strong>${album.createUserName}</strong>&nbsp;创建。
                    		共有<c:out value="${fn:length(imgs)}"/>张图片。
                    		<a href="default" class="btn btn-primary btn-xs">返回相册管理</a>
                    		<c:if test="${fn:length(imgs) > 0}">
                    			<a id="btn-showUpload" class="btn btn-primary btn-xs" href="javascript:void(0);">点击上传图片</a>
                    		</c:if>
                    	</p>
                    	<div id="uploadBox" class="ibox-content">
	                        <div class="page-container">
	                            <p>建议单张图片大小不要大于1m，可以是jpg、jpeg、png、格式。</p>
	                            <div id="uploader" class="wu-example">
	                                <div class="queueList">
	                                    <div id="dndArea" class="placeholder">
	                                        <div id="filePicker" class="webuploader-container">
	                                        	点击选择图片
	                                        </div>
	                                        <p>或将照片拖到这里，单次最多可选100张</p>
	                                    </div>
	                                </div>
	                                <div class="statusBar" style="display:none;">
	                                    <div class="progress">
	                                        <span class="text">0%</span>
	                                        <span class="percentage"></span>
	                                    </div>
	                                    <div class="info"></div>
	                                    <div class="btns">
	                                        <div id="filePicker2"></div>
	                                        <div class="uploadBtn">开始上传</div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="lightBoxGallery clearfix">
		                    <c:forEach items="${imgs}" var="img">
		                    	<div class="fancybox-box">
			                    	<a href="${imgUrl}${img.imgPath}" title="${img.imgName}" _imgId="${img.imgId}" _albumId="${img.albumId}" rel="group" class="fancybox">
			                    		<img src="${imgUrl}${img.imgPath}">
			                    	</a>
			                    	<div class="tool">
			                    		<i class="fa fa-check" title="设置封面" onclick="setAlbumCover(${img.albumId},${img.imgId})"></i>
			                    		<i class="fa fa-remove" title="删除这张" onclick="deleteImg(${img.imgId},this)"></i>
			                    	</div>
		                    	</div>
		                    </c:forEach>
                    	</div
                    </div>
                </div>
            </div>
        </div>
	</div>
	<script src="<%=basePath%>UI/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>UI/plugins/layer/layer.min.js"></script>
	<script src="<%=basePath%>UI/plugins/moment/moment.min.js"></script>
	<script src="<%=basePath%>UI/themes/hplus/js/content.min.js"></script>
	<script src="<%=basePath%>UI/plugins/fancybox/jquery.fancybox.pack.js"></script>
	<script src="<%=basePath%>UI/plugins/fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
	<script src="<%=basePath%>UI/plugins/webuploader/webuploader.min.js"></script>
	<script src="<%=basePath%>UI/plugins/pace/pace.min.js"></script>
	<script src="<%=basePath%>UI/js/api.base.js"></script>
	<script type="text/javascript">
		function initWebUpload(){
			var $ = jQuery,    // just in case. Make sure it's not an other libaray.

	        $wrap = $('#uploader'),

	        // 图片容器
	        $queue = $('<ul class="filelist"></ul>')
	            .appendTo( $wrap.find('.queueList') ),

	        // 状态栏，包括进度和控制按钮
	        $statusBar = $wrap.find('.statusBar'),

	        // 文件总体选择信息。
	        $info = $statusBar.find('.info'),

	        // 上传按钮
	        $upload = $wrap.find('.uploadBtn'),

	        // 没选择文件之前的内容。
	        $placeHolder = $wrap.find('.placeholder'),

	        // 总体进度条
	        $progress = $statusBar.find('.progress').hide(),

	        // 添加的文件数量
	        fileCount = 0,

	        // 添加的文件总大小
	        fileSize = 0,

	        // 优化retina, 在retina下这个值是2
	        ratio = window.devicePixelRatio || 1,

	        // 缩略图大小
	        thumbnailWidth = 110 * ratio,
	        thumbnailHeight = 110 * ratio,

	        // 可能有pedding, ready, uploading, confirm, done.
	        state = 'pedding',

	        // 所有文件的进度信息，key为file id
	        percentages = {},

	        supportTransition = (function(){
	            var s = document.createElement('p').style,
	                r = 'transition' in s ||
	                      'WebkitTransition' in s ||
	                      'MozTransition' in s ||
	                      'msTransition' in s ||
	                      'OTransition' in s;
	            s = null;
	            return r;
	        })(),

	        // WebUploader实例
	        uploader;

		    if ( !WebUploader.Uploader.support() ) {
		        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
		        throw new Error( 'WebUploader does not support the browser you are using.' );
		    }
		    
		    // 实例化
		    uploader = WebUploader.create({
		        pick: {
		            id: '#filePicker',
		            label: '点击选择图片'
		        },
		        dnd: '#uploader .queueList',
		        paste: document.body,
	
		        accept: {
		            title: 'Images',
		            extensions: 'gif,jpg,jpeg,png',
		            mimeTypes: 'image/*'
		        },
	
		        // swf文件路径
		        swf: Base.globvar.basePath + 'UI/js/plugins/webuploader/Uploader.swf',
	
		        disableGlobalDnd: true,
	
		        chunked: true,
		        fileVal: "imgs",
		        server: "uploadImgs?album_id=${album.albumId}",
		        fileNumLimit: 100,
		        fileSizeLimit: 5 * 1024 * 1024,    // 200 M
		        fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
		    });
	
		    // 添加“添加文件”的按钮，
		     uploader.addButton({
		        id: '#filePicker2',
		        label: '继续添加'
		    });
	
		    // 当有文件添加进来时执行，负责view的创建
		    function addFile( file ) {
		        var $li = $( '<li id="' + file.id + '">' +
		                '<p class="title">' + file.name + '</p>' +
		                '<p class="imgWrap"></p>'+
		                '<p class="progress"><span></span></p>' +
		                '</li>' ),
	
		            $btns = $('<div class="file-panel">' +
		                '<span class="cancel">删除</span>' +
		                '<span class="rotateRight">向右旋转</span>' +
		                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
		            $prgress = $li.find('p.progress span'),
		            $wrap = $li.find( 'p.imgWrap' ),
		            $info = $('<p class="error"></p>'),
	
		            showError = function( code ) {
		                switch( code ) {
		                    case 'exceed_size':
		                        text = '文件大小超出';
		                        break;
	
		                    case 'interrupt':
		                        text = '上传暂停';
		                        break;
	
		                    default:
		                        text = '上传失败，请重试';
		                        break;
		                }
	
		                $info.text( text ).appendTo( $li );
		            };
	
		        if ( file.getStatus() === 'invalid' ) {
		            showError( file.statusText );
		        } else {
		            // @todo lazyload
		            $wrap.text( '预览中' );
		            uploader.makeThumb( file, function( error, src ) {
		                if ( error ) {
		                    $wrap.text( '不能预览' );
		                    return;
		                }
	
		                var img = $('<img src="'+src+'">');
		                $wrap.empty().append( img );
		            }, thumbnailWidth, thumbnailHeight );
	
		            percentages[ file.id ] = [ file.size, 0 ];
		            file.rotation = 0;
		        }
	
		        file.on('statuschange', function( cur, prev ) {
		            if ( prev === 'progress' ) {
		                $prgress.hide().width(0);
		            } else if ( prev === 'queued' ) {
		                $li.off( 'mouseenter mouseleave' );
		                $btns.remove();
		            }
	
		            // 成功
		            if ( cur === 'error' || cur === 'invalid' ) {
		                console.log( file.statusText );
		                showError( file.statusText );
		                percentages[ file.id ][ 1 ] = 1;
		            } else if ( cur === 'interrupt' ) {
		                showError( 'interrupt' );
		            } else if ( cur === 'queued' ) {
		                percentages[ file.id ][ 1 ] = 0;
		            } else if ( cur === 'progress' ) {
		                $info.remove();
		                $prgress.css('display', 'block');
		            } else if ( cur === 'complete' ) {
		                $li.append( '<span class="success"></span>' );
		            }
	
		            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
		        });
	
		        $li.on( 'mouseenter', function() {
		            $btns.stop().animate({height: 30});
		        });
	
		        $li.on( 'mouseleave', function() {
		            $btns.stop().animate({height: 0});
		        });
	
		        $btns.on( 'click', 'span', function() {
		            var index = $(this).index(),
		                deg;
	
		            switch ( index ) {
		                case 0:
		                    uploader.removeFile( file );
		                    return;
	
		                case 1:
		                    file.rotation += 90;
		                    break;
	
		                case 2:
		                    file.rotation -= 90;
		                    break;
		            }
	
		            if ( supportTransition ) {
		                deg = 'rotate(' + file.rotation + 'deg)';
		                $wrap.css({
		                    '-webkit-transform': deg,
		                    '-mos-transform': deg,
		                    '-o-transform': deg,
		                    'transform': deg
		                });
		            } else {
		                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
		                // use jquery animate to rotation
		                // $({
		                //     rotation: rotation
		                // }).animate({
		                //     rotation: file.rotation
		                // }, {
		                //     easing: 'linear',
		                //     step: function( now ) {
		                //         now = now * Math.PI / 180;
	
		                //         var cos = Math.cos( now ),
		                //             sin = Math.sin( now );
	
		                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
		                //     }
		                // });
		            }
	
	
		        });
	
		        $li.appendTo( $queue );
		    }
	
		    // 负责view的销毁
		    function removeFile( file ) {
		        var $li = $('#'+file.id);
	
		        delete percentages[ file.id ];
		        updateTotalProgress();
		        $li.off().find('.file-panel').off().end().remove();
		    }
	
		    function updateTotalProgress() {
		        var loaded = 0,
		            total = 0,
		            spans = $progress.children(),
		            percent;
	
		        $.each( percentages, function( k, v ) {
		            total += v[ 0 ];
		            loaded += v[ 0 ] * v[ 1 ];
		        } );
	
		        percent = total ? loaded / total : 0;
	
		        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
		        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
		        updateStatus();
		    }
	
		    function updateStatus() {
		        var text = '', stats;
	
		        if ( state === 'ready' ) {
		            text = '选中' + fileCount + '张图片，共' +
		                    WebUploader.formatSize( fileSize ) + '。';
		        } else if ( state === 'confirm' ) {
		            stats = uploader.getStats();
		            if ( stats.uploadFailNum ) {
		                text = '已成功上传' + stats.successNum+ '张照片，'+
		                    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片'
		            }
	
		        } else {
		            stats = uploader.getStats();
		            text = '共' + fileCount + '张（' +
		                    WebUploader.formatSize( fileSize )  +
		                    '），已上传' + stats.successNum + '张';
	
		            if ( stats.uploadFailNum ) {
		                text += '，失败' + stats.uploadFailNum + '张';
		            }
		        }
	
		        $info.html( text );
		    }
	
		    function setState( val ) {
		        var file, stats;
	
		        if ( val === state ) {
		            return;
		        }
	
		        $upload.removeClass( 'state-' + state );
		        $upload.addClass( 'state-' + val );
		        state = val;
	
		        switch ( state ) {
		            case 'pedding':
		                $placeHolder.removeClass( 'element-invisible' );
		                $queue.parent().removeClass('filled');
		                $queue.hide();
		                $statusBar.addClass( 'element-invisible' );
		                uploader.refresh();
		                break;
	
		            case 'ready':
		                $placeHolder.addClass( 'element-invisible' );
		                $( '#filePicker2' ).removeClass( 'element-invisible');
		                $queue.parent().addClass('filled');
		                $queue.show();
		                $statusBar.removeClass('element-invisible');
		                uploader.refresh();
		                break;
	
		            case 'uploading':
		                $( '#filePicker2' ).addClass( 'element-invisible' );
		                $progress.show();
		                $upload.text( '暂停上传' );
		                break;
	
		            case 'paused':
		                $progress.show();
		                $upload.text( '继续上传' );
		                break;
	
		            case 'confirm':
		                $progress.hide();
		                $upload.text( '开始上传' ).addClass( 'disabled' );
	
		                stats = uploader.getStats();
		                if ( stats.successNum && !stats.uploadFailNum ) {
		                    setState( 'finish' );
		                    return;
		                }
		                break;
		            case 'finish':
		                stats = uploader.getStats();
		                if ( stats.successNum ) {
		                    Base.confirm("上传成功,是否马上刷新?",function(yes){
		                    	if(yes){
		                    		window.location.reload(true);
		                    	}
		                    });
		                } else {
		                    // 没有成功的图片，重设
		                    state = 'done';
		                    location.reload();
		                }
		                break;
		        }
	
		        updateStatus();
		    }
	
		    uploader.onUploadProgress = function( file, percentage ) {
		        var $li = $('#'+file.id),
		            $percent = $li.find('.progress span');
	
		        $percent.css( 'width', percentage * 100 + '%' );
		        percentages[ file.id ][ 1 ] = percentage;
		        updateTotalProgress();
		    };
	
		    uploader.onFileQueued = function( file ) {
		        fileCount++;
		        fileSize += file.size;
	
		        if ( fileCount === 1 ) {
		            $placeHolder.addClass( 'element-invisible' );
		            $statusBar.show();
		        }
	
		        addFile( file );
		        setState( 'ready' );
		        updateTotalProgress();
		    };
	
		    uploader.onFileDequeued = function( file ) {
		        fileCount--;
		        fileSize -= file.size;
	
		        if ( !fileCount ) {
		            setState( 'pedding' );
		        }
	
		        removeFile( file );
		        updateTotalProgress();
	
		    };
	
		    uploader.on( 'all', function( type ) {
		        var stats;
		        switch( type ) {
		            case 'uploadFinished':
		                setState( 'confirm' );
		                break;
	
		            case 'startUpload':
		                setState( 'uploading' );
		                break;
	
		            case 'stopUpload':
		                setState( 'paused' );
		                break;
	
		        }
		    });
	
		    uploader.onError = function( code ) {
		    	Base.alert("error: "+code);
		        //alert( 'Eroor: ' + code );
		    };
	
		    $upload.on('click', function() {
		        if ( $(this).hasClass( 'disabled' ) ) {
		            return false;
		        }
	
		        if ( state === 'ready' ) {
		            uploader.upload();
		        } else if ( state === 'paused' ) {
		            uploader.upload();
		        } else if ( state === 'uploading' ) {
		            uploader.stop();
		        }
		    });
	
		    $info.on( 'click', '.retry', function() {
		        uploader.retry();
		    } );
	
		    $info.on( 'click', '.ignore', function() {
		        //alert( 'todo' );
		    } );
	
		    $upload.addClass( 'state-' + state );
		    updateTotalProgress();
		}
		
		$(document).ready(function(){
			$(".fancybox").fancybox({
				prevEffect: 'elastic',
		        nextEffect: 'elastic',
		        openEffect: 'elastic',
		        closeEffect: 'elastic'
			});
			
			
			$(".fancybox").on("mouseenter",function(){
				$(this).next(".tool").slideDown();
			}).on("mouseleave",function(){
				$(this).next(".tool").finish().hide();
			});
			
			$(".tool").on("mouseover",function(){
				$(this).show();
			}).on("mouseleave",function(){
				$(this).finish().hide();
			});
			
			var imgsCount = "${imgsCount}";
			if(imgsCount > 0 ){
				hideUpload();
				$("#btn-showUpload").on("click",showUpload);
			}else{
				showUpload();
				$("#btn-showUpload").on("click",hideUpload);
			}
			
		});
		
		function showUpload(){
			initWebUpload();
			$("#uploadBox").fadeIn();
			$("#btn-showUpload").text("关闭上传图片").off("click").on("click",hideUpload);
		}
	
		function hideUpload(){
			$("#uploadBox").hide();
			$("#btn-showUpload").text("打开上传图片").off("click").on("click",showUpload);
		}
		
		function setAlbumCover(album_id,img_id){
			Base.ajax({
				url : "setAlbumCover",
				data : {"album_id": album_id,"img_id":img_id},
				type : 'post',
				success : function(data){
					if(!data.error) {
						
					}
				}
			});
		}
		
		function deleteImg(img_id,obj){
			Base.ajax({
				url : "deleteImg",
				data : {"img_id": img_id},
				type : 'post',
				success : function(data){
					if(!data.error) {
						$(obj).parents(".fancybox-box").remove();
					}
				}
			});
		}
	</script>
  </body>
</html>
