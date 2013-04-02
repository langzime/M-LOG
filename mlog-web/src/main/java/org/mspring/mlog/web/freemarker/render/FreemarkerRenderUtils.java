/**
 * 
 */
package org.mspring.mlog.web.freemarker.render;

import java.io.IOException;

import org.mspring.mlog.web.freemarker.ExtendsFreeMarkerConfigurer;
import org.mspring.platform.core.ContextManager;
import org.mspring.platform.utils.FreemarkerUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Gao Youbo
 * @since 2013-3-20
 * @description
 * @TODO
 */
public class FreemarkerRenderUtils {
    /**
     * 获取freemarker配置
     * 
     * @return
     */
    public static Configuration getConfiguration() {
        return ContextManager.getApplicationContext().getBean(ExtendsFreeMarkerConfigurer.class).getConfiguration();
    }

    /**
     * 渲染
     * 
     * @param template
     * @param Model
     * @return
     */
    public static String render(String template, Object Model) {
        Configuration configuration = getConfiguration();
        Template temp = FreemarkerUtils.getTemplate(configuration, template);
        return FreemarkerUtils.render(temp, Model);
    }

    /**
     * 渲染字符串
     * 
     * @param name
     * @param content
     * @param model
     * @return
     */
    public static String renderString(String name, String content, Object model) {
        Configuration configuration = FreemarkerUtils.createConfiguration("UTF-8");
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(name, content);
        configuration.setTemplateLoader(loader);
        try {
            Template temp = configuration.getTemplate(name);
            return FreemarkerUtils.render(temp, model);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
