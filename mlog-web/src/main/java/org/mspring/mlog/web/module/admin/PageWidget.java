/**
 * 
 */
package org.mspring.mlog.web.module.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Page;
import org.mspring.mlog.service.PageService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.PageQueryCriterion;
import org.mspring.platform.persistence.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gao Youbo
 * @since 2013-3-19
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/admin/page")
public class PageWidget extends AdminWidget {
    @Autowired
    private PageService pageService;

    @RequestMapping("/list")
    @Log
    public String list(@ModelAttribute org.mspring.platform.persistence.support.Page<Page> pagePage, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (pagePage == null) {
            pagePage = new org.mspring.platform.persistence.support.Page<Page>();
        }
        pagePage.setSort(new Sort("id", Sort.DESC));
        pagePage = pageService.findPages(new PageQueryCriterion(queryParams), pagePage);
        model.addAttribute("pagePage", pagePage);
        return "/admin/page/listPage";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Page page, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/admin/page/createPage";
    }

    @RequestMapping("/create/save")
    @Log
    public String create_save(@ModelAttribute Page page, HttpServletRequest request, HttpServletResponse response, Model model) {
        pageService.createPage(page);
        return "redirect:/admin/page/list";
    }

    @RequestMapping("/edit")
    public String edit(@RequestParam(required = false) Long id, @ModelAttribute Page page, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id == null) {
            Object obj = getSessionAttribute(request, "PageWidget_edit_id");
            if (obj != null) {
                id = (Long) obj;
            }
        }
        if (id == null) {
            return prompt(model, "请先选择要修改的页面");
        }

        page = pageService.getPageById(id);
        if (page == null) {
            return prompt(model, "请先选择要修改的页面");
        }
        model.addAttribute("page", page);
        setSessionAttribute(request, "PageWidget_edit_id", id);
        return "/admin/page/editPage";
    }

    @RequestMapping("/edit/save")
    @Log
    public String edit_save(@ModelAttribute Page page, HttpServletRequest request, HttpServletResponse response, Model model) {
        pageService.updatePage(page);
        return "redirect:/admin/page/list";
    }

    @RequestMapping("/delete")
    @Log
    public String delete(@RequestParam(required = false) Long[] id, @ModelAttribute org.mspring.platform.persistence.support.Page<Page> pagePage, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        pageService.deletePage(id);
        return list(pagePage, queryParams, request, response, model);
    }
}