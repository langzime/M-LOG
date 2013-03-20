<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Site</title>
<link href="${base}/script/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${base}/script/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${base}/style/user.css" rel="stylesheet">
<script type="text/javascript" src="${base}/script/jquery.js"></script>
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
    <style type="text/css">
	body { padding-top: 60px;}
	</style>
    <div class="navbar navbar-fixed-top navbar-inverse">
  		<div class="navbar-inner">
    		<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
          		</a>
          		<a class="brand" href="#">Site</a>
          		<div class="nav-collapse">
          			<ul class="nav">
	              		<li class="">
	                		<a href="#"></a>
	                	</li>
	              		<li class="">
	            	</ul>
	            	<ul class="nav pull-right">
	            		<li class="">
			                <a href="#">Admin</a>
						</li>
						<li class="divider-vertical"></li>
	            	</ul>
          		</div>
			</div>
		</div>
	</div>
	
	
	<#--
	<header class="jumbotron subhead" id="overview">
  		<div class="container">
		    <h2>${blogname}</h2>
		    <p class="lead">${blogsubname}</p>
  		</div>
	</header>
	-->


	<div class="container-fluid">
		<div class="row-fluid">
			<#include "sidebar.ftl"/>
				<div class="span10">