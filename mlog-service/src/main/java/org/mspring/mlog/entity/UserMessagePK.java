/**
 * 
 */
package org.mspring.mlog.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.mspring.mlog.entity.security.User;

/**
 * @author Gao Youbo
 * @since 2013-3-28
 * @description
 * @TODO
 */
@Embeddable
public class UserMessagePK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7114347562928968230L;

    private User user;
    private Message message;

    /**
     * 
     */
    public UserMessagePK() {
        // TODO Auto-generated constructor stub
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Message.class)
    @JoinColumn(name = "message_id")
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
