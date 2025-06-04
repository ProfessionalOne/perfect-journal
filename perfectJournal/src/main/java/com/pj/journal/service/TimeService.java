package com.pj.journal.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pj.journal.model.board.BoardDao;
import com.pj.journal.model.board.BoardVo;

@Service
public class TimeService {
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	public void getBoardList(int userId, Model model, int page, String sort, String field, String keyword, Boolean onlyMine) {
		int pageSize = 10;
		int offset = (page - 1) * pageSize;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			BoardDao boardDao = session.getMapper(BoardDao.class);
 
			List<BoardVo> boardList = boardDao.selectByTime(userId, offset, pageSize, sort, field, keyword, onlyMine);

			int totalCount = boardDao.getTotalCount(offset, pageSize, sort, field, keyword, userId, onlyMine, 1);
			int totalPages = (int) Math.ceil((double) totalCount / pageSize);
			int beginPage = pageSize * ((page - 1) / pageSize) + 1;
			int endPage = beginPage + (pageSize - 1);
			if (endPage > totalPages) {
				endPage = totalPages;
			}

			model.addAttribute("boardList", boardList);
			model.addAttribute("currentPage", page);
			model.addAttribute("beginPage", beginPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("currentSort", sort);
			model.addAttribute("searchField", field);
			model.addAttribute("keyword", keyword);
			model.addAttribute("today", LocalDate.now());
		}
	}
	
	public BoardVo getBoardList(int postId, Integer viewerUserId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BoardDao dao = session.getMapper(BoardDao.class);
	        BoardVo post = dao.selectOneBoard(postId);

	        if (post == null) return null;

	        boolean isTimeCapsule = post.isTimeCapsule();
	        boolean isOwner = viewerUserId != null && post.getUserId() == viewerUserId;
	        boolean isReleased = post.getReleaseDate() != null &&
	                             !post.getReleaseDate().isAfter(LocalDate.now());

	        // 💡 조건: 타임캡슐이고 공개 전이고 작성자도 아닌 경우
	        if (isTimeCapsule && !isReleased && !isOwner) {
	            post.setContent("⏳ 공개일이 되면 확인할 수 있어요.");
	            post.setImage("/images/Journie_Moment_logo.png");
	        }

	        return post;
		}
	}
}
