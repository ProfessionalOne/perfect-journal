package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.pj.journal.model.comment.CommentDao;
import com.pj.journal.model.comment.CommentVo;

@Service
public class CommentService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	public void getCommentList(Model model) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			model.addAttribute("commentNReplyList", session.getMapper(CommentDao.class).selectAllCommentnReply());
		}
	}
	@Transactional
	public void addComment(CommentVo bean) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			session.getMapper(CommentDao.class).insertOneComment(bean);
		}
	}
	@Transactional
	public void addReply(CommentVo bean) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			session.getMapper(CommentDao.class).insertOneReply(bean);
		}
	}
	public void editCommentnReply(CommentVo bean) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			session.getMapper(CommentDao.class).updateOneCommentnReply(bean);
		}
	}
	public void deleteCommentnReply(int CommentId) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			session.getMapper(CommentDao.class).deleteOneCommentnReply(CommentId);
		}
	}
}


