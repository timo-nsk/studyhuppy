package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.aspects.CheckStapelExists;
import com.studyhub.kartei.adapter.web.CookieHandler;
import com.studyhub.kartei.adapter.web.exception.InvalidSetFragetypRequestException;
import com.studyhub.kartei.adapter.web.form.AnyChoiceKarteikarteForm;
import com.studyhub.kartei.adapter.web.form.NewKarteikarteForm;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.service.application.StapelService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KarteikarteController {

	private final StapelService stapelService;

	public KarteikarteController(StapelService stapelService) {
		this.stapelService = stapelService;
	}

	@GetMapping("/lernen/{karteiSetId}/new-karte")
	@CheckStapelExists
	public String neueKarteErstellen(@PathVariable("karteiSetId") String karteiSetId,
	                                 Model model,
	                                 HttpServletResponse resp,
	                                 HttpServletRequest req,
	                                 NewKarteikarteForm newKarteikarteForm,
	                                 AnyChoiceKarteikarteForm anyChoiceKarteikarteForm) {

		if(!CookieHandler.frageTypCookieExists(req)) {
			Cookie newCookie = CookieHandler.createFrageTypCookie(FrageTyp.NORMAL);
			resp.addCookie(newCookie);
			model.addAttribute("fragetypen", FrageTyp.allTypes());
			model.addAttribute("fragetypChoice", newCookie.getValue());
		} else {
			model.addAttribute("fragetypen", FrageTyp.allTypes());
			String frageTyp = CookieHandler.getFrageTyp(req);
			model.addAttribute("fragetypChoice", frageTyp);
			//model.addAttribute("anzahlAntworten", CookieHandler.getAnzahlAntworten(req));
		}
		model.addAttribute("karteiSetId", karteiSetId);
		model.addAttribute("stapelName", stapelService.findByFachId(karteiSetId).getName());

		if(!CookieHandler.anzahlAntwortenCookieExists(req)) {
			Cookie newCookie = CookieHandler.createAnzahlAntwortenCookie(2);
			resp.addCookie(newCookie);
			model.addAttribute("anzahlAntworten", Integer.parseInt(newCookie.getValue()));
		} else {
			model.addAttribute("anzahlAntworten", CookieHandler.getAnzahlAntworten(req));
		}

		return "/new-karte/new-karte";
	}

	@PostMapping("/set-fragetyp")
	public String setFragetyp(@RequestBody SetFragetypRequest request, HttpServletResponse resp) {
		if (!request.isValidRequest()) throw new InvalidSetFragetypRequestException(
					"Parameter '%s' oder '%s' sind nicht gültige Werte.".formatted(request.getFrageTyp(), request.getKarteiSetId()));

		CookieHandler.updateFrageTypCookie(request, resp);
		return "redirect:/lernen/%s/new-karte".formatted(request.getKarteiSetId());
	}


	@PostMapping("/create-karteikarte-normal/{karteiSetId}")
	@CheckStapelExists
	public String createKarteikarte(@PathVariable String karteiSetId,
	                                @Valid NewKarteikarteForm newKarteikarteForm,
	                                BindingResult bindingResult,
	                                RedirectAttributes redirectAttributes,
	                                Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("fragetypChoice", newKarteikarteForm.frageTyp()); // DAS MUSS BLEIBEN
			model.addAttribute("fragetypen", FrageTyp.allTypes()); // DAS MUSS BLEIBEN
			return "/new-karte/new-karte";
		}

		redirectAttributes.addFlashAttribute("newKarteikarteForm", newKarteikarteForm); //wenn oben return, dann bleiben trotzdem die inhalte der felder die nciht falsch waren gesetzt

		stapelService.updateSetWithNewKarteikarte(karteiSetId, newKarteikarteForm.toKarteikarte());

		return "redirect:/lernen/%s/new-karte".formatted(karteiSetId);
	}

	@PostMapping("/create-choice-karteikarte/{karteiSetId}")
	@CheckStapelExists
	public String createSingleChoiceKarteikarte(
			@PathVariable("karteiSetId") String karteiSetId,
			@Valid AnyChoiceKarteikarteForm anyChoiceKarteikarteForm,
	        BindingResult result,
	        RedirectAttributes redirectAttributes,
	        Model model)
	{
		System.out.println(anyChoiceKarteikarteForm);
		if (result.hasErrors()) {
			model.addAttribute("fragetypChoice", anyChoiceKarteikarteForm.frageTyp()); // DAS MUSS BLEIBEN
			model.addAttribute("fragetypen", FrageTyp.allTypes());
			model.addAttribute("anzahlAntworten", anyChoiceKarteikarteForm.anzahlAntworten());// DAS MUSS BLEIBEN
			return "new-karte";
		}

		redirectAttributes.addFlashAttribute("anyChoiceKarteikarteForm", anyChoiceKarteikarteForm);

		stapelService.updateSetWithNewKarteikarte(karteiSetId, anyChoiceKarteikarteForm.toKarteikarte());

		return "redirect:/lernen/%s/new-karte".formatted(karteiSetId);
	}

	@PostMapping("/set-anzahlAntworten")
	public String setAnzahlAntworten(SetAnzahlAntwortenRequest request, HttpServletResponse resp) {
		resp.addCookie(CookieHandler.createAnzahlAntwortenCookie(request.anzahlAntworten()));
		return "redirect:/lernen/%s/new-karte".formatted(request.karteiSetId());
	}

	@PostMapping("/add-neue-karte")
	public ResponseEntity<Void> addNewKarteikarte(@RequestBody NewNormalKarteikarteRequest nkRequest,
												  HttpServletRequest req) {
		return ResponseEntity.ok().build();
	}
}

