package com.pj.journal.model.user;

import org.apache.catalina.Session;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	// 로그인 내용
	UserVo selectByUser(@Param("user") String user, @Param("password") String password);
//
//	String findMemberId(@Param("email") String email, @Param("answer") String answer, @Param("question") int question);
//
//	MemberVo findMemberPw(@Param("email") String email, @Param("answer") String answer, @Param("question") int question,
//			@Param("user") String user);
	

	String changeMemberPw();

	Session logoutOneMember();

	// 회원가입 내용
	void insertOneMember(UserVo bean);

	// 이 밑은 회원정보수정/탈퇴 쪽 내용이라 나중에 구현하고 만들어야 할 것 같아서 일단 만들기만 해놨어요
	int updateOneMember(UserVo bean);

	int deleteOneMember(int pk);


}
