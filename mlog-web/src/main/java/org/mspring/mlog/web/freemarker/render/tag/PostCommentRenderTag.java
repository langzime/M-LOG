/**
 * 
 */
package org.mspring.mlog.web.freemarker.render.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.entity.Comment;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.web.security.SecurityUtils;

import freemarker.template.SimpleHash;

/**
 * @author Gao Youbo
 * @since 2013-3-1
 * @description
 * @TODO
 */
public class PostCommentRenderTag extends CacheRenderTag {

    /**
     * 
     */
    private static final long serialVersionUID = -8679684665697439912L;

    protected Long post;

    public void setPost(Long post) {
        this.post = post;
    }

    @Override
    protected String cachedProcess(SimpleHash model) {
        // TODO Auto-generated method stub
        if (post == null) {
            return "";
        }
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        User user = SecurityUtils.getCurrentUser(request);
        
        List<Comment> comments = ServiceFactory.getCommentService().findCommentsByPost(post);
        
        model.put("comments", comments);
        model.put("postId", post);
        model.put("currentUser", user);
        return getTemplateString(model);
    }

    @Override
    protected String getCacheKey(SimpleHash model) {
        // TODO Auto-generated method stub
        StringBuffer key = new StringBuffer();
        key.append(this.getClass().getName()).append(template + "_").append("post:" + post);
        return key.toString();
    }

}
