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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
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
}