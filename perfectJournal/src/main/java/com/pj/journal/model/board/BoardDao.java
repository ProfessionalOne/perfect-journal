package com.pj.journal.model.board;

import java.util.List;

public interface BoardDao {
	public List<BoardVo> selectAllBoard();

	BoardVo selectOneBoard(int pk);

	int insertOneBoard(BoardVo bean);

	int updatetOneBoard(BoardVo bean);

	int deleteOneBoard(int pk);
}
