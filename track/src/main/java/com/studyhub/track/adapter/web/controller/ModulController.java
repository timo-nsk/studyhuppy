package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.authentication.AuthenticationService;
import com.studyhub.track.adapter.kartei.CreateNewStapelRequest;
import com.studyhub.track.adapter.kartei.StapelRequestService;
import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.KlausurDateRequest;
import com.studyhub.track.adapter.web.ModulForm;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modul;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ModulController {

	private final ModulService modulService;
	private final StapelRequestService stapelRequestService;
	private final AuthenticationService authenticationService;
	private final JWTService jwtService;

	public ModulController(ModulService modulService, StapelRequestService stapelRequestService, AuthenticationService authenticationService, JWTService jwtService) {
		this.modulService = modulService;
		this.stapelRequestService = stapelRequestService;
		this.authenticationService = authenticationService;
		this.jwtService = jwtService;
	}

	@GetMapping("/dashboard")
	public String index(Model model,
	                    @CookieValue("auth_token") String token) {
		model.addAttribute("activeModulList", modulService.findActiveModuleByUsername(true, token));
		model.addAttribute("deactivatedModulList", modulService.findActiveModuleByUsername(false, token));
		return "home";
	}

	@GetMapping("/modul-details/{fachId}")
	public String modulDetails(@PathVariable String fachId,
	                           Model model) {
		Modul modul = modulService.findByFachId(UUID.fromString(fachId));
		model.addAttribute("klausurDate", modulService.findKlausurDateByFachId(UUID.fromString(fachId)));
		model.addAttribute("daysLeft", modul.getKlausurDate() == null ? 0 : modulService.daysLeftToKlausur(UUID.fromString(fachId)));
		model.addAttribute("kreditpunkte", modul.getKreditpunkte().getAnzahlPunkte());
		model.addAttribute("kontaktzeitStunden", modul.getKreditpunkte().getKontaktzeitStunden());
		model.addAttribute("selbststudiumStunden", modul.getKreditpunkte().getSelbststudiumStunden());
		model.addAttribute("modulName", modul.getName());
		model.addAttribute("active", modul.isActive() ? "ja" : "nein");
		model.addAttribute("timeLearned", modul.getTimeLearned());

		return "modul-details";
	}

	@GetMapping("/bearbeiten")
	public String bearbeiten(Model model) {
		model.addAttribute("modulList", modulService.findAll());
		return "configure";
	}

	@GetMapping("/new-module")
	public String addModule(ModulForm modulForm) {
		return "add-module";
	}

	@PostMapping("/new-modul")
	public String newModule(@Valid @ModelAttribute ModulForm modulForm,
	                        BindingResult bindingResult,
	                        RedirectAttributes redirectAttributes,
	                        @CookieValue("auth_token") String token) {
		if(bindingResult.hasErrors()) return "add-module";

		redirectAttributes.addFlashAttribute("modulForm", modulForm);
		int semester = authenticationService.getSemesterOfUser(jwtService.extractUsername(token));
		Modul modul = modulForm.newModulFromFormData(modulForm, semester, token);

		if (Optional.ofNullable(modulForm.stapelCheckbox()).orElse(false)) {
			CreateNewStapelRequest request = new CreateNewStapelRequest(modul.getFachId().toString(), modulForm.stapelName(), modulForm.beschreibung(), modulForm.lernstufen(), jwtService.extractUsername(token));
			request.validateForBindingResult(bindingResult);
			if(bindingResult.hasErrors()) return "add-module";
			stapelRequestService.sendCreateNewStapelRequest(request);
		}

		modulService.saveNewModul(modul);
		return "redirect:/dashboard";
	}

	@PostMapping("/reset")
	public String reset(String fachId) {
		modulService.resetModulTime(UUID.fromString(fachId));
		//String modulName = modulService.findModulNameByFachid(UUID.fromString(fachId));
		return "redirect:/dashboard";
	}

	@PostMapping("/delete")
	public String deleteModul(String fachId) {
		modulService.deleteModul(UUID.fromString(fachId));
		return "redirect:/dashboard";
	}

	@PostMapping("/add-time")
	public String addTime(String fachId, String time) {
		modulService.addTime(UUID.fromString(fachId), time);
		return "redirect:/dashboard";
	}

	@PostMapping("/add-klausur-date")
	public String addKlausurDate(KlausurDateRequest req) {
		modulService.addKlausurDate(UUID.fromString(req.fachId()), req.date(), req.time());
		return "redirect:/dashboard";
	}

	@PostMapping("/activate")
	public String activate(String fachId) {
		modulService.activateModul(UUID.fromString(fachId));
		return "redirect:/dashboard";
	}

	@PostMapping("/deactivate")
	public String deactivate(String fachId) {
		modulService.deactivateModul(UUID.fromString(fachId));
		return "redirect:/dashboard";
	}
}