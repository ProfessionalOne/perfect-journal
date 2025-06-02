package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pj.journal.model.board.BoardVo;
import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.TimeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TimeController {
	@Autowired
	TimeService timeService;
	
	@GetMapping("/time")
	public String timeList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "latest") String sort, @RequestParam(required = false) String searchField,
			@RequestParam(required = false) String keyword,
			HttpSession session, Model model) {
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if(loginUser == null) {
			return "redirect:/users/login";
		}
		int userId = loginUser.getUserId();
		timeService.getBoardList(userId, model, page, sort, searchField, keyword);
		return "board/timeCapsule";
	}
	
	@GetMapping("/time/{postId}")
	@Transactional
	public String detail(@PathVariable int postId, HttpSession session, Model model) {
		BoardVo post = timeService.getBoardList(postId);
		model.addAttribute("bean", post);

		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		boolean isOwner = false;
		if(loginUser != null && post.getNickname().equals(loginUser.getNickname())) {
			isOwner = true;
		}
		model.addAttribute("isOwner", isOwner);
		return "board/detail";
	}
}
