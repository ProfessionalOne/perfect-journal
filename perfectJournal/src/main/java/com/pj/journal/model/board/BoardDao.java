package com.pj.journal.model.board;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BoardDao {
	public List<BoardVo> selectAllBoard();

	BoardVo selectOneBoard(int pk);

	int insertOneBoard(BoardVo bean);

	int updatetOneBoard(BoardVo bean);

	int deleteOneBoard(int pk);

	List<BoardVo> selectByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

	int getTotalCount();
}
