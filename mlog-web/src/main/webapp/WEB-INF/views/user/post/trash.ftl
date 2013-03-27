<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<#import "../ui.ftl" as ui>
<@ui.header title="回收站" />
	<@ui.sidebar active="回收站" />
	<div class="span10">
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="${siteurl}">首页</a> <span class="divider">/</span></li>
			<li><a href="${base}/user/profile">管理</a> <span class="divider">/</span></li>                  
			<li class="active">文章管理</li>
	    </ul>
	    <div class="row-fluid">
	    	<form id="postForm" name="postForm" action="${base}/user/post/list" method="POST">
		    	<@spring.bind "postPage" />
				<!-- pagination parameter -->
				<@spring.formHiddenInput path="postPage.pageNo" />
				<@spring.formHiddenInput path="postPage.totalPages" />
				<@spring.formHiddenInput path="postPage.totalCount" />
				<!-- sorter parameter -->
				<@spring.formHiddenInput path="postPage.sortEnable" />
				<@spring.formHiddenInput path="postPage.sort.field" />
				<@spring.formHiddenInput path="postPage.sort.order" />
				<table class="table table-bordered table-hover " cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<input type="checkbox" onclick="mlog.form.checkAll(this, 'id');" />
						</th>
						<th>编号</th>
						<th>标题</th>
						<th>分类</th>
						<th>创建时间</th>
						<th>置顶？</th>
						<th>
							操作
						</th>
					</tr>
					<#if postPage??>
						<#list postPage.result as item>
							<#assign tdClass = "odd">
							<#if item_index%2 == 0>
								<#assign tdClass = "even">
							</#if>
							<tr>
								<td class="${tdClass}"><input type="checkbox" name="id" value="${item.id}" /></td>
								<td class="${tdClass}">${item.id}</td>
								<td class="${tdClass}"><a href="<@postUrl post="item" />" title="${item.title}" target="_blank"><@substring str=item.title endIndex=50 /></a></td>
								<td class="${tdClass}">
									<#if (item.catalogs?exists && item.catalogs?size > 0)>
										<#list item.catalogs as catalog>
											${catalog.name}&nbsp;
										</#list>
									</#if>
								</td>
								<td class="${tdClass}">${item.createTime}</td>
								<td class="${tdClass}"><#if item.isTop><font color="red">是</font><#else>否</#if></td>
								<td class="${tdClass}">
									<a href="${base}/admin/post/edit?id=${item.id}">修改</a>
								</td>
							</tr>
						</#list>
					</#if>
				</table>
				<div style="float:left;">
					<input type="button" class="btn" value="恢复正常" id="btn_publish"/>
					<input type="button" class="btn btn-danger" value="删除" id="btn_delete"/>
				</div>
				<div style="float:right;">
					<@mspring.pagingnavigator page=postPage form_id="postForm" />
				</div>
	    	</form>
		</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#btn_delete").click(function(){
    			mlog.form.confirmSubmit('postForm', '${base}/user/post/delete_trash', '确认要删除选中的文章吗？');
    		});
    		
    		$('#btn_publish').click(function(){
    			mlog.form.confirmSubmit('postForm', '${base}/user/post/publish', '确认要恢复选中文章吗？');
    		});
    	});
    </script>
<#include "../footer.ftl" />