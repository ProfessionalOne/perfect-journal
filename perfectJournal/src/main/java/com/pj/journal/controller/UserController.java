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
		UserVo userVo=userService.selectByUser(user);
		
		if(userVo == null || !password.equals(userVo.getPassword())) {
//			 model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "user/login";
		}
		session.setAttribute("loginUser", userVo);
		return "redirect:/posts";
	}
	
	@GetMapping("/users/changePw")
	public String changePwForm() {
	    return "user/changePw"; // templates/user/changePw.html
	}

	@PostMapping("/users/changePw")
	public String changePassword(@RequestParam("user") String user,
	                             @RequestParam("password") String password,
	                             Model model) {
	    int result = userService.changeUserPw(user, password);

	    if (result > 0) {
	        return "redirect:/users/login";  // 성공 시 로그인 페이지로 리디렉션
	    } else {
	        return "user/changePw";          // 실패 시 비밀번호 변경 페이지로 다시
	    }
	}
	
}
