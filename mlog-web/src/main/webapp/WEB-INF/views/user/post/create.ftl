<#import "/META-INF/spring.ftl" as spring />
<#import "../ui.ftl" as ui>
<@ui.header title="发表文章" />
	<@ui.sidebar active="发表文章" />
	<div class="span10">
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="${siteurl}">首页</a> <span class="divider">/</span></li>
			<li><a href="${base}/user/profile">管理</a> <span class="divider">/</span></li>                  
			<li class="active">发表文章</li>
	    </ul>
	    <div class="row-fluid">
	    	<form action="${base}/user/post/create/save" method="post" class="form-vertical" id="post_form">
	    		<@spring.bind "post" />
		    	<div class="span8">
		    		<@spring.formHiddenInput path="post.status" />
			    	<@spring.formInput path="post.title" attributes='maxlength="80" style="width:98%;" placeholder="此处输入文章标题" validate=\'{required:true, messages:{required:"请输入文章标题"}}\''/>
			    	<@spring.formTextarea path="post.content" attributes='style="width: 100%; height: 450px; display: none;"' />
		    	</div>
				<div class="span4 row-fluid">
					<div class="widget-box">
						<div class="widget-title">文章分类</div>
						<div class="widget-content" style="max-height:200px;">
							<ul class="unstyled">
								<#list catalogs as catalog>
									<li><label><input type="checkbox" name="catalogs" value="${catalog.id}" id="catalog_${catalog.id}"/> ${catalog.name}</label></li>
								</#list>
							</ul>
						</div>
					</div>
					<div class="widget-box">
						<div class="widget-title">文章选项</div>
						<div class="widget-content">
							<div class="control-group">
	    						<label class="text-info" for="password">访问密码：</label>
	    						<div class="controls">
		      						<@spring.formInput path="post.password" attributes='style="width:90%;"' />
		    					</div>
							</div>
							<div class="control-group">
	    						<label class="text-info" for="site">文章来源：</label>
	    						<div class="controls">
		      						<@spring.formInput path="post.site" attributes='style="width:90%;"' />
		    					</div>
							</div>
							<div class="control-group">
	    						<label class="text-info" for="url">来源地址：</label>
	    						<div class="controls">
		      						<@spring.formInput path="post.url" attributes='style="width:90%;"' />
		    					</div>
							</div>
						</div>
					</div>
				</div>
				<div class="span12">
					<span style="float:center;display: inline-block;">
    					<input id="post_add" type="button" class="btn btn-small btn-primary" value="发   表" data-loading-text="正在提交..." />
    					<input id="save_as_draft" type="button" class="btn btn-small btn-primary" value="存为草稿" data-loading-text="正在提交..." />
					</span>
				</div>
			</form>
		</div>
    </div>
    <script type="text/javascript" src="${base}/script/jquery.form.js"></script>
    <script type="text/javascript" src="${base}/script/lhgdialog/lhgdialog.min.js"></script>
    <script type="text/javascript" src="${base}/script/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/script/jquery-validation/jquery.metadata.js"></script>
    <script type="text/javascript">
    	mlog.editor.init({
			type : "kindeditor",
			model : "all",
			id : "content"
		});
		
    	$(function(){
    		//发表
    		$('#post_add').click(function(){
    			if($("#post_form").valid()){
    				var _this = this;
    				$(_this).button('loading');
	    			var tip = mlog.dialog.tip({
	    				msg : '正在提交...',
	    				lock : true
	    			});
	    			$('#post_form').ajaxSubmit({
	    				success: function(){
	    					tip.close();
	    					$(_this).button('reset');
	    					window.location = '${base}/user/post/list';
	    				}
	    			});
    			}
    		});
    		
    		$('#save_as_draft').click(function(){
    			var _this = this;
				$(_this).button('loading');
    			var tip = mlog.dialog.tip({
    				msg : '正在提交...',
    				lock : true
    			});
    			$('#status').val('draft');
    			$('#post_form').ajaxSubmit({
    				success: function(){
    					tip.close();
    					$(_this).button('reset');
    					window.location = '${base}/user/post/drafts';
    				}
    			});
    		});
    	});
    	
    	mlog.form.validate({
			selector : "#post_form",
			errorLabelContainer : "#error",
			wrapper: 'li',
			onfocusout : false,
			onkeyup : false,
			onclick : false,
			showErrors : function(errorMap, errorList) {
				var msg = "";
				$.each(errorList, function(i, v) {
					msg += (v.message + "<br />");
				});
				if(msg){
					var tip = mlog.dialog.tip({
	    				msg : msg,
	    				type : 'error',
	    				time : 1
	    			});
				}
			}
		});
    </script>
<#include "../footer.ftl" />