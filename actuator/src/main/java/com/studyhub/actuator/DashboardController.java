package com.studyhub.actuator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	private final SystemHealthService systemHealthService;

	public DashboardController(SystemHealthService systemHealthService) {
		this.systemHealthService = systemHealthService;
	}

	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		model.addAttribute("systemHealthList", systemHealthService.getSystemHealth());
		return "dashboard";
	}
}
