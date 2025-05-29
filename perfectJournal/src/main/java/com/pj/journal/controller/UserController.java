package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/users/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/users/login")
	public String login(@RequestParam("user") String user,
			@RequestParam("password") String password,
			HttpSession session, Model model) {
		System.out.println(user);
		System.out.println(password);
		UserVo userVo=userService.selectByUser(user, password);
		System.out.println(userVo);
		
		if(userVo == null || !password.equals(userVo.getPassword())) {
//			 model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "user/login";
		}
		session.setAttribute("loginUser", userVo);
		return "redirect:/posts";
	}
	
	@GetMapping("/users/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); 
	    return "redirect:/posts";
	}

	

}
