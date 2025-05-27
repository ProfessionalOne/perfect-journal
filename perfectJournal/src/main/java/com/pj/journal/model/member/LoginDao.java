package com.pj.journal.model.member;

public interface LoginDao {

	LoginVo selectOneMember(int pk);

	void insertOneMember(LoginVo bean);

	int updateOneMember(LoginVo bean);

	int deleteOneMember(int pk);

}
