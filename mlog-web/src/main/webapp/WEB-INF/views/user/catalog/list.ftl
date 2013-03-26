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
	    	<div class=" table-hover" style="width:65%;float:left">
		 	 <div class="widget-box" >
						<div class="widget-title">分类列表</div>
						<div class="widget-content" >
						<@spring.bind "catalogPage" />
				<!-- pagination parameter -->
				<@spring.formHiddenInput path="catalogPage.pageNo" />
				<@spring.formHiddenInput path="catalogPage.totalPages" />
				<@spring.formHiddenInput path="catalogPage.totalCount" />
				<!-- sorter parameter -->
				<@spring.formHiddenInput path="catalogPage.sortEnable" />
				<@spring.formHiddenInput path="catalogPage.sort.field" />
				<@spring.formHiddenInput path="catalogPage.sort.order" />
				<table class="table  table-hover"  cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<input type="checkbox" onclick="mlog.form.checkAll(this, 'deleteIds');" />
						</th>
						<th>名称</th>
						<th>排序</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					<#if catalogPage??>
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
								<td class="${tdClass}">${item.order!""}
								</td>
								<td class="${tdClass}">${item.createTime}</td>
								<td class="${tdClass}">
									<input type="hidden" name="ids" value="${item.id}" />
									<a href="${base}/admin/catalog/edit?id=${item.id}">修改</a>
								</td>
							</tr>
						</#list>
					</#if>
					<tr>
						<td class="${tdClass}">
						<input  type="button" class="btn btn-small btn-primary" value="删  除" data-loading-text="正在提交..." onclick="ctrl();" />
						</td>
						
						<td class="${tdClass}" colspan="5">
							<@mspring.pagingnavigator page=catalogPage form_id="catalogForm" />
						</td>
					</tr>
				</table>
			</div>
			</div>
		    </div>
		    <div class="span4 row-fluid">	
		    <div class="widget-box">
						<div class="widget-title">添加分类</div>
						<div class="widget-content" >
							<form class="form-horizontal">
								<div class="control-group">
								  <label class="control-label" style="width:60px">编号:</label>
								  <div class="controls" style="margin-left:90px">
									<@spring.formInput path="catalog.id" attributes='class="textinput" style="width:150px;" disabled="disabled"' defaultValue="自动生成"  />
								  </div>
								</div>
								<div class="control-group">
								  <label class="control-label" style="width:60px">名称:</label>
								  <div class="controls" style="margin-left:90px">
								   <@spring.formInput path="catalog.name" attributes='class="textinput" style="width:150px;" validate=\'{required: true, catalogNameExists:true, messages:{required:"请输入分类名称", catalogNameExists:"分类名字已经存在"}}\'' />
								  </div>
								</div>
								<div class="control-group">
								  <label class="control-label" style="width:60px">创建时间:</label>
								  <div class="controls" style="margin-left:90px">
								    <@spring.formInput path="catalog.createTime" attributes='class="textinput" style="width:150px;" disabled="disabled"' defaultValue="当前时间" />
								  </div>
								</div>
								
								<div class="control-group">
								  <label class="control-label" style="width:60px">排序:</label>
								 <div class="controls" style="margin-left:90px">
								    <@spring.formInput path="catalog.order" attributes='class="textinput" style="width:150px;" validate=\'{required: false, digits: true, messages:{digits:"排序号必须为正整数"}}\'' />
								  </div>
								</div>
								
								<div class="control-group">
								  <label class="control-label" style="width:60px">描述:</label>
								  <div class="controls" style="margin-left:90px">                    
								    <@spring.formTextarea path="catalog.description" attributes='class="textinput" style="width:150px;"' />
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
			
			<div class="span4 row-fluid">	
		    <div class="widget-box">
						<div class="widget-title">修改分类</div>
						<div class="widget-content" >
							<form class="form-horizontal">
								<div class="control-group">
								  <label class="control-label" style="width:60px">编号:</label>
								  <div class="controls" style="margin-left:90px">
									<@spring.formInput path="catalog.id" attributes='class="textinput" style="width:150px;" disabled="disabled"' defaultValue="自动生成"  />
								  </div>
								</div>
								<div class="control-group">
								  <label class="control-label" style="width:60px">名称:</label>
								  <div class="controls" style="margin-left:90px">
								   <@spring.formInput path="catalog.name" attributes='class="textinput" style="width:150px;" validate=\'{required: true, catalogNameExists:true, messages:{required:"请输入分类名称", catalogNameExists:"分类名字已经存在"}}\'' />
								  </div>
								</div>
								<div class="control-group">
								  <label class="control-label" style="width:60px">创建时间:</label>
								  <div class="controls" style="margin-left:90px">
								    <@spring.formInput path="catalog.createTime" attributes='class="textinput" style="width:150px;" disabled="disabled"' defaultValue="当前时间" />
								  </div>
								</div>
								
								<div class="control-group">
								  <label class="control-label" style="width:60px">排序:</label>
								 <div class="controls" style="margin-left:90px">
								    <@spring.formInput path="catalog.order" attributes='class="textinput" style="width:150px;" validate=\'{required: false, digits: true, messages:{digits:"排序号必须为正整数"}}\'' />
								  </div>
								</div>
								
								<div class="control-group">
								  <label class="control-label" style="width:60px">描述:</label>
								  <div class="controls" style="margin-left:90px">                    
								    <@spring.formTextarea path="catalog.description" attributes='class="textinput" style="width:150px;"' />
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