<#import "/META-INF/spring.ftl" as spring/>
<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<#import "../ui.ftl" as ui>
<@ui.header title="评论管理"/>
	<@ui.sidebar active="评论管理"/>
	<div class="span10">
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="${siteurl}">首页</a> <span class="divider">/</span></li>
			<li><a href="${base}/user/profile">管理</a> <span class="divider">/</span></li>                  
			<li class="active">评论管理</li>
	    </ul>
	    <div class="row-fluid">
	    	<form id="commentForm" name="commentForm" action="${base}/user/comment/list" method="POST">
	    		<@spring.bind "comment" />
				<table  style="margin-bottom:0px" >
					<tr height="25px">
						<td class="fieldlabel" style="width:70px;">评论状态</td>
						<td >
							<@spring.formSingleSelect path="comment.status" 
							
							options=commentStatus attributes='onchange="changeStatus();" style="width:120px;margin-bottom: 0px;height:26px;font-size:12px;color:#555"' />
						</td>
						
						<td class="fieldlabel" style="width:70px;">评论内容</td>
						<td>
							<@spring.formInput path="comment.content" attributes='class="textinput" style="width:120px ;margin-bottom: 0px;height:15px;"' />
						</td>
						<td class="fieldlabel" style="width:70px;">文章标题</td>
						<td><@spring.formInput path="comment.post.title" attributes='class="textinput" style="width:120px ;margin-bottom: 0px;height:15px"' /></td>
						
						<td><input type="submit" class="btn btn-small btn-primary" value=" 查 询 " /></td>
					</tr>
				</table>
	    		<@spring.bind "commentPage"/>
	    		<!-- pagination parameter -->
				<@spring.formHiddenInput path="commentPage.pageNo" />
				<@spring.formHiddenInput path="commentPage.totalPages" />
				<@spring.formHiddenInput path="commentPage.totalCount" />
				<!-- sorter parameter -->
				<@spring.formHiddenInput path="commentPage.sortEnable" />
				<@spring.formHiddenInput path="commentPage.sort.field" />
				<@spring.formHiddenInput path="commentPage.sort.order" />
				<table class="table table-bordered table-hover " cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<input type="checkbox" onclick="mlog.form.checkAll(this, 'id');" />
						</th>
						<th>序号</th>
						<th>文章标题</th>
						<th>评论内容</th>
						<th>评论时间</th>
						<th>作者</th>
						<th>状态</th>
						<th>
							操作
						</th>
					</tr>
					<#if commentPage?exists>
						<#list commentPage.result as item>
							<#assign tdClass = "odd">
							<#if item_index%2 == 0>
								<#assign tdClass = "even">
							</#if>
							<#assign post=item.postEager />
							<tr>
								<td class="${tdClass}"><input type="checkbox" name="id" value="${item.id}" /></td>
								<td class="${tdClass}">${item_index+1}</td>
								<td class="${tdClass}"><a href="<@postUrl post="post"/>"title="${post.title}" target="_blank">${post.title}</a></td>
								<td class="${tdClass}"><@substring str=item.content endIndex=50 /></td>
								<td class="${tdClass}">${item.createTime}</td>
								<td class="${tdClass}">${item.author.alias?default("")}</td>
								<!--状态 -->
								<td class="${tdClass}">
									<#if item.status = "approved">
										<font color="green">审核通过</font>
									<#elseif item.status = "wait_for_approve">
										<font color="red">等待审核</font>
									<#elseif item.status = "spam">
										<font color="gray" style="TEXT-DECORATION: line-through">垃圾评论</font>
									<#elseif item.status = "recycle">
										回收站
									</#if>
								</td>
								<td class="${tdClass}">
									<a href="javascript:viewComment('${item.id}')">查看详情</a>
								</td>
							</tr>
						</#list>
					</#if>
				</table>
				<div style="float:left;">
					<input  type="button" class="btn btn-danger" style="background-color:green"  value="审核通过" 
					data-loading-text="正在提交..." onclick="mlog.form.confirmSubmit('commentForm', '${base}/user/comment/audit?type=approved', '确认审核通过？');" />
					<input  type="button" class="btn btn-danger" value="标记为垃圾评论" 
					data-loading-text="正在提交..." onclick="mlog.form.confirmSubmit('commentForm', '${base}/user/comment/audit?type=spam', '确认标记为垃圾评论？');" />
					<input  type="button" class="btn btn-danger" style="background-color:gray" value="移入回收站" 
					data-loading-text="正在提交..."  onclick="mlog.form.confirmSubmit('commentForm', '${base}/user/comment/audit?type=recycle', '确认移入回收站？');" />
					<input  type="button" class="btn btn-danger" style="background-color:black"  value="彻底删除" 
					data-loading-text="正在提交..." onclick="mlog.form.confirmSubmit('commentForm', '${base}/user/comment/delete');" />
				</div>
				<div style="float:right;">
					<@mspring.pagingnavigator page=commentPage form_id="commentForm" />
				</div>
	    	</form>
	    </div>
	</div>
<script type="text/javascript">
	function deleteComment(){
		mlog.form.submitForm('commentForm', '${base}/user/comment/delete');
	}
	
	function changeStatus(){
		$('#commentForm').submit();
	}
	
	function viewComment(id){
			mlog.dialog.showModelDialog({
			title : '评论详情',
			content : 'url:${base}/user/comment/view?id=' + id,
			width : '500px'
		});
	}
</script>
<#include "../footer.ftl" />
