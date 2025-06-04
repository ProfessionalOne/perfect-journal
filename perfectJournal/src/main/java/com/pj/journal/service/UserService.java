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

	public String findUserId(String email, String answer, int question) {

		UserVo vo = userDao.findUserIdEncrypted(email, question);
		if (vo != null && BCrypt.checkpw(answer, vo.getAnswer())) {
			
	        return vo.getUser(); // 찾은 id 반환 (UserVo에 getUser()가 아이디 반환)
	    } else {
	        return "notFound";
	    }
	}

	public int findUserPw(String user, String email, String answer, int question) {
		
		UserVo vo = userDao.findUserPwEncrypted(email, user, question);

		if (vo != null && BCrypt.checkpw(answer, vo.getAnswer())) {
			return vo.getUserId(); 
	    } else {
	        return -1;
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
			String hashedAnswer = BCrypt.hashpw(bean.getAnswer(),BCrypt.gensalt());
			bean.setAnswer(hashedAnswer);
			session.getMapper(UserDao.class).insertOneUser(bean);
			session.commit();
		}
	}


}
