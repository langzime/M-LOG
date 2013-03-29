/**
 * 
 */
package org.mspring.mlog.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.mspring.mlog.entity.em.MessageStatus;

/**
 * @author Gao Youbo
 * @since 2013-3-28
 * @description
 * @TODO
 */
@Entity
@Table(name = "user_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7645226052019566745L;

    private UserMessagePK PK;

    /**
     * 消息状态 0=未读 1=已读 -1=删除
     */
    private MessageStatus status;

    /**
     * 
     */
    public UserMessage() {
        // TODO Auto-generated constructor stub
    }

    public UserMessage(UserMessagePK pK, MessageStatus status) {
        super();
        this.PK = pK;
        this.status = status;
    }

    public UserMessage(Long user, Long message, MessageStatus status) {
        super();
        this.PK = new UserMessagePK(user, message);
        this.status = status;
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)), @AttributeOverride(name = "messageId", column = @Column(name = "message_id", nullable = false)) })
    public UserMessagePK getPK() {
        return PK;
    }

    public void setPK(UserMessagePK pK) {
        PK = pK;
    }

    @Column(name = "status", nullable = false)
    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public static final class Status {
        public static final Integer UNREAD = 0;
        public static final Integer READED = 1;
        public static final Integer DELETED = -1;
    }
}
