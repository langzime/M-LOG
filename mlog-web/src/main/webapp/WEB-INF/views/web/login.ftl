<#import "ui.ftl" as ui>
<@ui.header title="登录"/>
<style type="text/css">
	*{margin:0;padding: 0;}
	body{background: #444 url(http://sandbox.runjs.cn/uploads/rs/418/nkls38xx/carbon_fibre_big.png)}
	.loginBox{width:420px;height:280px;padding:0 20px;border:1px solid #fff; color:#000; margin-top:40px; border-radius:8px;background: white;box-shadow:0 0 15px #222; background: -moz-linear-gradient(top, #fff, #efefef 8%);background: -webkit-gradient(linear, 0 0, 0 100%, from(#f6f6f6), to(#f4f4f4));font:11px/1.5em 'Microsoft YaHei' ;position: absolute;left:50%;top:50%;margin-left:-210px;margin-top:-115px;}
	.loginBox h2{height:45px;font-size:20px;font-weight:normal;}
	.loginBox .left{border-right:1px solid #ccc;height:100%;padding-right: 20px; }
</style>
<div class="container">
	<form action="${base}/admin/doSecurity" method="post" >
		<input type="hidden" name="targetUrl" value="/user/profile"/>
		<input type="hidden" name="failureUrl" value="/login"/>
		<section class="loginBox row-fluid">
			<section class="span7 left">
				<h2>用户登录</h2>
	        	<p><input type="text" id="name" name="name" autofocus="autofocus" required="required" autocomplete="off" placeholder="请输入用户名"/></p>
	        	<p><input type="password" id="password" name="password" required="required" placeholder="请输入密码" /></p>
	        	<section class="row-fluid">
	        		<section class="span8">
	        			<input type="text" name="validateCode" required="required" placeholder="请输入验证码" style="width:80%" />
	        		</section>
	        		<section class="span2">
	        			<a href="javascript:changeImg();"><img id="validateCode" src="${base}/common/validateCode" style="max-width:100px;"/></a>
	        		</section>
	    		</section>
	        	<section class="row-fluid">
	        		<section class="span8 lh30">
	        			<label><input type="checkbox" name="rememberMe" />下次自动登录</label>
	        		</section>
	      			<section class="span1"><input type="submit" value=" 登录 " class="btn btn-primary"></section>
	  			</section>
	  			<#if SPRING_SECURITY_LAST_EXCEPTION?has_content>
				<p style="color:red;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
				</#if>
			</section>
			<section class="span5 right">
				<h2><a href="#">没有帐户？</a></h2>
				<section>
					<p>欢迎登录！</p>
					<p><input type="button" value=" 注册 " class="btn"></p>
				</section>
			</section>
		</section>
	</form>
</div>
<script type="text/javascript">
	function changeImg(){
		var img = document.getElementById('validateCode');
		img.src = "${base}/common/validateCode";
	}
</script>
<#include "footer.ftl" />