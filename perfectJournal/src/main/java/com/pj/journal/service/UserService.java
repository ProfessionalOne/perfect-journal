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

	public String findUserId(UserVo bean, String answer) {

		UserVo vo = userDao.findUserIdEncrypted(bean);
		if (vo != null && BCrypt.checkpw(answer, vo.getAnswer())) {
			System.out.println(BCrypt.checkpw(answer, vo.getAnswer()));
	        return vo.getUser(); // 찾은 id 반환 (UserVo에 getUser()가 아이디 반환)
	    } else {
	        return "notFound";
	    }
	}

	public String findUserPw(UserVo bean, String answer) {
		UserVo vo = userDao.findUserPwEncrypted(bean);
		if (vo != null && BCrypt.checkpw(answer, vo.getAnswer())) {
			return "findPw"; 
	    } else {
	        return "notFound";
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
