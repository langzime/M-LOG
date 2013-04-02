/**
 * 
 */
package org.mspring.mlog.service;

import java.util.List;

import org.mspring.mlog.entity.Catalog;
import org.mspring.platform.persistence.query.QueryCriterion;
import org.mspring.platform.persistence.support.Page;

/**
 * @author Gao Youbo
 * @since 2012-7-18
 * @Description
 * @TODO
 */
public interface CatalogService {
    /**
     * 创建分类
     * 
     * @param catalog
     * @return
     */
    public Catalog createCatalog(Catalog catalog);

    /**
     * 查找分类
     * 
     * @param idArray
     */
    public void deleteCatalog(Long... idArray);

    /**
     * 查找所有分类
     * 
     * @return
     */
    public List<Catalog> findAllCatalog();

    /**
     * 分页查找分类
     * 
     * @param page
     * @param criterion
     * @return
     */
    public Page<Catalog> findCatalog(Page<Catalog> page, QueryCriterion criterion);

    /**
     * 根据编号获取分类
     * 
     * @param catalogId
     * @return
     */
    public Catalog getCatalogById(Long catalogId);

    /**
     * 更新分类信息
     * 
     * @param catalog
     */
    public void updateCatalog(Catalog catalog);

    /**
     * 设置排序
     * 
     * @param ids
     * @param orders
     */
    public void setCatalogOrders(Long[] ids, Long[] orders);

    /**
     * 设置父分类
     * 
     * @param catalogId
     * @param parentId
     */
    public void setCatalogParent(Long catalogId, Long parentId);

    /**
     * 查找子分类
     * 
     * @param parent
     * @return
     */
    public List<Catalog> findChildCatalogs(Long parent);

    /**
     * 查找所有子分类
     * 
     * @param parent
     * @return
     */
    public List<Catalog> findAllChildCatalogs(Long parent);

    /**
     * 分类是否存在
     * 
     * @param name
     * @param id
     * @param userId
     * @return
     */
    public boolean catalogExists(String name, Long id, Long userId);

    /**
     * 根据用户查找该用户的Catalog
     * 
     * @param user
     * @return
     */
    public List<Catalog> findCatalogByUser(Long user);
}
