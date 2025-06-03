package com.pj.journal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pj.journal.model.board.BoardDao;
import com.pj.journal.model.board.BoardVo;

@Service
public class BoardService {
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	public void getBoardList(Model model, int page, String sort, String field, String keyword) {
		int pageSize = 10;
		int offset = (page - 1) * pageSize;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			BoardDao boardDao = session.getMapper(BoardDao.class);

			List<BoardVo> boardList = boardDao.selectBySearch(offset, pageSize, sort, field, keyword);
			int totalCount = boardDao.getTotalCount(field, keyword);
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
		}
	}

	public BoardVo getBoardList(int postId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(BoardDao.class).selectOneBoard(postId);
		}
	}

	public void addBoardList(BoardVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			LocalDate now = LocalDate.now();
			LocalDate releaseDate = now.plusDays(bean.getDuration());
			bean.setCreatedAt(LocalDateTime.now());
			bean.setReleaseDate(releaseDate);
			bean.setTimeCapsule(true);
			session.getMapper(BoardDao.class).insertOneBoard(bean);
		}
	}

	public void updateBoardList(BoardVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			session.getMapper(BoardDao.class).updatetOneBoard(bean);
		}
	}

	public void deletePost(int postId) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			session.getMapper(BoardDao.class).deleteOneBoard(postId);
		}
	}

}
