package com.pj.journal.model.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardDao {

	public List<BoardVo> selectAllBoard();

	BoardVo selectOneBoard(int pk);

	int insertOneBoard(BoardVo bean);

	int updatetOneBoard(BoardVo bean);

	int deleteOneBoard(int pk);

	List<BoardVo> selectBySearch(@Param("offset") int offset, @Param("pageSize") int pageSize,
			@Param("sort") String sort, @Param("searchField") String field, @Param("keyword") String keyword);

	int getTotalCount(@Param("searchField") String searchField, @Param("keyword") String keyword);
}
