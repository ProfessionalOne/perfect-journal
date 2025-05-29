package com.pj.journal.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.pj.journal.model.member.*;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	HttpSession httpsession;

	}