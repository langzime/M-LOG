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
						<th>编号</th>
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
									<a href="${base}/user/comment/auditView?id=${item.id}">审批</a>
								</td>
							</tr>
						</#list>
					</#if>
					<tr>
						<td ><input  type="button" class="btn btn-danger" value="废 弃" data-loading-text="正在提交..." onclick="ctrl();" /></td>
						<td  colspan="7">
							<@mspring.pagingnavigator page=commentPage form_id="commentForm" />
						</td>
					</tr>
				</table>
	    	</form>
	    </div>
	</div>
<script type="text/javascript">
	function delete(){
		mlog.form.submitForm('commentForm', '${base}/user/comment/delete');
	}
</script>
<#include "../footer.ftl" />
