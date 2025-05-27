package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pj.journal.model.board.BoardDao;
import com.pj.journal.model.board.BoardVo;

@Service
public class BoardService {
	
	SqlSessionFactory sqlSessionFactory;

	public BoardService(SqlSessionFactory sqlSessionFactory) {
		super();
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void getBoardList(Model model) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			System.out.println(session.getMapper(BoardDao.class).selectAllBoard());
			model.addAttribute("boardList", session.getMapper(BoardDao.class).selectAllBoard());
//			model.addAttribute("userList",session.getMapper(null));
		}
	}

	public void addBoardList(BoardVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			session.getMapper(BoardDao.class).insertOneBoard(bean);
		}
	}
}
