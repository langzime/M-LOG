/**
 * 
 */
package org.mspring.mlog.web.module.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2013-4-1
 * @description
 * @TODO
 */
@Widget
public class Web_LoginWidget {
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/web/login";
    }
}
