package org.mspring.mlog.web.module.user;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Catalog;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.CatalogService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.utils.CatalogUtils;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.CatalogQueryCriterion;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SNS类别管理
 * @author huhongyu
 *
 */
@Widget
@RequestMapping("user/catalog")
public class User_CatalogWidget extends AbstractUserWidget {

    private CatalogService catalogService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * 展现分类列表
     * @param catalogPage
     * @param catalog
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/list")
    @Log
    public String listCatalog(@ModelAttribute Page<Catalog> catalogPage, @ModelAttribute Catalog catalog, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
    	
    	User loginUser = SecurityUtils.getCurrentUser();
    	if (catalogPage == null) {
            catalogPage = new Page<Catalog>();
            catalogPage.setPageSize(12);
        }
    	
    	 Sort sort = new Sort();
         sort.setField("order");
         sort.setOrder("desc");
         catalogPage.setSort(sort);
         
    	if(loginUser!=null){
    		queryParams.put("user.id", loginUser.getId());
    	}
    	
        catalogPage = catalogService.findCatalog(catalogPage, new CatalogQueryCriterion(queryParams));

        model.addAttribute("catalogs", CatalogUtils.getTreeList(catalogService.findAllCatalog()));
        model.addAttribute("catalogPage", catalogPage);
        return view("catalog/list");
    }
    
    /**
     * 删除分类-包括批量删除
     * @param catalogPage
     * @param catalog
     * @param queryParams
     * @param ids
     * @param deleteIds
     * @param orders
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("ctrl")
    @Log
    public String ctrl(@ModelAttribute Page<Catalog> catalogPage, @ModelAttribute Catalog catalog, @QueryParam Map queryParams, @RequestParam(required = false) Long[] ids, @RequestParam(required = false) Long[] deleteIds, @RequestParam(required = false) Long[] orders, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (deleteIds != null && deleteIds.length > 0) {
            catalogService.deleteCatalog(deleteIds);
        }
        if (orders != null && orders.length > 0) {
            catalogService.setCatalogOrders(ids, orders);
        }
        return listCatalog(catalogPage, catalog, queryParams, request, response, model);
    }
    
    /**
     * 创建新的分类
     * @param catalog
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/create")
    @Log
    public String create(@ModelAttribute Catalog catalog , HttpServletRequest request,HttpServletResponse response ,Model model){
    	if(catalog.getCreateTime()==null){
    		catalog.setCreateTime(new Date());
    	}
    	catalogService.createCatalog(catalog);
    	return "redirect:"+view("catalog/list");
    }
    
    /**
     * 修改分类
     * @param catalog
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("modify")
    @Log
    public String modify(@ModelAttribute Catalog catalog , HttpServletRequest request,HttpServletResponse response ,Model model){
    	if(catalog.getId()!=null&&catalog.getId()!=0){
    		catalog.setCreateTime(catalogService.getCatalogById(catalog.getId()).getCreateTime());
    		catalog.setUser(SecurityUtils.getCurrentUser());
    		catalog.setModifyTime(new Date());
    		catalogService.updateCatalog(catalog);
    	}
    	return "redirect:"+view("catalog/list");
    }
    
}
