package com.pj.journal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<?> findUserId(@RequestBody Map<String, Object> param) {
		String email = (String) param.get("email");
		String answer = (String) param.get("answer");
		int question;
		// question 값이 문자열로 올 수도 있으니 예외처리
		try {
			question = Integer.parseInt(param.get("question").toString());
		} catch (Exception e) {
			question = 0; // 혹은 기본값, 예외처리 등
		}
		String userId = userService.findUserId(email, answer, question);

		if ("notFound".equals(userId)) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}

		return ResponseEntity.ok(userId);
	}

	@PostMapping("/users/find/pw")
	public ResponseEntity<?> findUserPw(@RequestBody Map<String, Object> param, HttpSession session) {
		String user = (String) param.get("user");
		String email = (String) param.get("email");
		String answer = (String) param.get("answer");

		int question;
		try {
			question = Integer.parseInt(param.get("question").toString());
		} catch (Exception e) {
			question = 0;
		}

		int foundUser = userService.findUserPw(user, email, answer, question);

		if (foundUser == -1) {
			return ResponseEntity.notFound().build();
		}

		session.setAttribute("user", user);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users/changePw")
	public String changePwForm(HttpSession session, Model model) {
		String user = (String) session.getAttribute("user");

		if (user != null && !user.isEmpty()) {
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "비밀번호를 재설정할 사용자 정보가 확인되지 않습니다. 아이디 찾기/비밀번호 찾기 기능을 이용해주세요.");
			return "user/find";
		}

		return "user/changePw";
	}

	@PostMapping("/users/changePw")
	public ResponseEntity<String> changePassword(@RequestParam("user") String user,
			@RequestParam("password") String password, HttpSession session, Model model) {
		if (!password
				.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		int result = userService.changeUserPw(user, password);

		if (result > 0) {
			session.removeAttribute("user");
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/users/signup")
	public String register() {
		return "user/register";
	}

	@PostMapping("/users/signup")
	public String register(@ModelAttribute UserVo bean, Model model) {
		if (userService.isIdAlreadyExists(bean.getUser()) > 0) {
			bean.setUser(null);
			bean.setPassword(null);
			model.addAttribute("signupError", "이미 사용중인 아이디입니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (!bean.getUser().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$")) {
			bean.setUser(null);
			bean.setPassword(null);
			model.addAttribute("signupError", "아이디는 영문자와 숫자를 모두 포함해야 하며, 8~15자여야 합니다. 특수문자는 사용할 수 없습니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (!bean.getPassword()
				.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")) {
			model.addAttribute("signupError", "비밀번호는 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
			return "user/register";
		}

		if (userService.isEmailAlreadyExists(bean.getEmail()) > 0) {
			model.addAttribute("signupError", "이미 사용중인 이메일입니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (!bean.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			model.addAttribute("signupError", "이메일 형식이 올바르지 않습니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (userService.isNickNameAlreadyExists(bean.getNickname()) > 0) {
			model.addAttribute("signupError", "이미 사용중인 닉네임입니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (!bean.getNickname().matches("^[가-힣a-zA-Z0-9]{2,12}$")) {
			model.addAttribute("signupError", "닉네임은 한글, 영문, 숫자만 사용 가능하며 2~12자 사이여야 합니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		if (!bean.getAnswer().matches("^[가-힣a-zA-Z0-9]{1,10}$")) {
			model.addAttribute("signupError", "답변은 1~10자 이내의 한글, 영문, 숫자만 입력 가능합니다.");
			model.addAttribute("user", bean);
			return "user/register";
		}

		userService.insertOneUser(bean);
		model.addAttribute("signupSuccess", "회원가입 성공! 로그인 해주세요.");

		return "user/register";
	}

}
