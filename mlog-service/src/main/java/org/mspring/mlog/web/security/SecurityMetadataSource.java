/**
 * 
 */
package org.mspring.mlog.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.entity.security.Resource;
import org.mspring.mlog.entity.security.TreeItem;
import org.mspring.mlog.service.security.ResourceService;
import org.mspring.mlog.service.security.TreeItemService;
import org.mspring.platform.utils.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

/**
 * @author Gao Youbo
 * @since 2013-1-11
 * @Description
 * @TODO
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    /**
     * 重置资源定义
     */
    public static void resetResourceDefine() {
        resourceMap = null;
    }

    /**
     * 加载所有资源与权限的关系
     */
    public static void loadResourceDefine() {
        if (resourceMap == null) {
            ResourceService resourceService = ServiceFactory.getResourceService();
            TreeItemService treeItemService = ServiceFactory.getTreeItemService();

            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Resource> resources = resourceService.findAllResources();
            for (Resource resource : resources) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig("resource_" + resource.getId());
                configAttributes.add(configAttribute);
                resourceMap.put(resource.getUrl(), configAttributes);
            }

            List<TreeItem> items = treeItemService.findAllTreeItems();
            Iterator<TreeItem> it = items.iterator();
            while (it.hasNext()) {
                TreeItem item = it.next();
                if (StringUtils.isNotBlank(item.getCall())) {
                    Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                    ConfigAttribute configAttribute = new SecurityConfig("treeitem_" + item.getId());
                    configAttributes.add(configAttribute);
                    String pattern = item.getCall() + "/**";
                    resourceMap.put(pattern, configAttributes);
                }
            }
        }
    }

    /**
     * 返回所请求资源所需要的权限
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        loadResourceDefine();

        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            RequestMatcher urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(request)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }
}