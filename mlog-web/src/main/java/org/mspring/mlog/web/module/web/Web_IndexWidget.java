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
 * @since 2013-3-28
 * @description
 * @TODO
 */
@Widget
@RequestMapping("/")
public class Web_IndexWidget extends AbstractWebWidget {
    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        return "/web/index";
    }
}
