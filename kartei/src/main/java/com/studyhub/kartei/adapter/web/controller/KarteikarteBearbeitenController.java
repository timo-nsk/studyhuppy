package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.aspects.CheckStapelExists;
import com.studyhub.kartei.adapter.web.exception.KarteikarteEditierenFehlerException;
import com.studyhub.kartei.adapter.web.form.NewKarteikarteForm;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.studyhub.kartei.adapter.web.form.NewKarteikarteForm.toForm;

@Controller
public class KarteikarteBearbeitenController {

	private final StapelService stapelService;
	private final KarteikarteService karteikarteService;

	public KarteikarteBearbeitenController(StapelService stapelService, KarteikarteService karteikarteService) {
		this.stapelService = stapelService;
		this.karteikarteService = karteikarteService;
	}

	@GetMapping("/karte-bearbeiten/{karteiSetId}/{karteFachId}")
	@CheckStapelExists
	//@CheckKarteikarteExists
	public String index(@PathVariable String karteiSetId,
	                    @PathVariable String karteFachId,
	                    Model model) {
		Karteikarte karte = stapelService.findKarteikarteByFachId(karteiSetId, karteFachId);
		NewKarteikarteForm newKarteikarteForm = toForm(karte);
		model.addAttribute("newKarteikarteForm", newKarteikarteForm);
		model.addAttribute("fragetypChoice", newKarteikarteForm.frageTyp());
		model.addAttribute("karteiSetId", karteiSetId);
		model.addAttribute("karteFachId", karteFachId);
		return "karte-bearbeiten";
	}

	@PostMapping("/bearbeite-karte/{karteiSetId}/{karteFachId}")
	@CheckStapelExists
	//@CheckKarteikarteExists
	public String bearbeiteKarte(@PathVariable String karteiSetId,
	                             @PathVariable String karteFachId,
	                             NewKarteikarteForm newKarteikarteForm) {

		boolean erfolg = karteikarteService.editNormalKarteikarte(karteiSetId, karteFachId, newKarteikarteForm.toKarteikarte());

		if (!erfolg) throw new KarteikarteEditierenFehlerException("Not able to edit karteikarte '%s'".formatted(karteFachId));

		return "redirect:/set-bearbeiten/%s".formatted(karteiSetId);
	}
}
