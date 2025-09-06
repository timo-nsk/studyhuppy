package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.adapter.web.AngularApi;
import com.studyhub.track.adapter.web.controller.request.dto.LernplanRequest;
import com.studyhub.track.adapter.web.controller.request.dto.LernplanResponse;
import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.session.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plan/v1")
public class LernplanApiController {

	private LernplanService lernplanService;
	private JWTService jwtService;

	public LernplanApiController(LernplanService lernplanService, JWTService jwtService) {
		this.lernplanService = lernplanService;
		this.jwtService = jwtService;
	}

	@GetMapping("/get-all-lernplaene")
	public ResponseEntity<List<Lernplan>> getAllLernplaene(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Lernplan> lernplaene = lernplanService.getAllLernplaeneByUsername(username);
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
	public ResponseEntity<LernplanResponse> getActiveLernplan(HttpServletRequest httpRequest) {
		String username = jwtService.extractUsernameFromHeader(httpRequest);
		LernplanResponse lernplan = lernplanService.getActiveLernplanByUsername(username);

		if (lernplan != null) {
			return ResponseEntity.ok(lernplan);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete-lernplan/{fachId}")
	public ResponseEntity<Void> deleteLernplan(@PathVariable UUID fachId) {
		System.out.println(fachId);
		lernplanService.deleteLernplanByFachId(fachId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/set-active-lernplan/{fachId}")
	public ResponseEntity<Void> setActiveLernplan(@PathVariable UUID fachId, HttpServletRequest httpRequest) {
		String username = jwtService.extractUsernameFromHeader(httpRequest);
		lernplanService.setActiveLernplan(fachId, username);
		return ResponseEntity.ok().build();
	}

	@AngularApi
	@GetMapping("/has-lernplan")
	public ResponseEntity<Boolean> hasLernplan(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Lernplan> data = lernplanService.getAllLernplaeneByUsername(username);
		return ResponseEntity.ok(!data.isEmpty());
	}
}
