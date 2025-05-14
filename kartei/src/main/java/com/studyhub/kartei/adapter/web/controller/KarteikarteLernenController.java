package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.aspects.CheckStapelExists;
import com.studyhub.kartei.adapter.web.exception.StapelDoesNotExistException;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.*;
import com.studyhub.kartei.service.application.UpdateInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class KarteikarteLernenController {

	private final StapelService stapelService;
	private final KarteikarteService karteikarteService;

	public KarteikarteLernenController(StapelService stapelService, KarteikarteService karteikarteService) {
		this.stapelService = stapelService;
		this.karteikarteService = karteikarteService;
	}

	@ModelAttribute("fragetypChoice")
	public String getDefaultFragetyp() {
		return FrageTyp.NORMAL.toString();
	}

	@GetMapping("/lernen/{karteiSetId}/übersicht")
	@CheckStapelExists
	public String setÜbersicht(@PathVariable("karteiSetId") String karteiSetId,
	                           Model model) {
		if (stapelService.stapelNotExists(karteiSetId))
			throw new StapelDoesNotExistException("Set with fachId: '%s' does not exist.".formatted(karteiSetId));

		Stapel stapel = stapelService.findByFachId(karteiSetId);

		model.addAttribute("karteikartenAvailable", stapel.karteikartenAvailable());
		model.addAttribute("setFachId", stapel.getFachId().toString());
		model.addAttribute("setName", stapel.getName());

		return "set-übersicht";
	}

	@PostMapping("/update-karteikarte")
	public ResponseEntity<Void> updateKarteikarte(@RequestBody UpdateInfo updateInfo) {
		boolean success = karteikarteService.updateKarteikarteForNextReview(updateInfo);
		if (!success) throw new KarteikarteUpdateException("Karteikarte could not be updated.");

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/lernen/{karteiSetId}/ende")
	public String endLearning(@PathVariable("karteiSetId") String karteiSetId) {
		return "redirect:/kartei-uebersicht";
	}

	public void endLernSession(HttpSession session, HttpServletResponse resp) {
		session.removeAttribute("currentSet");
		Cookie cookie = new Cookie("sessionKarteIndex", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}
}