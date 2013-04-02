/**
 * 
 */
package org.mspring.mlog.service.impl;

import java.util.Date;

import org.mspring.mlog.entity.Page;
import org.mspring.mlog.service.PageService;
import org.mspring.platform.core.AbstractServiceSupport;
import org.mspring.platform.persistence.query.QueryCriterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gao Youbo
 * @since 2013-3-19
 * @description
 * @TODO
 */
@Service
@Transactional
public class PageServiceImpl extends AbstractServiceSupport implements PageService {

    @Override
    public Page getPageById(Long id) {
        // TODO Auto-generated method stub
        Object page = getById(Page.class, id);
        if (page != null) {
            return (Page) page;
        }
        return null;
    }

    @Override
    public void createPage(Page page) {
        // TODO Auto-generated method stub
        if (page.getCreateTime() == null) {
            page.setCreateTime(new Date());
        }
        create(page);
    }

    @Override
    public void updatePage(Page page) {
        // TODO Auto-generated method stub
        if (page.getModifyTime() == null) {
            page.setModifyTime(new Date());
        }
        update(page);
    }

    @Override
    public void deletePage(Long... id) {
        // TODO Auto-generated method stub
        remove(Page.class, id);
    }

    @Override
    public org.mspring.platform.persistence.support.Page<Page> findPages(QueryCriterion queryCriterion, org.mspring.platform.persistence.support.Page<Page> pagePage) {
        // TODO Auto-generated method stub
        return findPage(queryCriterion, pagePage);
    }

}
