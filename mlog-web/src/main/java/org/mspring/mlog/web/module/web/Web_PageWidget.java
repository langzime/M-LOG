/**
 * 
 */
package org.mspring.mlog.web.module.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Page;
import org.mspring.mlog.service.PageService;
import org.mspring.mlog.web.DwrPush;
import org.mspring.mlog.web.freemarker.render.FreemarkerRender;
import org.mspring.mlog.web.freemarker.render.FreemarkerRenderUtils;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.module.AbstractWidget;
import org.mspring.platform.utils.DateUtils;
import org.mspring.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import freemarker.template.SimpleHash;

/**
 * @author Gao Youbo
 * @since 2013-3-19
 * @description
 * @TODO
 */
@Widget
public class Web_PageWidget extends AbstractWidget {
    @Autowired
    private PageService pageService;

    @RequestMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return;
        }
        Page page = pageService.getPageById(new Long(id));
        if (page == null) {
            return;
        }
        String content = page.getContent();
        FreemarkerRender render = new FreemarkerRender(page.getId().toString(), request, response);
        SimpleHash hash = render.buildTemplateModel();
        String html = FreemarkerRenderUtils.renderString(page.getId().toString(), content, hash);

        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.write(html);
            writer.close();
        } catch (IOException e) {
        }
    }

    @RequestMapping("/p/{page}")
    public String p(@PathVariable String page) {
        return "/user/" + page;
    }
}
