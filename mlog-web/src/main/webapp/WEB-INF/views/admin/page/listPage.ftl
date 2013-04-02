<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<#include "../inc/header.ftl" />
<form id="pageForm" name="pageForm" action="${base}/admin/page/list" method="POST">
	<@spring.bind "pagePage" />
	<!-- pagination parameter -->
	<@spring.formHiddenInput path="pagePage.pageNo" />
	<@spring.formHiddenInput path="pagePage.totalPages" />
	<@spring.formHiddenInput path="pagePage.totalCount" />
	<!-- sorter parameter -->
	<@spring.formHiddenInput path="pagePage.sortEnable" />
	<@spring.formHiddenInput path="pagePage.sort.field" />
	<@spring.formHiddenInput path="pagePage.sort.order" />
	<table class="dtable" cellspacing="0" cellpadding="0">
		<tr>
			<th><input type="checkbox" onclick="mlog.form.checkAll(this, 'id');" /></th>
			<th>编号</th>
			<th>名称</th>
			<th>创建时间</th>
			<th>修改时间</th>
			<th>操作</th>
		</tr>
		<#if pagePage??>
			<#list pagePage.result as item>
				<#assign tdClass = "odd">
				<#if item_index%2 == 0>
					<#assign tdClass = "even">
				</#if>
				<tr>
					<td class="${tdClass}"><input type="checkbox" name="id" value="${item.id}" /></td>
					<td class="${tdClass}">${item.id} </td>
					<td class="${tdClass}">${item.name} </td>
					<td class="${tdClass}">${item.createTime} </td>
					<td class="${tdClass}">${item.modifyTime!""} </td>
					<td class="${tdClass}">
						<a href="${base}/admin/page/edit?id=${item.id}">修改</a>
					</td>
				</tr>
			</#list>
		</#if>
	</table>
	<table style="width:100%;">
		<tr>
			<td>
				<input type="button" class="btn" value=" 删除 " onclick="mlog.form.confirmSubmit('pageForm', '${base}/admin/page/delete', '确认要将删除选中页面吗?');" />
			</td>
			<td>
				<@mspring.pagingnavigator page=pagePage form_id="pageForm" />
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	turnHighLight(130005);
</script>
<#include "../inc/footer.ftl" />