package com.pj.journal.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.pj.journal.model.comment.CommentVo;
import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
	@Autowired
	CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public String addComment(@PathVariable int postId
			, @ModelAttribute CommentVo bean
			, HttpSession session) {
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
		    return "redirect:/users/login";
		}bean.setUserId(loginUser.getUserId());
		bean.setCreatedAt(LocalDateTime.now());
		bean.setUpdatedAt(LocalDateTime.now());
		commentService.addComment(bean, bean.getGroupId());

		return "redirect:/posts/" + postId;
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public String editComment(@PathVariable int postId, @PathVariable int commentId, @ModelAttribute CommentVo bean) {
		bean.setUpdatedAt(LocalDateTime.now());
		commentService.updateComment(bean);
		
		return "redirect:/posts/" + postId;
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public String updateIsDeleted(@PathVariable int postId, @PathVariable int commentId) {
		commentService.updateCommentIsDeleted(commentId);
		
		return "redirect:/posts/" + postId;
	}

}
