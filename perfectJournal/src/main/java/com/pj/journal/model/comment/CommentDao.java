package com.pj.journal.model.comment;

import java.util.List;

public interface CommentDao {

	//그냥 댓글을 Comment, 대댓글은 Reply라고 할게요
	List<CommentVo> selectAllCommentnReply();
	CommentVo selectOne(int pk);
	void insertOneComment(CommentVo bean);
	void insertOneReply(CommentVo bean);
	int updateOneCommentnReply(CommentVo bean);
	int deleteOneCommentnReply(int pk);
	//delete도 사실상 update의 기능을 수행
}
