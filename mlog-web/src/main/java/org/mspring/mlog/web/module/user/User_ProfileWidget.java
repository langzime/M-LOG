/**
 * 
 */
package org.mspring.mlog.web.module.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2013-3-20
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/user")
public class User_ProfileWidget extends AbstractUserWidget {
    @RequestMapping("/profile")
    public String profile(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        return view("profile");
    }
}
