/**
 * 
 */
package org.mspring.mlog.web.module.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Message;
import org.mspring.mlog.entity.em.MessageStatus;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.MessageService;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.web.render.JSONRender;
import org.mspring.platform.web.render.TextRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gao Youbo
 * @since 2013-3-29
 * @description
 * @TODO
 */
@Widget
public class Web_MessageWidget {
    @Autowired
    private MessageService messageService;

    /**
     * 通过Ajax获取未读消息的接口
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get_message")
    public void getMessage(HttpServletRequest request, HttpServletResponse response) {
        User user = SecurityUtils.getCurrentUser(request);
        if (user == null) {
            return;
        }
        List<Message> messages = messageService.findMessageByUser(user.getId(), MessageStatus.UNREAD);
        JSONRender render = new JSONRender(messages);
        render.render(response);
    }

    /**
     * 通过Ajax获取未读消息的数量
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get_unread_message_count")
    public void getUnreadCount(HttpServletRequest request, HttpServletResponse response) {
        User user = SecurityUtils.getCurrentUser(request);
        if (user == null) {
            return;
        }
        int count = messageService.findUnreadMessageCount(user.getId());
        TextRender render = new TextRender(count + "");
        render.render(response);
    }

}
