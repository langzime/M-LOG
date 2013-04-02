/**
 * 
 */
package org.mspring.mlog.service.security;

import java.util.List;

import org.mspring.mlog.entity.security.Resource;
import org.mspring.platform.persistence.query.QueryCriterion;
import org.mspring.platform.persistence.support.Page;

/**
 * @author Gao Youbo
 * @since 2013-1-11
 * @Description
 * @TODO
 */
public interface ResourceService {
    /**
     * 查找所有resource
     * 
     * @return
     */
    List<Resource> findAllResources();

    /**
     * 创建Resource
     * 
     * @param resource
     * @return
     */
    Resource createResource(Resource resource);

    /**
     * 根据ResourceID获取resource
     * 
     * @param id
     * @return
     */
    Resource getResourceById(Long id);

    /**
     * 更新资源
     * 
     * @param resource
     */
    void updateResource(Resource resource);

    /**
     * 删除资源
     * 
     * @param id
     */
    void deleteResource(Long... id);

    /**
     * 分页查询
     * 
     * @param queryCriterion
     * @param resourcePage
     * @return
     */
    Page<Resource> findResourcePage(QueryCriterion queryCriterion, Page<Resource> resourcePage);

    /**
     * 给角色授权资源
     * 
     * @param roleId
     * @param resources
     */
    void setRoleResource(Long roleId, Long... resources);

    /**
     * 根据角色获取该角色拥有的资源
     * 
     * @param roleId
     */
    List<Resource> getResourcesByRole(Long roleId);
}
