package com.pj.journal.model.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

	String findUserId(UserVo bean);

	String findUserPw(UserVo bean);

}
