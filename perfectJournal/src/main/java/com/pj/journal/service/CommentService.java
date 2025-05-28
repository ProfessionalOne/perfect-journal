package com.pj.journal.service;

import java.util.List;

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
    private CommentDao commentDao;

    public void getCommentList(int postId, Model model) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<CommentVo> commentList = session.getMapper(CommentDao.class).selectAllCommentnReply(postId);
            model.addAttribute("commentNReplyList", commentList);
        }
    }

    @Transactional
    public void addComment(CommentVo bean) {
        commentDao.insertOneComment(bean);
    }

    @Transactional
    public void addReply(CommentVo bean) {
        commentDao.insertOneReply(bean);
    }

    public void editCommentnReply(CommentVo bean) {
        commentDao.updateOneCommentnReply(bean);
    }

    public void deleteCommentnReply(CommentVo bean) {
        commentDao.deleteOneCommentnReply(bean);
    }
}


