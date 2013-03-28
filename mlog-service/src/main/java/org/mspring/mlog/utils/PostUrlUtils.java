/**
 * 
 */
package org.mspring.mlog.utils;

import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.entity.Post;
import org.mspring.mlog.entity.security.User;
import org.mspring.platform.utils.StringUtils;

/**
 * @author Gao Youbo
 * @since 2013-2-28
 * @description
 * @TODO
 */
public class PostUrlUtils {

    /**
     * 获取文章的链接
     * 
     * @param post
     * @return
     */
    public static String getPostUrl(String user, Long post) {
        return "/" + user + "/post/" + post;
    }

    /**
     * 获取文章链接
     * 
     * @param post
     * @return
     */
    public static String getPostUrl(Post post) {
        if (post == null || post.getId() == null) {
            return "";
        }
        try {
            if (StringUtils.isBlank(post.getAuthor().getName())) {
                User user = ServiceFactory.getUserService().getUserById(post.getAuthor().getId());
                post.setAuthor(user);
            }
            return getPostUrl(post.getAuthor().getName(), post.getId());
        } catch (Exception e) {
            // TODO: handle exception
            post = ServiceFactory.getPostService().getPostById(post.getId());
            return getPostUrl(post);
        }
    }
}
