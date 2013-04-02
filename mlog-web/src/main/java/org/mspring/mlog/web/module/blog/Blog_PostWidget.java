/**
 * 
 */
package org.mspring.mlog.web.module.blog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mspring.mlog.common.PageNames;
import org.mspring.mlog.entity.Post;
import org.mspring.mlog.service.PostService;
import org.mspring.mlog.service.cache.CacheService;
import org.mspring.mlog.utils.PermissionUtils;
import org.mspring.mlog.utils.PostUrlUtils;
import org.mspring.mlog.web.freemarker.FreemarkerVariableNames;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.platform.utils.StringUtils;
import org.mspring.platform.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gao Youbo
 * @since 2013-3-1
 * @description 文章页
 * @TODO
 */
@Widget
@RequestMapping({ "/", "" })
public class Blog_PostWidget extends AbstractBlogWidget {
    @Autowired
    private PostService postService;
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/{user}/post/{id}")
    public String post(@PathVariable String user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        Post post = null;
        Object obj = cacheService.getCacheValue(CacheService.CacheName.POST_PAGE_CACHE_NAME, id);
        if (obj != null && obj instanceof Post) {
            post = (Post) obj;
        }
        if (post == null && StringUtils.isNotBlank(id) && ValidatorUtils.isNumber(id)) {
            post = postService.getPostById(new Long(id));
            cacheService.updateCacheValue(CacheService.CacheName.POST_PAGE_CACHE_NAME, id, post);
        }

        if (post != null && !PermissionUtils.hasPostPermission(post, session)) {
            model.addAttribute("postId", post.getId());
            model.addAttribute("postUrl", PostUrlUtils.getPostUrl(post));
            return "skin:/post-token";
        }
        model.addAttribute(FreemarkerVariableNames.POST, post);
        setCurrnetPage(model, PageNames.SINGLE);

        // seo 信息
        String keyword = post.getTitle();
        String description = "";
        if (StringUtils.isNotBlank(post.getSummary())) {
            description = StringUtils.contentTransform(post.getSummary(), true, false, null, null);
        } else {
            description = StringUtils.contentTransform(post.getContent(), true, true, 0, 200);
        }
        // 去掉空格和制表符
        description = description.replaceAll("\n|\t", "");

        model.addAttribute("keyword", keyword);
        model.addAttribute("description", description);
        return "skin:/post";
    }

    @RequestMapping("/{user}/post/token")
    public String token(@PathVariable String user, @RequestParam(required = true) Long postId, @RequestParam(required = true) String postUrl, @RequestParam(required = false) String password, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        boolean has_permission = PermissionUtils.hasPostPermission(postId, password);
        if (!has_permission) {
            model.addAttribute("postId", postId);
            model.addAttribute("postUrl", postUrl);
            model.addAttribute("not_token", false);
            return "skin:/post-token";
        }
        PermissionUtils.setPostPermission(postId, password, request);
        return "redirect:" + postUrl;
    }
}
