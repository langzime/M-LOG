/**
 * 
 */
package org.mspring.mlog.service;

import org.mspring.mlog.entity.Page;
import org.mspring.platform.persistence.query.QueryCriterion;

/**
 * @author Gao Youbo
 * @since 2013-3-18
 * @description
 * @TODO
 */
public interface PageService {
    Page getPageById(Long id);

    void createPage(Page page);

    void updatePage(Page page);

    void deletePage(Long... id);

    org.mspring.platform.persistence.support.Page<Page> findPages(QueryCriterion queryCriterion, org.mspring.platform.persistence.support.Page<Page> pagePage);
}
