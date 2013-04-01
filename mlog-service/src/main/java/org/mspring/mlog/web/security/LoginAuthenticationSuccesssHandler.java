/**
 * 
 */
package org.mspring.mlog.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * @author Gao Youbo
 * @since 2013-1-14
 * @Description
 * @TODO
 */
public class LoginAuthenticationSuccesssHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger log = Logger.getLogger(LoginAuthenticationFailureHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();
    
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.
     * SimpleUrlAuthenticationSuccessHandler
     * #onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        //获取默认的登录成功跳转页
        String defaultTargetUrl = org.mspring.platform.utils.StringUtils.isNotBlank(request.getParameter("targetUrl")) ? request.getParameter("targetUrl") : getTargetUrlParameter();
        if (savedRequest == null) {
            getRedirectStrategy().sendRedirect(request, response, defaultTargetUrl);
            return;
        }
        if (isAlwaysUseDefaultTargetUrl() || (defaultTargetUrl != null && StringUtils.hasText(request.getParameter(defaultTargetUrl)))) {
            requestCache.removeRequest(request, response);
            getRedirectStrategy().sendRedirect(request, response, defaultTargetUrl);
            return;
        }

        clearAuthenticationAttributes(request);
        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        log.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
