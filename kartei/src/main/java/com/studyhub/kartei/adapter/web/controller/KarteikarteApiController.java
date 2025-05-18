package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.StapelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/api")
public class KarteikarteApiController {

	private final StapelService stapelService;

	public KarteikarteApiController(StapelService stapelService) {
		this.stapelService = stapelService;
	}

	@PostMapping("/add-neue-karte-normal")
	public ResponseEntity<Void> addNewKarteikarteNormal(@RequestBody NewNormalKarteikarteRequest nKRequest) {
		try {
			Karteikarte karte = nKRequest.buildNormaleKarte();
			stapelService.updateSetWithNewKarteikarte(nKRequest.stapelId(), karte);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/add-neue-karte-choice")
	public ResponseEntity<Void> addNewKarteikarteChoice(@RequestBody NewChoiceKarteikarteRequest choiceKRequest) {
		try {
			Karteikarte karte = choiceKRequest.buildChoiceKarte();
			stapelService.updateSetWithNewKarteikarte(choiceKRequest.stapelId(), karte);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}

