package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.kartei.adapter.modul.ModulRequestService;
import com.studyhub.kartei.adapter.web.form.StapelForm;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StapelController {

	private final StapelService stapelService;
	private final ModulRequestService modulRequestService;
	private final LernzeitService lernzeitService;
	private final JWTService jwtService;

	public StapelController(StapelService stapelService, ModulRequestService modulRequestService, LernzeitService lernzeitService, JWTService jwtService) {
		this.stapelService = stapelService;
		this.modulRequestService = modulRequestService;
		this.lernzeitService = lernzeitService;
		this.jwtService = jwtService;
	}

	@GetMapping("/kartei-uebersicht")
	public String home(Model model,
	                   HttpServletResponse resp,
	                   @CookieValue("auth_token") String token) {
		Cookie c = new Cookie("sessionKarteIndex", "0");
		c.setPath("/");
		resp.addCookie(c);

		model.addAttribute("today", lernzeitService.dateOfToday());
		model.addAttribute("lernzeitService", lernzeitService);
		model.addAttribute("karteiSetsAvailable", stapelService.areKarteiSetsAvailable());
		model.addAttribute("karteisetList", stapelService.findByUsername(jwtService.extractUsername(token)));
		model.addAttribute("modulService", modulRequestService);
		return "kartei-dashboard";
	}

	@GetMapping("/new-set")
	public String newSet(StapelForm stapelForm,
	                     Model model) {
		model.addAttribute("modulMap", modulRequestService.getModulApiMap());
		return "new-set";
	}

	@PostMapping("/new-set")
	public ModelAndView newSetErstellen(@Valid @ModelAttribute StapelForm stapelForm,
	                                    BindingResult result,
	                                    RedirectAttributes redirectAttributes,
	                                    @CookieValue(name = "auth_token") String token) {
		if (result.hasErrors()) return new ModelAndView("new-set", HttpStatus.NOT_ACCEPTABLE);

		redirectAttributes.addFlashAttribute("stapelForm", stapelForm);

		String username = jwtService.extractUsername(token);
		stapelService.saveSet(stapelForm.toStapel(username));

		return new ModelAndView("redirect:/kartei-uebersicht");
	}
}
