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
			@RequestParam(defaultValue = "latest") String sort, @RequestParam(required = false) String searchField,
			@RequestParam(required = false) String keyword, Model model,
			HttpSession session, @RequestParam(required = false) Boolean onlyMine) {
		
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		
		//로그인 되지 않은 상태에서 '내 글만 보기' 옵션을 사용했을 경우
		//이 부분 지우시면 로그인 안 한 상태에서 누를 때 그냥 빈 리스트만 출력하게 됩니다
	    if (Boolean.TRUE.equals(onlyMine) && loginUser == null) {
	    	model.addAttribute("msg", "로그인 후 내 글만 보기 옵션을 사용할 수 있습니다.");
	        return "redirect:/users/login";
	
	    }
		boardService.getBoardList(model, page, sort, searchField, keyword, loginUser, onlyMine);
		System.out.println(loginUser);
		return "board/home";
	}

	@GetMapping("/posts/{postId}")
	@Transactional
	public String detail(@PathVariable int postId, HttpSession session, Model model) {
		BoardVo post = boardService.getBoardList(postId);
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		
	    if (post.getIsLocked()==1) {
	        if (loginUser == null || !(post.getUserId()==(loginUser.getUserId()))) {
	            // 권한 없으면 에러페이지 혹은 안내
	            model.addAttribute("errorMsg", "비밀글입니다. 본인만 볼 수 있습니다.");
	            return "board/home"; 
	        }
	    }

	    model.addAttribute("bean", post);

		List<CommentVo> comments = commentService.getCommentList(postId);
		model.addAttribute("commentsList", comments);

		
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
			, @ModelAttribute BoardVo bean, @RequestParam(value = "isLocked", required = false) int isLocked) {
		if (!file.isEmpty() && file.getSize() <= 5242880) {
			try {
				String originalFilename = file.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuidFileName = UUID.randomUUID().toString() + ext;

				String uploadPath = new File("src/main/resources/static/images").getAbsolutePath();
				File saveFile = new File(uploadPath, uuidFileName);
				file.transferTo(saveFile);
				
				bean.setImage("/images/" + uuidFileName);
				bean.setIsLocked(1==(isLocked) ? 1 : 0);
				System.out.println("isLocked: " + bean.getIsLocked());
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		bean.setUserId(loginUser.getUserId());
		boardService.addBoardList(bean);
		System.out.println("isLocked: " + bean.getIsLocked());
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
			@ModelAttribute BoardVo bean, @RequestParam(value = "isLocked", required = false) int isLocked) {
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
				bean.setIsLocked(1==(isLocked) ? 1 : 0);
				System.out.println("isLocked: " + bean.getIsLocked());

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String existingImage = boardService.getBoardList(postId).getImage();
			bean.setImage(existingImage);
		}

		bean.setPostId(postId);
		boardService.updateBoardList(bean);
		System.out.println("isLocked: " + bean.getIsLocked());
		return "redirect:/posts/"+postId;
	}

	@DeleteMapping("/posts/{postId}")
	public String deletePost(@PathVariable int postId) {
		boardService.deletePost(postId);
		return "redirect:/posts";
	}

}
