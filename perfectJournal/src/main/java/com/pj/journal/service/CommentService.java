package com.pj.journal.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.journal.model.comment.CommentDao;
import com.pj.journal.model.comment.CommentVo;

@Service
public class CommentService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	public List<CommentVo> getCommentList(int postId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(CommentDao.class).selectAllComments(postId);
		}
	}

	@Transactional
	public void addComment(CommentVo comment, int targetGroupId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			if (comment.getLevel() == 0) {
				comment.setGroupId(0);
				session.getMapper(CommentDao.class).insertComment(comment);
				comment.setGroupId(comment.getCommentId()); // 생성된 commentId를 groupId로 설정
				session.getMapper(CommentDao.class).updateGroupId(comment.getCommentId(), comment.getGroupId());
			} else {
				comment. setGroupId(targetGroupId);
				session.getMapper(CommentDao.class).insertComment(comment);
			}

		}

	}

	public void updateComment(CommentVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.getMapper(CommentDao.class).updateComment(bean);
		}
	}

	public void updateCommentIsDeleted(int commentId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.getMapper(CommentDao.class).updateCommentIsDeleted(commentId);
		}
	}

}
