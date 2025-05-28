package com.pj.journal.model.comment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentDao {

	// 그냥 댓글을 Comment, 대댓글은 Reply라고 할게요
	List<CommentVo> selectAllCommentnReply(@Param("postId") int postId);

	CommentVo selectOne(int pk);
	//사용 안할거 같아서 구현은 안했지만 일단 몰라서 써놓기는 했어요
	void insertOneComment(CommentVo bean);

	void insertOneReply(CommentVo bean);

	int updateOneCommentnReply(CommentVo bean);

	int deleteOneCommentnReply(CommentVo bean);
	// delete도 사실상 update의 기능을 수행
}
