package org.mspring.mlog.web.module.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mspring.mlog.entity.Comment;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.CommentService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.CommentQueryCriterion;
import org.mspring.mlog.web.security.SecurityUtils;
import org.mspring.platform.persistence.query.QueryCriterion;
import org.mspring.platform.persistence.support.Page;
import org.mspring.platform.persistence.support.Sort;
import org.mspring.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Widget
@RequestMapping("user/comment")
public class User_CommentWidget extends AbstractUserWidget {

	public static final Logger log = Logger.getLogger(User_CommentWidget.class);
	
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 获取评论列表
     * 
     * @param commentPage
     * @param comment
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log
    @RequestMapping("/list")
    public String listComment(@ModelAttribute Page<Comment> commentPage, @ModelAttribute Comment comment, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (comment == null) {
            comment = new Comment();
        }
        if (commentPage == null) {
            commentPage = new Page<Comment>();
        }
        
        if (queryParams.get("status") == null || StringUtils.isBlank(queryParams.get("status").toString())) {
            comment.setStatus(Comment.Status.APPROVED);
            queryParams.put("status", Comment.Status.APPROVED);
        }
        
        commentPage.setSort(new Sort("id", Sort.DESC));
        commentPage.pageSize(10);
        User loginUser = SecurityUtils.getCurrentUser();

        if (loginUser != null) {
            queryParams.put("user.id", loginUser.getId());
        }

        QueryCriterion queryCriterion = new CommentQueryCriterion(queryParams);
        commentPage = commentService.findComment(commentPage, queryCriterion);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("commentStatus", Comment.Status.getCommentStatusMap());
        return view("comment/list");
    }

    /**
     * 删除评论
     * 
     * @param deleteIds
     * @param ids
     * @param commentPage
     * @param comment
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log
    @RequestMapping("/delete")
    public String deleteComments(@RequestParam(required = false) Long[] deleteIds, @RequestParam(required = false) Long[] ids, @ModelAttribute Page<Comment> commentPage, @ModelAttribute Comment comment, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (deleteIds != null && deleteIds.length > 0) {
            commentService.deleteComment(deleteIds);
        }
        return listComment(commentPage, comment, queryParams, request, response, model);
    }

    /**
     * 显示该评论的详细信息
     * 
     * @param id
     * @param commentPage
     * @param comment
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log
    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String showCommentView(@RequestParam(required = false) Long id, @ModelAttribute Page<Comment> commentPage, @ModelAttribute Comment comment, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        Comment commentView = commentService.getCommentById(id);
        model.addAttribute("comment", commentView);
        return view("comment/view");
    }

    /**
     * 审核评论
     * 
     * @param commentPage
     * @param comment
     * @param queryParams
     * @param request
     * @param response
     * @param model
     * @return 评论列表
     */
    @RequestMapping("/audit")
    @Log
    public String auditComment(@RequestParam(required = false) Long[] id, @ModelAttribute Page<Comment> commentPage, @ModelAttribute Comment comment, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        String status = request.getParameter("type");
        if (id != null && id.length > 0 && StringUtils.isNotBlank(status)) {
            if (Comment.Status.APPROVED.equals(status)) {
                commentService.approved(id);
            } else if (Comment.Status.SPAM.equals(status)) {
                commentService.spam(id);
            } else if (Comment.Status.RECYCLE.equals(status)) {
                commentService.recycle(id);
            } else {
                log.warn("update comment status failure, status [" + status + "] is illegal");
            }
        }
        return listComment(commentPage, comment, queryParams, request, response, model);
    }
}