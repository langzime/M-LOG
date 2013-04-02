package org.mspring.mlog.web.module.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Widget
@RequestMapping("user/comment")
public class User_CommentWidget extends AbstractUserWidget{

	private CommentService commentService;
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Log
	@RequestMapping("/list")
    public String listComment(@ModelAttribute Page<Comment> commentPage, @ModelAttribute Comment comment, @QueryParam Map queryParams, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (comment == null) {
            comment = new Comment();
        }
        if (commentPage == null) {
            commentPage = new Page<Comment>();
        }
        commentPage.setSort(new Sort("id", Sort.DESC));
        
        User loginUser = SecurityUtils.getCurrentUser();
        
        if(loginUser!=null){
        	queryParams.put("user.id", loginUser.getId());
        }
        
        QueryCriterion queryCriterion = new CommentQueryCriterion(queryParams);
        commentPage = commentService.findComment(commentPage, queryCriterion);
        model.addAttribute("commentPage", commentPage);
        return view("comment/list");
    }
}
