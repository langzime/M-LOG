<#macro header title="">
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="utf-8">
	<title>${title} - ${sitename}</title>
	<link href="${base}/script/bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
			        				<li role="presentation"><a role="menuitem" tabindex="-1" href="#">消息</a></li>
			                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">退出</a></li>
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