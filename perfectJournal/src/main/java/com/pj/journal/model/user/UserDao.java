package com.pj.journal.model.user;

import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Mapper;
//import제대로 했는지 확신이 안서서 나중에 한 번 더 체크 부탁드려요'-`
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	// 로그인 내용
	UserVo loginOneUser(@Param("user") String user, @Param("password") String password);

	String findUserId(@Param("email") String email, @Param("answer") String answer, @Param("question") int question);

	UserVo findUserPw(@Param("email") String email, @Param("answer") String answer, @Param("question") int question,
			@Param("user") String user);

	String changeUserPw();

	Session logoutOneUser();

	// 회원가입 내용
	void insertOneUser(UserVo bean);
	
	int isIdAlreadyExists(@Param("user") String user);
	
	int isNickNameAlreadyExists(@Param("nickname") String nickname);

	int isEmailAlreadyExists(@Param("email") String email);
	
	// 이 밑은 회원정보수정/탈퇴 쪽 내용이라 나중에 구현하고 만들어야 할 것 같아서 일단 만들기만 해놨어요
	int updateOneUser(UserVo bean);

	int deleteOneUser(int pk);
}
