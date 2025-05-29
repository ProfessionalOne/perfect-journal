package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.pj.journal.model.comment.CommentVo;
import com.pj.journal.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public String addComment(@PathVariable int postId, @ModelAttribute CommentVo bean) {
		commentService.addComment(bean, bean.getGroupId());

		return "redirect:/posts/" + postId;
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public String editComment(@PathVariable int postId, @PathVariable int commentId, @ModelAttribute CommentVo bean) {
		commentService.updateComment(bean);
		
		return "redirect:/posts/" + postId;
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public String updateIsDeleted(@PathVariable int postId, @PathVariable int commentId) {
		commentService.updateCommentIsDeleted(commentId);
		
		return "redirect:/posts/" + postId;
	}

}
