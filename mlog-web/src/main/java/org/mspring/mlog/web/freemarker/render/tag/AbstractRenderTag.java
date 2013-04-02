/**
 * 
 */
package org.mspring.mlog.web.freemarker.render.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.mspring.mlog.utils.SkinUtils;
import org.mspring.mlog.web.freemarker.render.FreemarkerRender;
import org.mspring.platform.utils.FreemarkerUtils;

import freemarker.template.SimpleHash;

/**
 * @author Gao Youbo
 * @since 2013-2-22
 * @description
 * @TODO
 */
public abstract class AbstractRenderTag extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = -1648921326007606729L;

    protected String template;

    public void setTemplate(String template) {
        this.template = template;
    }

    protected static FreemarkerRender render;

    @Override
    public int doEndTag() throws JspException {
        // TODO Auto-generated method stub
        render = new FreemarkerRender(template, (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        SimpleHash hash = render.buildTemplateModel();
        try {
            String result = process(hash);
            pageContext.getOut().append(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    protected abstract String process(SimpleHash model) throws Exception;

    /**
     * 渲染模板
     * 
     * @param model
     * @return
     */
    protected String getTemplateString(SimpleHash model) {
        String view = SkinUtils.getWidgetUrl(template);
        return FreemarkerUtils.render(render.getFreemarkerConfiguration(), view, model);
    }

}
