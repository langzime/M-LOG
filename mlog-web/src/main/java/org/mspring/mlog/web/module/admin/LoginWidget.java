/**
 * 
 */
package org.mspring.mlog.web.module.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mspring.mlog.common.Keys;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.security.UserService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Gao Youbo
 * @since 2012-7-16
 * @Description
 * @TODO
 */
@Widget
@RequestMapping("/admin")
public class LoginWidget extends AbstractAdminWidget {

    private static final String MESSAGE = "message";

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
    @Log
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        User currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser != null) {
            return "redirect:/admin/index";
        }
        return "/admin/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        session.invalidate();
        CookieUtils.setCookie(response, Keys.IS_REMEMBER_USER_COOKIE, "false", 365);
        return "/admin/login";
    }
}
