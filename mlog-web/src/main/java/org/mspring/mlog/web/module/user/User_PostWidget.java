/**
 * 
 */
package org.mspring.mlog.web.module.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Catalog;
import org.mspring.mlog.entity.Post;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.CatalogService;
import org.mspring.mlog.service.PostService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.PostQueryCriterion;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.mspring.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        // 是否开启评论
        Map<String, String> commentStatus = new HashMap<String, String>();
        commentStatus.put(Post.CommentStatus.OPEN, "开启");
        commentStatus.put(Post.CommentStatus.CLOSE, "关闭");
        model.addAttribute("commentStatus", commentStatus);

        Map<String, String> isTop = new HashMap<String, String>();
        isTop.put("true", "是");
        isTop.put("false", "否");
        model.addAttribute("isTop", isTop);
        return view("post/create");
    }

    @RequestMapping("/create/save")
    public String create_save(@ModelAttribute Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = SecurityUtils.getCurrentUser(request);
        if (post.getAuthor() == null) {
            post.setAuthor(user);
        }
        post.setPostIp(request.getRemoteAddr());
        postService.createPost(post);
        return "redirect:/user/post/create";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/list")
    public String list(@ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (postPage == null) {
            postPage = new Page<Post>();
        }
        postPage.setSort(new Sort("isTop desc, id desc", ""));

        // 默认查看已发布的文章
        if (post == null) {
            post = new Post();
        }
        if (queryParams.get("status") == null || StringUtils.isBlank(queryParams.get("status").toString())) {
            post.setStatus(Post.Status.PUBLISH);
            queryParams.put("status", Post.Status.PUBLISH);
        }
        queryParams.put("author.id", SecurityUtils.getCurrentUser(request).getId());

        postPage = postService.findPost(postPage, new PostQueryCriterion(queryParams));
        model.addAttribute("postPage", postPage);
        model.addAttribute("status", Post.Status.getStatusMap());
        return "/user/post/list";
    }
    
    @RequestMapping("/delete")
    public String deletePost(@RequestParam(required = false) Long[] id,
            @ModelAttribute Page<Post> postPage, @ModelAttribute Post post,
            @QueryParam Map queryParams, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        if (id != null && id.length > 0) {
            postService.deletePost(id);
        }
        return list(postPage, post, queryParams, request, response, model);
    }
}