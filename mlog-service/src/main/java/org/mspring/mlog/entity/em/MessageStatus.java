/**
 * 
 */
package org.mspring.mlog.entity.em;

/**
 * @author Gao Youbo
 * @since 2013-3-29
 * @description 消息状态的枚举
 * @TODO
 */
public enum MessageStatus {
    UNREAD(0), READED(1), DELETED(-1);

    private int value = 0;

    private MessageStatus(int value) {
        this.value = value;
    }

    public static MessageStatus valueOf(int value) {
        switch (value) {
        case 0:
            return UNREAD;
        case 1:
            return READED;
        case 2:
            return DELETED;
        default:
            return null;
        }
    }

    public int value() {
        return this.value;
    }
}
