/**
 * 
 */
package org.mspring.mlog.web.module.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2013-4-1
 * @description
 * @TODO
 */
@Widget
public class Web_Register extends AbstractWebWidget {
    @RequestMapping("/reg")
    public String reg(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/web/reg";
    }

    @RequestMapping("/reg/save")
    public String reg_save(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response, Model models) {
        
        return "redirect:/reg/success";
    }

    @RequestMapping("/reg/success")
    public String reg_success(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/web/reg_success";
    }
}
