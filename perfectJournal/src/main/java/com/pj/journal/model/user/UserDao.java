package com.pj.journal.model.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

	UserVo selectByUser(@Param("user") String user);

	String findUserId(UserVo bean);

	String findUserPw(UserVo bean);

	UserVo findUserIdEncrypted(@Param("email") String email, @Param("question") int question);

	UserVo findUserPwEncrypted(@Param("email") String email, @Param("user") String user,
			@Param("question") int question);

	int changeUserPw(String user, String password);

	void insertOneUser(UserVo bean);

	int isIdAlreadyExists(@Param("user") String user);

	int isNickNameAlreadyExists(@Param("nickname") String nickname);

	int isEmailAlreadyExists(@Param("email") String email);

}
