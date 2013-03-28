<h5>评论</h5>
<#if (comments?exists && comments?size > 0)>
	<ul class="comment-list">
		<#list comments as comment>
			<li>
				<#assign author = comment.authorEager />
				<div id="comment-${comment.id}" class="comment-body">
					<img class="avatar" alt="${comment.author.alias}" src="<@gravatar email=comment.author.email />"/>
					<div class="comment-author">
						${comment.author.alias}
						<span class="says">说道：</span>
					</div>
					<div class="comment-meta">
						${comment.createTime}
						<a class="comment-reply-link" href="javascript:quote(${comment.id});">回复</a>
					</div>
					<div class="comment-content">
						<#if comment.parent?exists && comment.parent.id != 0>
							<#assign parent = comment.parentEager />
							<div class="quotewap">
								<div class="quotetop">@${parent.author!"蒙面大侠"}</div>
								<div class="quotemain">
									${parent.content!""}
								</div>
							</div>
						</#if>
						${comment.content!""}
					</div>
				</div>
			</li>	
		</#list>
	</ul>
</#if>
<#if post.commentStatus == "open">
	<#if currentUser?exists>
		<form id="comment-form" class="comment-form" target="_self" method="post" action="${base}/comment/post">
			<input type="hidden" name="postId" value="${postId}" />
			<input type="hidden" name="reply_comment" id="reply_comment" />
			<div class="item">
				<textarea name="content" id="comment_content"></textarea>
			</div>
			<div class="item">
				<input type="button" class="btn btn-primary" value="发表评论" id="btnPostComment" onclick="doSubmit()" />
			</div>
		</form>
		<script type="text/javascript" src="${base}/script/kindeditor/kindeditor.js" charset="utf-8"></script>
		<script type="text/javascript">
			mlog.initEditor({
				selector : "comment_content",
				width : "700px"
			});
			
			function doSubmit(){
				lockSubmit();
				$("#comment-form").submit();
			}
			
			function lockSubmit(){
				$("#btnPostComment").val('正在提交...');
				$("#btnPostComment").attr('disabled', 'disabled');
				$("#btnPostComment").addClass('disabled');
			}
		</script>
	<#else>
		请先登录
	</#if>
<#else>
	<h2>评论已关闭</h2>
</#if>