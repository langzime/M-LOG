/**
 * 
 */
package org.mspring.mlog.web.module.blog;

import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.web.freemarker.FreemarkerVariableNames;
import org.mspring.mlog.web.module.AbstractWidget;
import org.springframework.ui.Model;

/**
 * @author Gao Youbo
 * @since 2013-1-10
 * @Description
 * @TODO
 */
public class AbstractBlogWidget extends AbstractWidget {
    /**
     * 设置当前页
     * 
     * @param model
     * @param currentPage
     */
    protected void setCurrnetPage(Model model, String currentPage) {
        model.addAttribute(FreemarkerVariableNames.CURRENT_PAGE, currentPage);
    }

    /**
     * 设置博客拥有者
     * 
     * @param model
     * @param user
     */
    protected void setBlogOwner(Model model, User user) {
        model.addAttribute(FreemarkerVariableNames.BLOG_OWNER, user);
    }
}
