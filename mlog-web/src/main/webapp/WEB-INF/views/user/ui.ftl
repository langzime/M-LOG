<#macro sidebar active="个人中心">
	<div class="span2">
		<div class="back-left">
			<ul class="nav nav-tabs nav-stacked">
				<li <#if active="个人中心">class="active"</#if>><a href="${base}/user/profile">个人中心<i class="icon-chevron-right"></i></a></li>
				<li <#if active="发表文章">class="active"</#if>><a href="${base}/user/post/create">发表文章<i class="icon-chevron-right"></i></a></li>
				<li <#if active="文章管理">class="active"</#if>><a href="${base}/user/post/list">文章管理<i class="icon-chevron-right"></i></a></li>
				<li <#if active="草稿箱">class="active"</#if>><a href="${base}/user/post/drafts">草稿箱<i class="icon-chevron-right"></i></a></li>
				<li <#if active="回收站">class="active"</#if>><a href="${base}/user/post/trash">回收站<i class="icon-chevron-right"></i></a></li>
				<li <#if active="分类管理">class="active"</#if>><a href="${base}/user/catalog/list">分类管理<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/comments">博客评论<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/visits">访客记录<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/privacy">隐私管理<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/profile">个人资料<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/portraint">头像管理<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
				<li><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
			</ul>
		</div>
	</div>
</#macro>

<#macro header title=currentUser.alias>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="utf-8">
	<title>${title} - ${sitename}</title>
	<link href="${base}/script/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${base}/style/global.css">
	<link href="${base}/script/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="${base}/style/user.css" rel="stylesheet">
	<script type="text/javascript" src="${base}/script/jquery.js"></script>
	<script type="text/javascript" src="${base}/script/script_variable.js"></script>
	<script type="text/javascript" src="${base}/script/m-log.js"></script>
	<script type="text/javascript" src="${base}/script/custom.js"></script>
	
	<#-- jquery validate -->
	<script type="text/javascript" src="${base}/script/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/script/jquery-validation/jquery.metadata.js"></script>
	<script type="text/javascript" src="${base}/script/jquery-validation/validate.method.js"></script>
	
	<!--[if lt IE 9]>
    <script src="${template_url}/script/html5.js"></script>
    <![endif]-->
	<!--[if IE 6]>
	<script src="${base}/script/killie6/killie6.js"></script>
	<![endif]-->
	<style type="text/css">
		body {
			font-family: Arial, 'Microsoft YaHei';
		}
	</style>
	</head>
	<body>
	    <style type="text/css">
		body { padding-top: 60px;}
		</style>
	    <div class="navbar navbar-fixed-top <#-- navbar-inverse -->">
	  		<div class="navbar-inner">
	    		<div class="container-fluid">
	          		<a class="brand" href="${siteurl}" target="_blank">${sitename}</a>
	          		<div class="nav-collapse">
	          			<ul class="nav">
		              		<li class="">
		                		<a href="#"></a>
		                	</li>
		              		<li class="">
		            	</ul>
		            	<ul class="nav pull-right">
		            		<li class="dropdown">
			        			<a id="my_toolbar" class="dropdown-toggle" data-toggle="dropdown">${currentUser.alias} <b class="caret"></b></a>
			        			<ul class="dropdown-menu" role="menu" aria-labelledby="my_toolbar">
			        				<li role="presentation"><a role="menuitem" tabindex="-1" href="${base}/user/profile">个人中心</a></li>
			        				<li role="presentation"><a role="menuitem" tabindex="-1" href="#">我的主页</a></li>
			                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">个人资料</a></li>
		                        </ul>
			        		</li>
		            	</ul>
	          		</div>
				</div>
			</div>
		</div>
		
		<div class="container-fluid">
			<div class="row-fluid">
</#macro>