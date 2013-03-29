/**
 * 
 */
package org.mspring.mlog.web.module.blog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.common.PageNames;
import org.mspring.mlog.entity.Post;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.CatalogService;
import org.mspring.mlog.service.PostService;
import org.mspring.mlog.service.security.UserService;
import org.mspring.mlog.utils.CatalogUtils;
import org.mspring.mlog.web.freemarker.FreemarkerVariableNames;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.PostQueryCriterion;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2012-7-23
 * @Description 首页
 * @TODO
 */
@Widget
@RequestMapping({ "/", "" })
public class Blog_IndexWidget extends AbstractBlogWidget {
    @Autowired
    private PostService postService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserService userService;

    /**
     * 文章首页
     * 
     * @param postPage
     * @param request
     * @param response
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping("/{user}")
    public String index(@PathVariable String user, @ModelAttribute Page<Post> postPage, HttpServletRequest request, HttpServletResponse response, Model model) {
        User blogOwner = userService.getUserByName(user);
        if (blogOwner == null) {
            return "redirect:/errors/404";
        }
        if (postPage == null) {
            postPage = new Page<Post>();
        }
        if (postPage.getSort() == null) {
            postPage.setSort(new Sort("isTop desc, id desc", ""));
        }
        // postService.findPost(postPage,
        // "select post from Post post where post.status = ? order by post.isTop desc, post.id desc",
        // new Object[] { Post.Status.PUBLISH });
        Map queryParams = new HashMap();
        queryParams.put("status", Post.Status.PUBLISH);
        queryParams.put("author.id", blogOwner.getId());
        postPage = postService.findPost(postPage, new PostQueryCriterion(queryParams));

        model.addAttribute(FreemarkerVariableNames.POST_PAGE, postPage);
        model.addAttribute("navs", CatalogUtils.getTreeList(catalogService.findAllCatalog()));
        setCurrnetPage(model, PageNames.INDEX);
        setBlogOwner(model, blogOwner);
        return "skin:/index";
    }
}
