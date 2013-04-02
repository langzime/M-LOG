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
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.PostQueryCriterion;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gao Youbo
 * @since 2013-3-21
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/user/post")
@SuppressWarnings({ "unchecked", "rawtypes" })
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

    @RequestMapping(value = "/create/save", method = RequestMethod.POST)
    public String create_save(@ModelAttribute Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = SecurityUtils.getCurrentUser(request);
        if (post.getAuthor() == null) {
            post.setAuthor(user);
        }
        post.setPostIp(request.getRemoteAddr());
        postService.createPost(post);
        return "redirect:/user/post/create";
    }

    @RequestMapping("/list")
    public String list(@ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        postPage = getPage(postPage, post, queryParams, Post.Status.PUBLISH);

        model.addAttribute("postPage", postPage);
        model.addAttribute("status", Post.Status.getStatusMap());
        return "/user/post/list";
    }

    /**
     * 废弃文章
     * 
     * @param id
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/trash", method = RequestMethod.POST)
    public String trash(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id != null && id.length > 0) {
            postService.trash(id);
        }
        return list(postPage, post, queryParams, request, response, model);
    }

    /**
     * 草稿箱
     * 
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/drafts")
    public String drafts(@ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        postPage = getPage(postPage, post, queryParams, Post.Status.DRAFT);

        model.addAttribute("postPage", postPage);
        model.addAttribute("status", Post.Status.getStatusMap());
        return "/user/post/drafts";
    }

    /**
     * 删除草稿
     * 
     * @param id
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete_drafts", method = RequestMethod.POST)
    public String delete_drafts(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id != null && id.length > 0) {
            postService.deletePost(id);
        }
        return drafts(postPage, post, queryParams, request, response, model);
    }

    /**
     * 回收站
     * 
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/trash")
    public String trash(@ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {

        postPage = getPage(postPage, post, queryParams, Post.Status.TRASH);

        model.addAttribute("postPage", postPage);
        model.addAttribute("status", Post.Status.getStatusMap());
        return "/user/post/trash";
    }

    /**
     * 删除回收站文章
     * 
     * @param id
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete_trash", method = RequestMethod.POST)
    public String delete_trash(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id != null && id.length > 0) {
            postService.deletePost(id);
        }
        return trash(postPage, post, queryParams, request, response, model);
    }

    /**
     * 回收站文章恢复正常
     * 
     * @param id
     * @param postPage
     * @param post
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String publish(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Post> postPage, @ModelAttribute Post post, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id != null && id.length > 0) {
            postService.trash2Publish(id);
        }
        return trash(postPage, post, queryParams, request, response, model);
    }

    /**
     * 获取Page
     * 
     * @param postPage
     * @param post
     * @param queryParams
     * @param status
     * @return
     */
    private Page<Post> getPage(Page<Post> postPage, Post post, Map queryParams, String status) {
        if (postPage == null) {
            postPage = new Page<Post>();
        }
        postPage.setSort(new Sort("isTop desc, id desc", ""));

        if (post == null) {
            post = new Post();
        }

        post.setStatus(status);
        queryParams.put("status", status);

        queryParams.put("author.id", SecurityUtils.getCurrentUser().getId());
        return postService.findPost(postPage, new PostQueryCriterion(queryParams));
    }

}