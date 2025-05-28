package com.pj.journal.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.journal.model.board.BoardDao;
import com.pj.journal.model.comment.CommentDao;
import com.pj.journal.model.comment.CommentVo;

@Service
@Transactional
public class CommentService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    private CommentDao commentDao;

    public List<CommentVo> getCommentList(int postId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(CommentDao.class).selectAllCommentnReply(postId);
		}
        }


    public void addComment(CommentVo bean) {
        commentDao.insertOneComment(bean);
    }


    public void addReply(CommentVo bean) {
        commentDao.insertOneReply(bean);
    }

    public int editCommentnReply(CommentVo bean) {
        int count = commentDao.updateOneCommentnReply(bean);
        if (count == 0) {
            throw new NoSuchElementException("해당 댓글이 없거나 수정 권한이 없습니다.");
        }
        return count;
    }

    public int deleteCommentnReply(CommentVo bean) {
        int count = commentDao.deleteOneCommentnReply(bean);
        if (count == 0) {
            throw new NoSuchElementException("해당 댓글이 없거나 삭제 권한이 없습니다.");
        }
        return count;
    }
}


