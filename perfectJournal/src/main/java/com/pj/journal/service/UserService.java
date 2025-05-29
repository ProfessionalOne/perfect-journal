package com.pj.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.journal.model.user.UserDao;
import com.pj.journal.model.user.UserVo;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public UserVo selectByUser(String user) {
		return userDao.selectByUser(user);
	}
	
	public int changeUserPw(String user, String password) {
        return userDao.changeUserPw(user, password);
    }

}