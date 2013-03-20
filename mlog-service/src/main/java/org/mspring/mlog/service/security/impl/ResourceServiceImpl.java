/**
 * 
 */
package org.mspring.mlog.service.security.impl;

import java.util.List;

import org.mspring.mlog.entity.security.Resource;
import org.mspring.mlog.entity.security.RoleResource;
import org.mspring.mlog.service.security.ResourceService;
import org.mspring.platform.core.AbstractServiceSupport;
import org.mspring.platform.persistence.query.QueryCriterion;
import org.mspring.platform.persistence.support.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gao Youbo
 * @since 2013-1-11
 * @Description
 * @TODO
 */
@Service
@Transactional
public class ResourceServiceImpl extends AbstractServiceSupport implements ResourceService {

    /*
     * (non-Javadoc)
     * 
     * @see org.mspring.mlog.service.security.ResourceService#findAllResources()
     */
    @Override
    public List<Resource> findAllResources() {
        // TODO Auto-generated method stub
        return findAll(Resource.class);
    }

    @Override
    public Resource createResource(Resource resource) {
        // TODO Auto-generated method stub
        Long id = (Long) create(resource);
        return getResourceById(id);
    }

    @Override
    public Resource getResourceById(Long id) {
        // TODO Auto-generated method stub
        return (Resource) getById(Resource.class, id);
    }

    @Override
    public void updateResource(Resource resource) {
        // TODO Auto-generated method stub
        update(resource);
    }

    @Override
    public void deleteResource(Long... id) {
        // TODO Auto-generated method stub
        remove(id);
    }

    @Override
    public Page<Resource> findResourcePage(QueryCriterion queryCriterion, Page<Resource> resourcePage) {
        // TODO Auto-generated method stub
        return findPage(queryCriterion, resourcePage);
    }

    @Override
    public void setRoleResource(Long roleId, Long... resources) {
        // TODO Auto-generated method stub
        if (roleId == null || resources == null) {
            return;
        }
        // 先该角色清空之前的权限
        executeUpdate("delete from RoleResource rr where rr.PK.role.id = ?", roleId);
        for (Long res : resources) {
            RoleResource rr = new RoleResource(roleId, res);
            create(rr);
        }
    }

    @Override
    public List<Resource> getResourcesByRole(Long roleId) {
        // TODO Auto-generated method stub
        return find("select rr.PK.resource from RoleResource rr where rr.PK.role.id = ?", roleId);
    }

}
