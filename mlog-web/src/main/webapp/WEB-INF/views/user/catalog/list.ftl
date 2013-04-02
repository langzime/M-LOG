<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<#import "../ui.ftl" as ui>
<@ui.header title="分类管理"/>
	<@ui.sidebar active="分类管理" />
	<div class="span10">
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="${siteurl}">首页</a> <span class="divider">/</span></li>
			<li><a href="${base}/user/profile">管理</a> <span class="divider">/</span></li>                  
			<li class="active">分类管理</li>
	    </ul>
	    <div class="row-fluid">
	    	<div class="span8">
	    		<form id="catalogForm" name="catalogForm" action="${base}/user/catalog/list" method="POST">
		    		<@spring.bind "catalogPage" />
					<!-- pagination parameter -->
					<@spring.formHiddenInput path="catalogPage.pageNo" />
					<@spring.formHiddenInput path="catalogPage.totalPages" />
					<@spring.formHiddenInput path="catalogPage.totalCount" />
					<!-- sorter parameter -->
					<@spring.formHiddenInput path="catalogPage.sortEnable" />
					<@spring.formHiddenInput path="catalogPage.sort.field" />
					<@spring.formHiddenInput path="catalogPage.sort.order" />
					<table class="table table-bordered table-hover"  cellspacing="0" cellpadding="0">
						<tr>
							<th style="width:5%">
								<input type="checkbox" onclick="mlog.form.checkAll(this, 'deleteIds');" />
							</th>
							<th style="width:15%">名称</th>
							<th style="width:47%">描述</th>
							<th style="width:22%">创建时间</th>
							<th style="width:10%">操作</th>
						</tr>
						<#if catalogPage?exists>
							<#list catalogPage.result as item>
								<#assign tdClass = "odd">
								<#if item_index%2 == 0>
									<#assign tdClass = "even">
								</#if>
								
								<#assign deep = "">
								<#list 1..item.deep as i>
								<#assign deep = deep + "―">
								</#list>
								<tr>
									<td class="${tdClass}"><input type="checkbox" name="deleteIds" value="${item.id}" /></td>
									<td class="${tdClass}">${item.name}</td>
									<td class="${tdClass}">${item.description!""}</td>
									<td class="${tdClass}">${item.createTime}</td>
									<td class="${tdClass}">
										<input type="hidden" class="ids" name="ids" value="${item.id}" />
										<input type="hidden" class="order" name="order" value="${item.order!""}" />
										<a href="#" onclick=edit(this); >修改</a>
									</td>
								</tr>
							</#list>
						</#if>
					</table>
					<div style="float:left;">
						<input  type="button" class="btn btn-danger" value="废 弃" data-loading-text="正在提交..." onclick="ctrl();" />
					</div>
					<div style="float:right;">
						<@mspring.pagingnavigator page=catalogPage form_id="catalogForm" />
					</div>
				</form>
	    	</div>
	    	<div class="span4 row-fluid">
	    		<div class="widget-box">
					<div class="widget-title">添加分类</div>
					<div class="widget-content" >
						<form class="form-horizontal" id="createForm" action="${base}/user/catalog/create" method="POST">
						<@spring.bind "catalog" />
							<div class="control-group" style="margin-top:10px">
								<label class="control-label text-info" style="width:60px">名称:</label>
								<div class="controls" style="margin-left:90px">
									<@spring.formInput path="catalog.name" attributes='class="textinput" validate=\'{required: true, catalogNameExists:true, messages:{required:"请输入分类名称", catalogNameExists:"分类名字已经存在"}}\'' />
								</div>
							</div>
							<div class="control-group">
							  	<label class="control-label text-info" style="width:60px">排序:</label>
							 	<div class="controls" style="margin-left:90px">
							    	<@spring.formInput path="catalog.order" attributes='class="textinput" validate=\'{required: false, digits: true, messages:{digits:"排序号必须为正整数"}}\'' />
							  	</div>
							</div>
							<div class="control-group">
							  	<label class="control-label text-info" style="width:60px">描述:</label>
							  	<div class="controls" style="margin-left:90px">                    
							    	<@spring.formTextarea path="catalog.description" attributes='class="textinput"' />
							  	</div>
							</div>
							<div class="control-group">
								<input  type="submit" class="btn btn-small btn-primary" value="提交"   />
								<input  type="reset" class="btn btn-small btn-primary" value="重置"   />
							</div>
						</form>
					</div>
				</div>
				
				<div class="widget-box" style="margin-top:20px">
					<div class="widget-title">修改分类</div>
					<div class="widget-content">
						<form class="form-horizontal" id="modifyForm" action="${base}/user/catalog/modify" method="POST">
							<div class="control-group" style="margin-top:10px">
							  	<label class="control-label text-info" style="width:60px">名称:</label>
							  	<div class="controls" style="margin-left:90px">
							   		<@spring.formHiddenInput path="catalog.id" />
							   		<@spring.formInput path="catalog.name" attributes='class="textinput"  validate=\'{required: true, catalogNameExists:true, messages:{required:"请输入分类名称", catalogNameExists:"分类名字已经存在"}}\'' />
							  	</div>
							</div>
							
							<div class="control-group">
							  	<label class="control-label text-info" style="width:60px">排序:</label>
						 		<div class="controls" style="margin-left:90px">
						    		<@spring.formInput path="catalog.order" attributes='class="textinput" validate=\'{required: false, digits: true, messages:{digits:"排序号必须为正整数"}}\'' />
						  		</div>
							</div>
							
							<div class="control-group">
								<label class="control-label text-info" style="width:60px">描述:</label>
							  	<div class="controls" style="margin-left:90px">
							  		<@spring.formTextarea path="catalog.description" attributes='class="textinput"' />
							  	</div>
							</div>
							<div class="control-group">
								<input  type="submit" class="btn btn-small btn-primary" value="提交"   />
								<input  type="reset" class="btn btn-small btn-primary" value="重置"   />
							</div>
						</form>
					</div>
				</div>
	    	</div>
	    </div>
	</div>
	
