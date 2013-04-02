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
			<li><a href="${base}/user/comment/list">评论管理</a> <span class="divider">/</span></li>
			<li class="active">评论审核</li>
	    </ul>
	     <div class="widget-box">
			<div class="widget-title">评论审核</div>
				<div class="widget-content" >
	    		<form id="auditForm" name="auditForm" action="${base}/user/comment/audit" method="POST">
	    		<@spring.bind "comment"/>
		    		<div class="control-group" style="width:500px;float:left">
						<label class="control-label">评论作者</label>
						<input id="textinput" name="textinput" type="text" placeholder="placeholder" class="input-xlarge">
		    		</div>
		    		<div class="control-group" style="width:500px;float:right" >
						<label class="control-label">评论&nbsp;IP</label>
						<input id="textinput" name="textinput" type="text" placeholder="placeholder" class="input-xlarge">
		    		</div>
	    		</form>
	    		</div>
	    	</div>
	    </div>
	 </div>
<script type="text/javascript">
</script>
<#include "../footer.ftl" />
