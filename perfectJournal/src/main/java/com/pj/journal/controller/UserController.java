package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public String login(@RequestParam("user") String user, @RequestParam("password") String password,
			HttpSession session, Model model) {
		UserVo userVo = userService.selectByUser(user, password);

		if (userVo == null) {
			model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
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

	@GetMapping("/users/find")
	public String findUserInfo() {
		return "user/find";
	}

	@PostMapping("/users/find/id")
	public ResponseEntity<?> findUserId(@RequestBody UserVo bean, @RequestParam("answer") String answer) {
		String userId = userService.findUserId(bean, answer);

		if ("notFound".equals(userId)) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}

		return ResponseEntity.ok(userId);
	}


	@PostMapping("/users/find/pw")
	public ResponseEntity<?> findUserPw(@RequestBody UserVo bean, @RequestParam("answer") String answer) {
		String result = userService.findUserPw(bean, answer);

		if ("notFound".equals(result)) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/users/changePw")
	public String changePwForm() {
		return "user/changePw";
	}

	@PostMapping("/users/changePw")
	public String changePassword(@RequestParam("user") String user, @RequestParam("password") String password,
			Model model) {
		int result = userService.changeUserPw(user, password);

		if (result > 0) {
			return "redirect:/users/login";
		} else {
			return "user/changePw";
		}
	}

	@GetMapping("/users/signup")
	public String register() {
		return "user/register";
	}

	@PostMapping("/users/signup")
	public String register(@ModelAttribute UserVo bean, Model model) {
		if (userService.isIdAlreadyExists(bean.getUser()) > 0) {
			model.addAttribute("signupError", "이미 사용중인 아이디입니다.");
			return "user/register";
		}

		if (userService.isEmailAlreadyExists(bean.getEmail()) > 0) {
			model.addAttribute("signupError", "이미 사용중인 이메일입니다.");
			return "user/register";
		}

		if (userService.isNickNameAlreadyExists(bean.getNickname()) > 0) {
			model.addAttribute("signupError", "이미 사용중인 닉네임입니다.");
			return "user/register";
		}

		userService.insertOneUser(bean);
		model.addAttribute("signupSuccess", "회원가입 성공! 로그인 해주세요.");

		return "user/register";
	}

}
