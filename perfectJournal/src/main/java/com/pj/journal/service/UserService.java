package com.pj.journal.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pj.journal.model.user.*;

@Service
public class UserService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	public int isIdAlreadyExists(String user){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isIdAlreadyExists(user);
		}
	}
	public int isEmailAlreadyExists(String email){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isEmailAlreadyExists(email);
		}
	}
	public int isNickNameAlreadyExists(String nickname){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.getMapper(UserDao.class).isNickNameAlreadyExists(nickname);
		}
	}
	public boolean validateSignup(UserVo vo) {
        // 아이디: 영문, 숫자 4~12자
        if (vo.getUser() == null || !vo.getUser().matches("^[a-zA-Z0-9]{4,12}$")) {
            return false;
        }
        // 비밀번호: 8자 이상, 영문/숫자/특수문자 포함
        if (vo.getPassword() == null || !vo.getPassword().matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$")) {
            return false;
        }
        // 닉네임: 한글, 영문, 숫자 2~10자
        if (vo.getNickname() == null || !vo.getNickname().matches("^[a-zA-Z0-9가-힣]{2,10}$")) {
           return false;
        }
        return true;
	}
	public void insertOneUser(UserVo vo) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.getMapper(UserDao.class).insertOneUser(vo);
			session.commit();
		}
	}
		
}




	