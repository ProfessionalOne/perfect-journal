package com.pj.journal.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pj.journal.model.board.BoardVo;
import com.pj.journal.model.comment.CommentVo;
import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.BoardService;
import com.pj.journal.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@Autowired
	CommentService commentService;

	@GetMapping("/")
	public String redirectToBoard() {
		return "redirect:/posts";
	}

	@GetMapping("/posts")
	public String boardList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "latest") String sort,
			@RequestParam(required = false) String searchField,
			@RequestParam(required = false) String keyword,
			Model model,
			jakarta.servlet.http.HttpServletResponse response) {

			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			boardService.getBoardList(model, page, sort, searchField, keyword);
			return "board/home";
	}


	@GetMapping("/posts/{postId}")
	@Transactional
	public String detail(@PathVariable int postId, HttpSession session, Model model) {
		
		boardService.increaseViews(postId);
		
		BoardVo post = boardService.getBoardList(postId);
		model.addAttribute("bean", post);

		List<CommentVo> comments = commentService.getCommentList(postId);
		model.addAttribute("commentsList", comments);

		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		boolean isOwner = false;
		if(loginUser != null && post.getNickname().equals(loginUser.getNickname())) {
			isOwner = true;
		}
		model.addAttribute("isOwner", isOwner);
		return "board/detail";
	}

	@GetMapping("/posts/create")
	public String addBoardList() {
		return "board/createPost";
	}

	@PostMapping("/posts/create")
	public String addBoardList(@RequestParam("file") MultipartFile file
			, HttpSession session
			, @ModelAttribute BoardVo bean) {
		if (!file.isEmpty() && file.getSize() <= 5242880) {
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
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		bean.setUserId(loginUser.getUserId());
		boardService.addBoardList(bean);
		return "redirect:/posts";
	}

	@GetMapping("/posts/{postId}/edit")
	public String editPost(@PathVariable int postId, Model model) {
		BoardVo post = boardService.getBoardList(postId);
		model.addAttribute("bean", post);
		return "board/editPost";
	}

	@PutMapping("/posts/{postId}/edit")
	public String editPost(@PathVariable int postId, @RequestParam("file") MultipartFile file,
			@ModelAttribute BoardVo bean) {
		if (!file.isEmpty()) {
			try {
				String originalFilename = file.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuidFileName = UUID.randomUUID().toString() + ext;

				String uploadPath = new File("src/main/resources/static/images").getAbsolutePath();
				System.out.println(uploadPath);
				File saveFile = new File(uploadPath, uuidFileName);
				file.transferTo(saveFile);

				bean.setImage("/images/" + uuidFileName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String existingImage = boardService.getBoardList(postId).getImage();
			bean.setImage(existingImage);
		}

		bean.setPostId(postId);
		boardService.updateBoardList(bean);
		return "redirect:/posts/"+postId;
	}

	@DeleteMapping("/posts/{postId}")
	public String deletePost(@PathVariable int postId) {
		boardService.deletePost(postId);
		return "redirect:/posts";
	}

}
