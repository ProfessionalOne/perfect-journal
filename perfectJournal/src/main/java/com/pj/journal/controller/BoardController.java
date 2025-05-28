package com.pj.journal.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
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

import com.pj.journal.model.board.BoardDao;
import com.pj.journal.model.board.BoardVo;
import com.pj.journal.model.comment.CommentVo;
import com.pj.journal.service.BoardService;
import com.pj.journal.service.CommentService;

import jakarta.servlet.http.HttpSession;


@Controller
public class BoardController {
	@Autowired
    private BoardDao boardDao;
	
	@Autowired
	BoardService boardService;
	private CommentService commentService;

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
	public String detail(@PathVariable int postId, Model model) {
		 BoardVo post = boardService.getBoardList(postId);
	        model.addAttribute("post", post);
	        
	        List<CommentVo> comments = commentService.getCommentList(postId);
	        model.addAttribute("commentNReplyList", comments);

	        return "board/detail";
	    }
	@PostMapping("/posts/{postId}/comment")
	public String postComment(
	        @PathVariable int postId,
	        @RequestParam(defaultValue = "0") int level,
	        @RequestParam String content
	        //,HttpSession session  // 로그인할때 spring security 사용하시나요? 
	) {
		
	    CommentVo vo = new CommentVo();
	    vo.setPostId(postId);
	    //Integer userId = (Integer)session.getAttribute("userId");
	    //vo.setUserId(userId);
	    vo.setContent(content);

	    if (level == 0) {
	        // 새 댓글
	        commentService.addComment(vo);
	    } else {
	        // 대댓글(이대로 하면 아마 오류생길거예요 내일 와서 수정할게요)
	        commentService.addReply(vo);
	        vo.setLevel(level);
	    }

	    return "redirect:/board/detail/" + postId;
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
	@PostMapping("/posts/delete")
	public String deletePost(@RequestParam("postId") int postId) {
	    boardDao.deleteOneBoard(postId);
	    return "redirect:/posts";
	}

}
