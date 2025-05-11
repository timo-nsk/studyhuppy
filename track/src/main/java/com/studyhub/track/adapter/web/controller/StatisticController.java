package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.application.service.ModulService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticController {
	private final ModulService modulService;

	public StatisticController(ModulService modulService) {
		this.modulService = modulService;
	}

	@GetMapping("/overview")
	public String statistics(Model model) {
		//model.addAttribute("totalStudyTime", modulService.getTotalStudyTime());
		//model.addAttribute("NumberActiveModules", modulService.countActiveModules());
		//model.addAttribute("NumberNotActiveModules", modulService.countNotActiveModules());
		//model.addAttribute("MaxStudiedModul", modulService.findModulWithMaxSeconds());
		//model.addAttribute("MinStudiedModul", modulService.findModulWithMinSeconds());
		//TODO implement both functions
		//model.addAttribute("AverageStudytimePerDay", modulService.averageStudytimePerDay());
		//model.addAttribute("AverageStudytimePerWeek", modulService.averageStudytimePerWeek());
		return "overview";
	}

	@GetMapping("/overall")
	public String overall() {
		return "overall";
	}

	@GetMapping("/each-modul")
	public String eachModule() {
		return "each-module";
	}
}
