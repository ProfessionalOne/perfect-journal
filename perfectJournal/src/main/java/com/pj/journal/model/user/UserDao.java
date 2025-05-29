package com.pj.journal.model.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	// 로그인 내용
	UserVo selectByUser(@Param("user") String user, @Param("password") String password);

  String findUserId(UserVo bean);

	String findUserPw(UserVo bean);

	int changeUserPw(String user, String password);


}
