<!DOCTYPE html>
	<html>
	<head>
	<meta charset="utf-8">
	<title>dwr</title>
	<script type='text/javascript' src='/mlog-web/dwr/interface/dwrPush.js'></script>
  	<script type='text/javascript' src='/mlog-web/dwr/engine.js'></script>
  	<script type='text/javascript' src='/mlog-web/dwr/util.js'></script>
  	<script>
	   	function receiveMessages(message) {
	   		var c = document.getElementById('content');
	   		c.value = '\n' + message + c.value
		}
	</script>
	</head>
   <body onload="dwr.engine.setActiveReverseAjax(true);">
   内容：
	<textarea id="content" style="width:100%; height:300px;"></textarea>	
  </body>
</html>