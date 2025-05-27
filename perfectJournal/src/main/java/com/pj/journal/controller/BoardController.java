package com.pj.journal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pj.journal.model.board.BoardVo;
import com.pj.journal.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	@GetMapping("/")
    public String redirectToBoard() {
        return "redirect:/board";
    }

	@GetMapping("/board")
    public String boardList(@RequestParam(defaultValue = "1") int page, Model model) {
        boardService.getBoardList(model, page);  // 페이지 정보를 넘겨줌
        return "home";  // home.jsp or home.html (Thymeleaf)
    }

}
