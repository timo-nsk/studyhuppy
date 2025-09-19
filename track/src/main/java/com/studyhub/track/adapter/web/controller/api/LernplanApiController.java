package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.adapter.web.AngularApi;
import com.studyhub.track.adapter.web.controller.request.dto.LernplanRequest;
import com.studyhub.track.application.service.dto.LernplanWochenuebersicht;
import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.application.service.LernplanAktivierungsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plan/v1")
public class LernplanApiController {

	private LernplanService lernplanService;
	private LernplanAktivierungsService lernplanAktivierungsService;
	private JWTService jwtService;

	public LernplanApiController(LernplanService lernplanService, LernplanAktivierungsService lernplanAktivierungsService, JWTService jwtService) {
		this.lernplanService = lernplanService;
		this.lernplanAktivierungsService = lernplanAktivierungsService;
		this.jwtService = jwtService;
	}

	@GetMapping("/get-all-lernplaene")
	public ResponseEntity<List<Lernplan>> getAllLernplaene(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Lernplan> lernplaene = lernplanService.findAllLernplaeneByUsername(username);
		return ResponseEntity.ok(lernplaene);
	}

	@PostMapping("/create")
	public ResponseEntity<Void> createLernplan(@RequestBody LernplanRequest lernplanRequest, HttpServletRequest httpRequest) {
		Lernplan lernplan = lernplanRequest.toEntity(jwtService.extractUsernameFromHeader(httpRequest));
		boolean success = lernplanService.saveLernplan(lernplan);

		if (success) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/get-active-lernplan")
	public ResponseEntity<LernplanWochenuebersicht> getActiveLernplan(HttpServletRequest httpRequest) {
		String username = jwtService.extractUsernameFromHeader(httpRequest);
		LernplanWochenuebersicht lernplan = lernplanService.collectLernplanWochenuebersicht(username);

		if (lernplan != null) {
			return ResponseEntity.ok(lernplan);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete-lernplan/{fachId}")
	public ResponseEntity<Void> deleteLernplan(@PathVariable UUID fachId) {
		lernplanService.deleteLernplanByFachId(fachId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/set-active-lernplan/{fachId}")
	public ResponseEntity<Void> setActiveLernplan(@PathVariable UUID fachId, HttpServletRequest httpRequest) {
		String username = jwtService.extractUsernameFromHeader(httpRequest);
		lernplanAktivierungsService.setActiveLernplan(fachId, username);
		return ResponseEntity.ok().build();
	}

	@AngularApi
	@GetMapping("/has-lernplan")
	public ResponseEntity<Boolean> hasLernplan(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Lernplan> data = lernplanService.findAllLernplaeneByUsername(username);
		return ResponseEntity.ok(!data.isEmpty());
	}

	@AngularApi
	@GetMapping("/is-today-planned")
	public ResponseEntity<Boolean> isTodayPlanned(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		LernplanWochenuebersicht res = lernplanService.collectLernplanWochenuebersicht(username);

		if (res == null) {
			return ResponseEntity.ok(false);
		}  else {
			boolean isTodayPlanned = res.isTodayPlanned();
			return ResponseEntity.ok(isTodayPlanned);
		}
	}
}
