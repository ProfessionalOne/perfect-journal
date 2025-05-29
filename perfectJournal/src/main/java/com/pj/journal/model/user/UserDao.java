package com.pj.journal.model.user;

import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	// 로그인 내용
	UserVo selectByUser(String user);
//
//	String findMemberId(@Param("email") String email, @Param("answer") String answer, @Param("question") int question);
//
//	MemberVo findMemberPw(@Param("email") String email, @Param("answer") String answer, @Param("question") int question,
//			@Param("user") String user);
	

	int changeUserPw(String user, String password);

	Session logoutOneUser();

	// 회원가입 내용
	void insertOneUser(UserVo bean);

	// 이 밑은 회원정보수정/탈퇴 쪽 내용이라 나중에 구현하고 만들어야 할 것 같아서 일단 만들기만 해놨어요
	int updateOneUser(UserVo bean);

	int deleteOneUser(int pk);


}
