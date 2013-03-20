/**
 * 
 */
package org.mspring.mlog.web.module.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.security.Resource;
import org.mspring.mlog.service.security.ResourceService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.module.admin.query.ResourceQueryCriterion;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gao Youbo
 * @since 2013-3-20
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/admin/resource")
public class ResourceWidget extends AbstractAdminWidget {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/list")
    @Log
    public String list(@ModelAttribute Page<Resource> resourcePage, @ModelAttribute Resource resource, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (resourcePage == null) {
            resourcePage = new Page<Resource>();
        }
        resourcePage.setSort(new Sort("id", Sort.DESC));
        resourcePage = resourceService.findResourcePage(new ResourceQueryCriterion(queryParams), resourcePage);
        model.addAttribute("resourcePage", resourcePage);
        return "/admin/resource/listResource";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/admin/resource/createResource";
    }

    @RequestMapping("/create/save")
    @Log
    public String create_save(@ModelAttribute Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
        resource = resourceService.createResource(resource);
        return "redirect:/admin/resource/list";
    }

    @RequestMapping("/edit")
    public String edit(@RequestParam(required = false) Long id, @ModelAttribute Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id == null) {
            Object obj = getSessionAttribute(request, "ResourceWidget_edit_id");
            if (obj != null) {
                id = (Long) obj;
            }
        }
        if (id == null) {
            return prompt(model, "请先选择要修改的资源");
        }

        resource = resourceService.getResourceById(id);
        if (resource == null) {
            return prompt(model, "请先选择要修改的资源");
        }
        model.addAttribute("resource", resource);
        setSessionAttribute(request, "ResourceWidget_edit_id", id);
        return "/admin/resource/editResource";
    }

    @RequestMapping("/edit/save")
    @Log
    public String edit_save(@ModelAttribute Resource resource, HttpServletRequest request, HttpServletResponse response, Model model) {
        resourceService.updateResource(resource);
        return "redirect:/admin/resource/list";
    }

    @RequestMapping("/delete")
    @Log
    public String delete(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Resource> resourcePage, @ModelAttribute Resource resource, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        resourceService.deleteResource(id);
        return list(resourcePage, resource, queryParams, request, response, model);
    }
}
