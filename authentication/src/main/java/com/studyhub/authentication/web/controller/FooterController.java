package com.studyhub.authentication.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

	@GetMapping("/impressum")
	public String impressum() {
		return "impressum";
	}

	@GetMapping("/datenschutz")
	public String datenschutz() {
		return "datenschutz";
	}

	@GetMapping("/kontakt")
	public String kontakt() {
		return "kontakt";
	}
}
