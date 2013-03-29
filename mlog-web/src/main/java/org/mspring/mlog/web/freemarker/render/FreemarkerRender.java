/**
 * 
 */
package org.mspring.mlog.web.freemarker.render;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ClassUtils;
import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.service.OptionService;
import org.mspring.mlog.web.freemarker.directive.AbstractDirectiveModel;
import org.mspring.platform.utils.PropertiesUtils;
import org.mspring.platform.utils.StringUtils;
import org.mspring.platform.web.Keys;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;

/**
 * @author Gao Youbo
 * @since 2013-3-20
 * @description
 * @TODO
 */
public class FreemarkerRender {
    protected static TaglibFactory taglibFactory = null;
    protected static ServletContextHashModel servletContextHashModel = null;
    protected static Map<String, Object> variables = null;
    protected static Configuration configuration = null;

    protected String template;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public FreemarkerRender(String template, HttpServletRequest request, HttpServletResponse response) {
        super();
        this.template = template;
        this.request = request;
        this.response = response;
    }

    /**
     * build模板模型
     * 
     * @param model
     */
    public SimpleHash buildTemplateModel() {
        init();
        Map<Object, Object> model = processVariables();

        AllHttpScopesHashModel fmModel = new AllHttpScopesHashModel(getObjectWrapper(), request.getSession().getServletContext(), request);
        fmModel.put(FreemarkerServlet.KEY_JSP_TAGLIBS, taglibFactory);
        fmModel.put(FreemarkerServlet.KEY_APPLICATION, servletContextHashModel);
        fmModel.put(FreemarkerServlet.KEY_SESSION, buildSessionModel(request, response));
        fmModel.put(FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, getObjectWrapper()));
        fmModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
        fmModel.putAll(model);
        return fmModel;
    }

    /**
     * 初始化调用
     */
    protected void init() {
        configuration = FreemarkerRenderUtils.getConfiguration();
        addVariables();

        if (taglibFactory == null) {
            taglibFactory = new TaglibFactory(request.getSession().getServletContext());
        }
        if (servletContextHashModel == null) {
            GenericServlet servlet = new GenericServletAdapter();
            try {
                servlet.init(new DelegatingServletConfig());
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
            servletContextHashModel = new ServletContextHashModel(servlet, getObjectWrapper());
        }
    }

    /**
     * 获取freemarker配置
     * 
     * @return
     */
    public Configuration getFreemarkerConfiguration() {
        return configuration;
    }

    /**
     * 初始化模板变量
     * 
     * @return
     */
    protected Map<Object, Object> processVariables() {
        final OptionService optionService = ServiceFactory.getOptionService();

        Map<Object, Object> model = new HashMap<Object, Object>();
        Map<String, String> options = optionService.getOptions();
        model.putAll(options);
        model.put(Keys.REQUEST_KEY_BASE, request.getContextPath());
        try {
            model.put("m", taglibFactory.get("/WEB-INF/tld/mspring.tld"));
        } catch (TemplateModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 获取variables.properties中配置的variable
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    private void addVariables() {
        if (variables == null) {
            variables = new HashMap<String, Object>();
            Map<String, String> map = PropertiesUtils.getPropertyMap("variables.properties");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (StringUtils.isNotBlank(key)) {
                    try {
                        Class clazz = ClassUtils.getClass(value);
                        if (AbstractDirectiveModel.class.isAssignableFrom(clazz)) {
                            AbstractDirectiveModel model = (AbstractDirectiveModel) clazz.newInstance();
                            variables.put(key, model);
                        } else {
                            variables.put(key, value);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        variables.put(key, value);
                    }
                }
            }
            try {
                configuration.setAllSharedVariables(new SimpleHash(variables, configuration.getObjectWrapper()));
            } catch (TemplateModelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    protected ObjectWrapper getObjectWrapper() {
        ObjectWrapper ow = FreemarkerRenderUtils.getConfiguration().getObjectWrapper();
        return (ow != null ? ow : ObjectWrapper.DEFAULT_WRAPPER);
    }

    private HttpSessionHashModel buildSessionModel(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return new HttpSessionHashModel(session, getObjectWrapper());
        } else {
            return new HttpSessionHashModel(null, request, response, getObjectWrapper());
        }
    }

    private static class GenericServletAdapter extends GenericServlet {
        private static final long serialVersionUID = -6470270892327719566L;

        @Override
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        }
    }

    private class DelegatingServletConfig implements ServletConfig {

        @Override
        public String getServletName() {
            // TODO Auto-generated method stub
            return template;
        }

        @Override
        public ServletContext getServletContext() {
            // TODO Auto-generated method stub
            return request.getSession().getServletContext();
        }

        @Override
        public Enumeration<String> getInitParameterNames() {
            // TODO Auto-generated method stub
            return Collections.enumeration(new HashSet<String>());
        }

        @Override
        public String getInitParameter(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
