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

/**
 * @author Gao Youbo
 * @since 2013-3-28
 * @description
 * @TODO
 */
@Entity
@Table(name = "user_message")
public class UserMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7645226052019566745L;

    private UserMessagePK PK;

    /**
     * 消息状态 0=未读 1=已读 -1=删除
     */
    private Integer status;

    /**
     * 
     */
    public UserMessage() {
        // TODO Auto-generated constructor stub
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
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
