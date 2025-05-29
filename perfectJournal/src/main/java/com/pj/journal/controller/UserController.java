package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pj.journal.model.user.UserVo;
import com.pj.journal.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/users/find")
	public String findUserInfo() {
		return "user/find";
	}

	@PostMapping("/users/find/id")
	public ResponseEntity<?> findUserId(@RequestBody UserVo bean) {
		String userId = userService.findUserId(bean);

		if (userId == null) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}

		return ResponseEntity.ok(userId);
	}

	@PostMapping("/users/find/pw")
	public ResponseEntity<?> findUserPw(@RequestBody UserVo bean) {
		String userNum = userService.findUserPw(bean);

		System.out.println(userNum);

		if (userNum == null) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}

		return ResponseEntity.ok(userNum);
	}

}
