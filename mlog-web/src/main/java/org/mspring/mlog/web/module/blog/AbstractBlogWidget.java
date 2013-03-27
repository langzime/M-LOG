/**
 * 
 */
package org.mspring.mlog.web.module.blog;

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
    protected void setCurrnetPage(Model model, String currentPage) {
        model.addAttribute(FreemarkerVariableNames.CURRENT_PAGE, currentPage);
    }
}
