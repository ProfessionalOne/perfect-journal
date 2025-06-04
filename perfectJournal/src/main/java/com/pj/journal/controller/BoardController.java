package com.pj.journal.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.pj.journal.model.board.BoardVo;
import com.pj.journal.model.comment.CommentVo;
import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.BoardService;
import com.pj.journal.service.CommentService;
import com.pj.journal.service.SftpUploader;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	@Autowired
	CommentService commentService;

	@Autowired
	SftpUploader sftpUploader;

	@Value("${sftp.host}")
	private String SFTP_HOST;

	@Value("${sftp.port}")
	private int SFTP_PORT;

	@Value("${sftp.username}")
	private String SFTP_USER;

	@Value("${sftp.password}")
	private String SFTP_PASS;

	@GetMapping("/")
	public String redirectToBoard() {
		return "redirect:/posts";
	}

	@GetMapping("/posts")
	public String boardList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "latest") String sort, @RequestParam(required = false) String searchField,
			@RequestParam(required = false) String keyword, Model model,
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
		if (loginUser != null && post.getNickname().equals(loginUser.getNickname())) {
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

	public String addBoardList(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "duration", required = false) String duration, HttpSession session,
			@ModelAttribute BoardVo bean) {
		if (!file.isEmpty() && file.getSize() <= 5242880) {
			try {
				String originalFilename = file.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuidFileName = UUID.randomUUID().toString() + ext;

				sftpUploader.upload(file, uuidFileName);

				bean.setImage("/uploads/" + uuidFileName);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		bean.setUserId(loginUser.getUserId());

		if (duration != null && !duration.trim().isEmpty()) {
			try {
				int days = Integer.parseInt(duration);
				bean.setDuration(days);
				bean.setTimeCapsule(true);
				bean.setReleaseDate(LocalDate.now().plusDays(days));
			} catch (NumberFormatException e) {
				bean.setTimeCapsule(false);
				bean.setReleaseDate(null);
			}
		} else {
			bean.setTimeCapsule(false);
			bean.setReleaseDate(null);
		}

		boardService.addBoardList(bean);
		return "redirect:/posts";
	}

	@GetMapping("/posts/{postId}/edit")
	public String editPost(@PathVariable int postId, HttpSession session, Model model) {
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/users/login";
		}

		BoardVo post = boardService.getBoardList(postId);
		model.addAttribute("bean", post);
		return "board/editPost";
	}

	@PutMapping("/posts/{postId}/edit")

	public String editPost(@PathVariable int postId, @RequestParam("file") MultipartFile file, HttpSession session,
			@ModelAttribute BoardVo bean) {
		if (!file.isEmpty()) {
			try {
				String originalFilename = file.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String uuidFileName = UUID.randomUUID().toString() + ext;

				sftpUploader.upload(file, uuidFileName);

				bean.setImage("/uploads/" + uuidFileName);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			String existingImage = boardService.getBoardList(postId).getImage();
			bean.setImage(existingImage);
		}

		bean.setPostId(postId);
		boardService.updateBoardList(bean);
		return "redirect:/posts/" + postId;
	}

	@DeleteMapping("/posts/{postId}")
	public String deletePost(@PathVariable int postId) {
		boardService.deletePost(postId);
		return "redirect:/posts";
	}

}
