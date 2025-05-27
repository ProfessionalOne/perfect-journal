package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pj.journal.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@GetMapping("/")
	public String boardList(Model model) {
		boardService.getBoardList(model);
		return "home";
	}

}
