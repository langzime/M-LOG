/**
 * 
 */
package org.mspring.mlog.service;

import java.util.List;

import org.mspring.mlog.entity.Message;
import org.mspring.mlog.entity.em.MessageStatus;

/**
 * @author Gao Youbo
 * @since 2013-3-29
 * @description
 * @TODO
 */
public interface MessageService {
    /**
     * 发送消息
     * 
     * @param content
     *            消息内容
     * @param from
     *            发送人
     * @param to
     *            消息接收人
     * @return
     */
    boolean sendMessage(String content, Long from, Long... to);

    /**
     * 发送系统消息
     * 
     * @param content
     *            消息内容
     * @param to
     *            消息接收人
     * @return
     */
    boolean sendSystemMessage(String content, Long... to);

    /**
     * 发送消息
     * 
     * @param msg
     *            消息对象
     * @param user
     *            发送对象
     * @return
     */
    boolean sendMessage(Message msg, Long... user);

    /**
     * 根据编号获取消息对象
     * 
     * @param id
     *            编号
     * @return 消息
     */
    Message getMessageById(Long id);

    /**
     * 根据User查找消息
     * 
     * @param user
     *            用户
     * @param status
     *            消息状态
     * @return
     */
    List<Message> findMessageByUser(Long user, MessageStatus status);

    /**
     * 根据用户查找消息
     * 
     * @param user
     *            用户
     * @return
     */
    List<Message> findMessageByUser(Long user);
}
