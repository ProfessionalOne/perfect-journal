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

}
