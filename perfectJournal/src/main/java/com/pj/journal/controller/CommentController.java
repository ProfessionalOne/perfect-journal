package com.pj.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pj.journal.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	CommentService commentService;
	
}
