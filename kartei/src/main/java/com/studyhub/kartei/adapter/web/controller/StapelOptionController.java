package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.aspects.CheckStapelExists;
import com.studyhub.kartei.service.application.StapelOptionService;
import com.studyhub.kartei.service.application.StapelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/set-bearbeiten")
public class StapelOptionController {

	private final StapelService setService;
	private final StapelOptionService optionService;

	public StapelOptionController(StapelService setService, StapelOptionService optionService) {
		this.setService = setService;
		this.optionService = optionService;
	}

	@GetMapping("/{karteiSetId}")
	@CheckStapelExists
	public String setBearbeiten(@PathVariable("karteiSetId") String karteiSetId,
	                            Model model) {
		model.addAttribute("karteikarten", setService.findByFachId(karteiSetId).getKarteikarten());
		return "set-bearbeiten";
	}

	@GetMapping("/delete-set")
	public String deleteSet(String karteiSetId) {
		optionService.deleteKarteiSet(karteiSetId);
		return "redirect:/kartei-uebersicht";
	}

	@PostMapping("/change-set-name")
	public String changeSetName(String karteiSetId, String newSetName) {
		optionService.changeSetName(karteiSetId, newSetName);
		return "redirect:/set-bearbeiten/%s".formatted(karteiSetId);
	}

	@GetMapping("/reset-karteikarten")
	public String resetKarteikarten(String karteiSetId) {
		optionService.resetAllKarteikarten(karteiSetId);
		return "redirect:/set-bearbeiten/%s".formatted(karteiSetId);
	}

	@GetMapping("/delete-karteikarten")
	public String deleteKarteikarten(String karteiSetId) {
		optionService.deleteKarteikartenOfSet(karteiSetId);
		return "redirect:/set-bearbeiten/%s".formatted(karteiSetId);
	}
}
