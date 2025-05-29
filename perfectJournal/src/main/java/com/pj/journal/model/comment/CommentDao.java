package com.pj.journal.model.comment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentDao {

	List<CommentVo> selectAllComments(int postId);
	
    void insertComment(CommentVo comment);

    void updateGroupId(@Param("commentId") int commentId, @Param("groupId") int groupId);

	void updateComment(CommentVo bean);

	void updateCommentIsDeleted(int commentId);

//	CommentVo selectOne(int pk);
//	//사용 안할거 같아서 구현은 안했지만 일단 몰라서 써놓기는 했어요
//	void insertOneComment(CommentVo bean);
//
//	void insertOneReply(CommentVo bean);
//
//	int updateOneCommentnReply(CommentVo bean);
//
//	int deleteOneCommentnReply(CommentVo bean);
	// delete도 사실상 update의 기능을 수행
}
