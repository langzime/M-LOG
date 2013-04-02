<#import "/META-INF/spring.ftl" as spring />
<#import "ui.ftl" as ui>
<@ui.header title="注册"/>
	<div class="span8 well">
		<h2>用户注册</h2>
		<hr/>
		<@spring.bind "user" />
		<form id="reg_form" class="form-horizontal">
			<div class="control-group">
  				<label class="control-label">用户名：</label>
  				<div class="controls">
  					<@spring.formInput path="user.name" attributes='placeholder="请输入用户名" class="input-xlarge" validate=\'{required:true, messages:{required:"请输入用户名"}}\''/>
    				<p class="help-block">用户名只能包含字母和数字</p>
  				</div>
			</div>
			<div class="control-group">
  				<label class="control-label">昵称：</label>
  				<div class="controls">
  					<@spring.formInput path="user.alias" attributes='placeholder="请输入昵称" class="input-xlarge"'/>
  				</div>
			</div>
			<div class="control-group">
  				<label class="control-label">邮箱：</label>
  				<div class="controls">
  					<@spring.formInput path="user.email" attributes='placeholder="请输入邮箱" class="input-xlarge"'/>
  				</div>
			</div>
			<div class="control-group">
  				<label class="control-label">密码：</label>
  				<div class="controls">
  					<@spring.formPasswordInput path="user.password" attributes='placeholder="请输入密码" class="input-xlarge"'/>
  				</div>
			</div>
			<div class="control-group">
  				<label class="control-label">重复密码：</label>
  				<div class="controls">
    				<input id="repassword" name="repassword" type="text" placeholder="请重复密码" class="input-xlarge" />
  				</div>
			</div>
			<div class="control-group">
  				<div class="controls">
  					<input type="submit" class="btn btn-primary" value="提交"/>
  				</div>
			</div>
		</form>
	</div>
	<div class="span4 well">
	</div>
	<script type="text/javascript" src="${base}/script/jquery.form.js"></script>
    <script type="text/javascript" src="${base}/script/lhgdialog/lhgdialog.min.js"></script>
    <script type="text/javascript" src="${base}/script/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/script/jquery-validation/jquery.metadata.js"></script>
    <script type="text/javascript">
    	mlog.form.validate({
			selector : "#reg_form",
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
<#include "footer.ftl" />