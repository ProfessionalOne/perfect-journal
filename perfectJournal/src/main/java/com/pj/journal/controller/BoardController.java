package com.pj.journal.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pj.journal.model.board.BoardVo;
import com.pj.journal.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	@GetMapping("/")
	public String redirectToBoard() {
		return "redirect:/posts";
	}

	@GetMapping("/posts")
	public String boardList(@RequestParam(defaultValue = "1") int page, Model model) {
		boardService.getBoardList(model, page);
		return "board/home";
	}

	@GetMapping("/posts/{postId}")
	public String detaill(@PathVariable int postId, Model model) {
		model.addAttribute("bean", boardService.getBoardList(postId));
		return "board/detail";
	}
	
	@GetMapping("/posts/create")
	public String addBoardList(){
		return "board/createPost";
	}
	
	@PostMapping("/posts/create")
	public String addBoardList(@RequestParam("file") MultipartFile file, @ModelAttribute BoardVo bean) {

		if (!file.isEmpty()) {
			try {
				String originalFilename = file.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuidFileName = UUID.randomUUID().toString() + ext;

				String uploadPath = new File("src/main/resources/static/images").getAbsolutePath();
				File saveFile = new File(uploadPath, uuidFileName);
				file.transferTo(saveFile);

				bean.setImage("/images/" + uuidFileName);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		bean.setUserId(2);
		boardService.addBoardList(bean);
		return "redirect:/posts";
	}
}
