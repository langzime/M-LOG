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
	    <div class="back-right-inner">
	        <form action="${base}/user/post/create/save" method="post" class="form-inline" id="post_form"> 
	        	<@spring.bind "post" />
        		<table class="form-table">
        			<tr>
        				<td class="field-title"><span class="text-info">标题(必填)</span></td>
        				<td class="field-content"><@spring.formInput path="post.title" attributes='maxlength="80" style="width:95%;" placeholder="此处输入文章标题" validate=\'{required:true, messages:{required:"请输入文章标题"}}\''/></td>
				      	<td class="field-content" colspan="2" rowspan="4" style="width:100px;">
				      		<div style="width:100%;height;100%;">
				      			<#list catalogs as catalog>
				      				<input type="checkbox" value="${catalog.id}" id="catalog_${catalog.id}"/><label for="catalog_${catalog.id}">${catalog.name}</label></br>
				      			</#list>
				      		</div>
				      	</td>
        			</tr>
					<tr>
				      	<td class="field-title"><span class="text-info">访问密码</span></td>
				      	<td class="field-content"><@spring.formInput path="post.password" attributes='style="width:95%;"' /></td>
				      	
			      	</tr>
				    <tr>
				    	<td class="field-title"><span class="text-info">文章来源</span></td>
				      	<td class="field-content"><@spring.formInput path="post.site" attributes='style="width:95%;"' /></td>
				    </tr>
				    <tr>
				    	<td class="field-title"><span class="text-info">来源地址</span></td>
				      	<td class="field-content"><@spring.formInput path="post.url" attributes='style="width:95%;"' /></td>
				    </tr>
				</table>
        		<div>
        			<p class="text-info">内容(必填)
        				<span style="float:right;display: inline-block;">
        					<input id="post_add" type="button" class="btn btn-small btn-primary" value="发   表" data-loading-text="正在提交..." />
        					<input id="save_as_draft" type="button" class="btn btn-small btn-primary" value="存为草稿" data-loading-text="正在提交..." />
    					</span>
					</p>
				</div>
				<@spring.formTextarea path="post.content" attributes='style="width: 100%; height: 400px; display: none;"' />
        		<#--
        		<@spring.formRadioButtons path="post.commentStatus" options=commentStatus defaultValue="open" separator="&nbsp;" />
        		<@spring.formRadioButtons path="post.isTop" options=isTop defaultValue="false" separator="&nbsp;" />
        		-->
            </form>
	    </div>
    </div>
    <script type="text/javascript" src="${base}/script/jquery.form.js"></script>
    <script type="text/javascript" src="${base}/script/lhgdialog/lhgdialog.min.js"></script>
    <script type="text/javascript" src="${base}/script/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/script/jquery-validation/jquery.metadata.js"></script>
	<link href="http://ivaynberg.github.com/select2/select2-3.3.1/select2.css" rel="stylesheet"/>
	<script src="http://ivaynberg.github.com/select2/select2-3.3.1/select2.js"></script>
    <script type="text/javascript">
    	mlog.editor.init({
			type : "kindeditor",
			model : "all",
			id : "content"
		});
		
    	$(function(){
    		$('#post_add').click(function(){
    			if($("#post_form").valid()){
    				$(this).button('loading');
	    			var tip = mlog.dialog.tip({
	    				msg : '正在提交...',
	    				lock : true
	    			});
	    			$('#post_form').ajaxSubmit({
	    				success: function(){
	    					$(this).button('loading');
	    					tip.close();
	    				}
	    			});
    			}
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