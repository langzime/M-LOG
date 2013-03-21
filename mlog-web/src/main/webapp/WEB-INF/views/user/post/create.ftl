<#import "/META-INF/spring.ftl" as spring />
<#import "../ui.ftl" as ui>
<@ui.header title="发表文章" />
	<@ui.sidebar active="发表文章" />
	<div class="span10">
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="${siteurl}">首页</a> <span class="divider">/</span></li>
			<li><a href="${base}/user/profile">管理</a> <span class="divider">/</span></li>                  
			<li class="active">发表文章</li>
	    </ul>
	    <div class="back-right-inner">
	        <form action="/admin/post/new-do" method="post" id="entry_form">
	        	<@spring.bind "post" />
	        	<p class="text-info">标题(必填)</p>
	        	<@spring.formInput path="post.title" attributes='maxlength="80" placeholder="此处输入文章标题"'/>
        		
        		<div>
        			<p class="text-info">内容(必填)
        				<span style="float:right;display: inline-block;">
        					<input id="entry_add" type="button" class="btn btn-small btn-primary" value="发   表">
        					<input id="save_as_draft" type="button" class="btn btn-small btn-primary" value="存为草稿">
    					</span>
					</p>
				</div>
				<@spring.formTextarea path="post.content" attributes='style="width: 100%; height: 400px; display: none;"' />
            </form>
	    </div>
    </div>
    <script type="text/javascript">
		mlog.editor.init({
			type : "kindeditor",
			model : "all",
			id : "content"
		});
    </script>
<#include "../footer.ftl" />