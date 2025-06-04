package com.pj.journal.model.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	// 로그인 내용
	UserVo selectByUser(@Param("user") String user);

	String findUserId(UserVo bean);

	String findUserPw(UserVo bean);
	
	//'답변'에도 암호화 적용
	UserVo findUserIdEncrypted(UserVo bean);

	UserVo findUserPwEncrypted(UserVo bean);

	int changeUserPw(String user, String password);

	// 회원가입 내용
	void insertOneUser(UserVo bean);

	int isIdAlreadyExists(@Param("user") String user);

	int isNickNameAlreadyExists(@Param("nickname") String nickname);

	int isEmailAlreadyExists(@Param("email") String email);

	// 이 밑은 회원정보 수정/탈퇴 쪽 내용이라 나중에 구현하고 만들어야 할 것 같아서 일단 만들기만 해놨어요
	int updateOneUser(UserVo bean);

	int deleteOneUser(int pk);
}
