<#macro dropmenu>
<ul class="nav pull-right">
	<#if currentUser?exists>
		<li class="dropdown">
			<a id="my_toolbar" class="dropdown-toggle" data-toggle="dropdown">${currentUser.alias} <b class="caret"></b></a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="my_toolbar">
				<li role="presentation"><a role="menuitem" tabindex="-1" href="${base}/${currentUser.name}">我的主页</a></li>
				<li role="presentation"><a role="menuitem" tabindex="-1" href="${base}/user/profile">个人中心</a></li>
				<li role="presentation"><a role="menuitem" tabindex="-1" href="#">消息</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="#">退出</a></li>
            </ul>
		</li>
	<#else>
		<li><a href="${base}/login">登录</a></li>
	</#if>
</ul>
</#macro>