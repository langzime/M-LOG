<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<#include "../inc/header.ftl" />
<form id="resourceForm" name="resourceForm" action="${base}/admin/resource/list" method="POST">
	<@spring.bind "resourcePage" />
	<!-- pagination parameter -->
	<@spring.formHiddenInput path="resourcePage.pageNo" />
	<@spring.formHiddenInput path="resourcePage.totalPages" />
	<@spring.formHiddenInput path="resourcePage.totalCount" />
	<!-- sorter parameter -->
	<@spring.formHiddenInput path="resourcePage.sortEnable" />
	<@spring.formHiddenInput path="resourcePage.sort.field" />
	<@spring.formHiddenInput path="resourcePage.sort.order" />
	<table class="dtable" cellspacing="0" cellpadding="0">
		<tr>
			<th>编号</th>
			<th>名称</th>
			<th>URL</th>
			<th>操作</th>
		</tr>
		<#if resourcePage??>
			<#list resourcePage.result as item>
				<#assign tdClass = "odd">
				<#if item_index%2 == 0>
					<#assign tdClass = "even">
				</#if>
				<tr>
					<td class="${tdClass}">${item.id} </td>
					<td class="${tdClass}">${item.name} </td>
					<td class="${tdClass}">${item.url} </td>
					<td class="${tdClass}">
						<a href="${base}/admin/resource/edit?id=${item.id}">修改</a> | <a href="${base}/admin/resource/delete?id=${item.id}">删除</a>
					</td>
				</tr>
			</#list>
		</#if>
	</table>
	<table style="width:100%;">
		<tr>
			<td>
				<@mspring.pagingnavigator page=resourcePage form_id="resourceForm" />
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	turnHighLight(315005);
</script>
<#include "../inc/footer.ftl" />