<script type="text/javascript">
	$(document).ready(function(){
			mlog.form.validate({
			selector : "#createForm",
			errorLabelContainer : "#createError",
			wrapper: 'span',
			onfocusout : false,
			onkeyup : false,
			onclick : false,
			success : function(){
				mlog.utils.scrollTop();
			}
		});
	});
	
	function edit(obj){
		var id = $($($(obj).parent().parent().children().get(4)).find('input.ids')).val();
		var name= $($(obj).parent().parent().children().get(1)).html();
		var description= $($(obj).parent().parent().children().get(2)).html();
		var order = $($($(obj).parent().parent().children().get(4)).find('input.order')).val();
		
		//
		$('#modifyForm').find("input[id='name']").focus();
		
		$('#modifyForm').find("input[id='name']").val(name);
		$('#modifyForm').find("textarea[id='description']").val(description);
		$('#modifyForm').find("input[id='order']").val(order);
		$('#modifyForm').find("input[id='id']").val(id);
		
	}

	function ctrl(){
		var flagOrders = validateOrders();
		if(!flagOrders){
			return;
		}
		mlog.form.submitForm('catalogForm', '${base}/user/catalog/ctrl');
	}
	
	function validateOrders(){
		var orders = document.getElementsByName('orders');
		for(var i = 0; i < orders.length; i++){
			var value = $(orders[i]).val();
			if($.trim(value).length > 0 && !(/^\d+$/.test(value))){
				mlog.dialog.tip({msg:'排序号必须为数字', type:'warn'});
				$(orders[i]).focus();
				return false;
			}
			if($.trim(value).length > 0){
				if(value < 1 || value > 1000){
					mlog.dialog.tip({msg:'排序号的范围必须在1-1000之间', type:'warn'});
					$(orders[i]).focus();
					return false;
				}
			}
		}
		return true;
	}
</script>
<#include "../footer.ftl" />
