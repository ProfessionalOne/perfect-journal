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
			@Param("sort") String sort, @Param("searchField") String field, @Param("keyword") String keyword,
			@Param("userId") Integer userId, @Param("onlyMine") Boolean onlyMine);

	int getTotalCount(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("sort") String sort,
			@Param("searchField") String field, @Param("keyword") String keyword, @Param("userId") Integer userId,
			@Param("onlyMine") Boolean onlyMine, @Param("isTimeCapsule") Integer isTimeCapsule);

	List<BoardVo> selectByTime(@Param("userId") int userId, @Param("offset") int offset,
			@Param("pageSize") int pageSize, @Param("sort") String sort, @Param("searchField") String field,
			@Param("keyword") String keyword, @Param("onlyMine") Boolean onlyMine);

	void increaseViews(int postId);

}
