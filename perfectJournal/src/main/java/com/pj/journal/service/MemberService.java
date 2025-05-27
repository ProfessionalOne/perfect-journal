package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pj.journal.model.member.*;

@Service
public class MemberService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	//로그인 기능으로 만들려고 햇는데 생각해보니 int 어떻게 할지 모르겠어서 일단 0으로 해두고 남길게요
	public void getMember(Model model) {
		try(
				SqlSession session=sqlSessionFactory.openSession();
				){
			model.addAttribute("user", session.getMapper(LoginDao.class).selectOneMember(0));
		}
	}
}

