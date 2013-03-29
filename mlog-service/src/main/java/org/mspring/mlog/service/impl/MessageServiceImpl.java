/**
 * 
 */
package org.mspring.mlog.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.mspring.mlog.entity.Message;
import org.mspring.mlog.entity.UserMessage;
import org.mspring.mlog.entity.em.MessageStatus;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.MessageService;
import org.mspring.platform.core.AbstractServiceSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gao Youbo
 * @since 2013-3-29
 * @description
 * @TODO
 */
@Service
@Transactional
public class MessageServiceImpl extends AbstractServiceSupport implements MessageService {
    private static final Logger log = Logger.getLogger(MessageServiceImpl.class);

    @Override
    public boolean sendMessage(String content, Long from, Long... to) {
        // TODO Auto-generated method stub
        Message msg = new Message();
        msg.setAuthor(new User(from));
        msg.setContent(content);
        return sendMessage(msg, to);
    }

    @Override
    public boolean sendSystemMessage(String content, Long... to) {
        // TODO Auto-generated method stub
        Message msg = new Message();
        msg.setContent(content);
        return sendMessage(msg, to);
    }

    @Override
    public boolean sendMessage(Message msg, Long... user) {
        // TODO Auto-generated method stub
        try {
            if (msg == null) {
                log.warn("could not send message, object is null.");
                return false;
            }
            if (msg.getAuthor() == null) {
                msg.setIsSystem(true);
            } else {
                msg.setIsSystem(false);
            }

            if (!msg.getIsSystem() && (msg.getAuthor() == null || msg.getAuthor().getId() == null)) {
                log.warn("could not send message, message author is null.");
                return false;
            }
            if (user == null || user.length == 0) {
                log.warn("could not send message, to user is null");
                return false;
            }
            msg.setCreateTime(new Date());
            Long msgId = (Long) create(msg);
            UserMessage userMessage = null;
            for (Long u : user) {
                userMessage = new UserMessage(u, msgId, MessageStatus.UNREAD);
                create(userMessage);
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Message getMessageById(Long id) {
        // TODO Auto-generated method stub
        Object message = getById(Message.class, id);
        if (message == null) {
            return null;
        }
        return (Message) message;
    }

    @Override
    public List<Message> findMessageByUser(Long user, MessageStatus status) {
        // TODO Auto-generated method stub
        return find("select um.PK.message from UserMessage um where um.PK.user.id = ? and um.status = ?", user, status);
    }

    @Override
    public List<Message> findMessageByUser(Long user) {
        // TODO Auto-generated method stub
        return find("select um.PK.message from UserMessage um where um.PK.user.id = ?", user);
    }

}
