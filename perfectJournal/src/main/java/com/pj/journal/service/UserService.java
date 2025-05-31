package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.journal.model.user.UserDao;
import com.pj.journal.model.user.UserVo;

@Service
public class UserService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	UserDao userDao;

	public UserVo selectByUser(String user, String password) {
		
		UserVo bean = userDao.selectByUser(user);
		
		if (bean != null && BCrypt.checkpw(password, bean.getPassword())) {
			return bean;
		}
		
		return null;
	}

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

	public int changeUserPw(String user, String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		return userDao.changeUserPw(user, hashedPassword);
	}

	public int isIdAlreadyExists(String user) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isIdAlreadyExists(user);
		}
	}

	public int isEmailAlreadyExists(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isEmailAlreadyExists(email);
		}
	}

	public int isNickNameAlreadyExists(String nickname) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isNickNameAlreadyExists(nickname);
		}
	}

	public void insertOneUser(UserVo bean) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			String hashedPassword = BCrypt.hashpw(bean.getPassword(), BCrypt.gensalt());

			bean.setPassword(hashedPassword);

			session.getMapper(UserDao.class).insertOneUser(bean);
			session.commit();
		}
	}

}
