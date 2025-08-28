package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.adapter.web.controller.request.dto.LernplanRequest;
import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan/v1")
public class LernplanApiController {

	private LernplanService lernplanService;
	private JWTService jwtService;

	public LernplanApiController(LernplanService lernplanService, JWTService jwtService) {
		this.lernplanService = lernplanService;
		this.jwtService = jwtService;
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
	public ResponseEntity<Lernplan> getActiveLernplan(HttpServletRequest httpRequest) {
		String username = jwtService.extractUsernameFromHeader(httpRequest);
		Lernplan lernplan = lernplanService.getActiveLernplanByUsername(username);

		if (lernplan != null) {
			return ResponseEntity.ok(lernplan);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
