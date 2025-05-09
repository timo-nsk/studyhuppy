package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.config.AdminOnly;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@AdminOnly
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
}
