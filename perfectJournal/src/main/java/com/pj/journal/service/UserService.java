package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.journal.model.user.UserDao;
import com.pj.journal.model.user.UserVo;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	HttpSession httpsession;

	public String findUserId(UserVo bean) {
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			String findId = session.getMapper(UserDao.class).findUserId(bean);
			
			return findId;
		}
	}

	public String findUserPw(UserVo bean) {
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			String findPw = session.getMapper(UserDao.class).findUserPw(bean);
			
			return findPw;
		}
	}
}