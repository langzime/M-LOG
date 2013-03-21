/**
 * 
 */
package org.mspring.mlog.web.module.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Catalog;
import org.mspring.mlog.entity.Post;
import org.mspring.mlog.service.CatalogService;
import org.mspring.mlog.service.PostService;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2013-3-21
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/user/post")
public class User_PostWidget extends AbstractUserWidget {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private PostService postService;

    @RequestMapping("/create")
    public String create(@ModelAttribute Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Catalog> catalogs = catalogService.findCatalogByUser(SecurityUtils.getCurrentUser().getId());
        model.addAttribute("catalogs", catalogs);
        return view("post/create");
    }

    @RequestMapping("/create/save")
    public String create_save(@ModelAttribute Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
        return view("");
    }
}