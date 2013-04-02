/**
 * 
 */
package org.mspring.mlog.web.security.rememberme;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * @author Gao Youbo
 * @since 2013-3-20
 * @description
 * @TODO
 */
public class RememberMeAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String key;

    @Deprecated
    public RememberMeAuthenticationProvider() {
    }

    public RememberMeAuthenticationProvider(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    /**
     * 
     * @deprecated Use constructor injection
     */
    @Deprecated
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        // TODO Auto-generated method stub
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        Assert.hasLength(key);
        Assert.notNull(this.messages, "A message source must be set");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (this.key.hashCode() != ((RememberMeAuthenticationToken) authentication).getKeyHash()) {
            throw new BadCredentialsException(messages.getMessage("RememberMeAuthenticationProvider.incorrectKey", "The presented RememberMeAuthenticationToken does not contain the expected key"));
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return (RememberMeAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
