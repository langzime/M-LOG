<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>评论信息</title>
	<link rel="stylesheet" type="text/css" href="${base}/style/global.css">
	<script type="text/javascript" src="${base}/script/jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//斑马线
			var tables=document.getElementsByTagName("table");
			var b=false;
			for (var j = 0; j < tables.length; j++){
				var cells = tables[j].getElementsByTagName("tr");
				//cells[0].className="color3";
				b=false;
				for (var i = 0; i < cells.length; i++){
					if(b){
						cells[i].className="color2";
						b=false;
					}
					else{
						cells[i].className="color3";
						b=true;
					};
				};
			}
		});
	</script>
</head>
<body>
	<#assign post=comment.postEager />
	<table style="width:100%;">
		<tr>
			<td class="fieldlabel" style="width:60px;">编号：</td>
			<td>${comment.id?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">评论作者：</td>
			<td>${comment.author.alias?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">评论时间：</td>
			<td>${comment.createTime?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">IP：</td>
			<td>${comment.postIp?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">Agent：</td>
			<td>${comment.agent?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">状态：</td>
			<td>
				<#if comment.status = "approved">
					<font color="green">审核通过</font>
				<#elseif comment.status = "wait_for_approve">
					<font color="red">等待审核</font>
				<#elseif comment.status = "spam">
					<font color="gray" style="TEXT-DECORATION: line-through">垃圾评论</font>
				<#elseif comment.status = "recycle">
					回收站
					</#if>
			</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">评论文章：</td>
			<td>${comment.post.title?default("")}</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">评论内容：</td>
			<td>${comment.content?default("")}</td>
		</tr>
		<#--
		<tr>
			<td colspan="2" style="text-align:center;">
				<button class="btn">审核通过</button>
				<button class="btn">标记为垃圾评论</button>
				<button class="btn">移入回收站</button>
				<button class="btn">测底删除</button>
			</td>
		</tr>
		-->
	</table>
</body>
</html>