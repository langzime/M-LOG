/**
 * 
 */
package org.mspring.mlog.web.module.user;

import org.mspring.mlog.web.module.AbstractWidget;

/**
 * @author Gao Youbo
 * @since 2013-1-10
 * @Description
 * @TODO
 */
public class AbstractUserWidget extends AbstractWidget {
    protected static final String USER_VIEW_PREFIX = "/user/";

    protected String view(String view) {
        return USER_VIEW_PREFIX + view;
    }
}
