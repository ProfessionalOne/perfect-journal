package com.pj.journal.service;

import java.util.List;

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

	public void getBoardList(Model model, int page) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardDao boardDao = session.getMapper(BoardDao.class);

            List<BoardVo> boardList = boardDao.selectByPage(offset, pageSize);
            int totalCount = boardDao.getTotalCount();
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
        }
    }
	public BoardVo getBoardList(int postId) {
		 try (SqlSession session = sqlSessionFactory.openSession()) {
	            return session.getMapper(BoardDao.class).selectOneBoard(postId);
		 }
	}
	
	public void addBoardList(BoardVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			session.getMapper(BoardDao.class).insertOneBoard(bean);
		}
	}

	public void updateBoardList(BoardVo bean) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			session.getMapper(BoardDao.class).updatetOneBoard(bean);
		}
	}
	

}
