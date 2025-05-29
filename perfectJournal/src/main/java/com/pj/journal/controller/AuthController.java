package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;           // <-- 추가
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pj.journal.model.user.UserDao;
import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.UserService;

@Controller
public class AuthController {

    @Autowired
    UserService userService;
    UserDao userDao;

    @PostMapping("/users/signup")
    public String register(@ModelAttribute UserVo bean, Model model) {
        boolean valid = userService.validateSignup(bean);

        if (!valid) {
            model.addAttribute("signupError", "입력값을 확인하세요.");
            return "signup"; // 회원가입 페이지로 다시 이동
        }
        if (userService.isIdAlreadyExists(bean.getUser()) > 0) {
            model.addAttribute("signupError", "이미 사용중인 아이디입니다.");
            return "signup";
        }
        if (userService.isEmailAlreadyExists(bean.getEmail()) > 0) {
            model.addAttribute("signupError", "이미 사용중인 이메일입니다.");
            return "signup";
        }
        if (userService.isNickNameAlreadyExists(bean.getNickname()) > 0) {
            model.addAttribute("signupError", "이미 사용중인 닉네임입니다.");
            return "signup";
        }
        userService.insertOneUser(bean);
        // 회원가입 성공 시 로그인 페이지로 리다이렉트
        model.addAttribute("signupSuccess", "회원가입 성공! 로그인 해주세요.");
        return "signup";
    }


    @GetMapping("/check-userId")
    @ResponseBody
    public String checkUser(@RequestParam String userid) {
        return userDao.isIdAlreadyExists(userid) > 0 ? "exists" : "available";
    }

    @GetMapping("/check-nickname")
    @ResponseBody
    public String checkNickname(@RequestParam String nickname) {
        return userDao.isNickNameAlreadyExists(nickname) > 0 ? "exists" : "available";
    }

	@GetMapping("/check-email")
	@ResponseBody
	public String checkEmail(@RequestParam String email) {
		return userDao.isEmailAlreadyExists(email) > 0 ? "exists" : "available";
}
}

