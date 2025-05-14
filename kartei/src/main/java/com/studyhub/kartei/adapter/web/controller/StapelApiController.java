package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StapelApiController {

	private final StapelService stapelService;
	private final JWTService jwtService;

	public StapelApiController(StapelService stapelService, JWTService jwtService) {
		this.stapelService = stapelService;
		this.jwtService = jwtService;
	}

	@Api
	@PostMapping("/create-stapel")
	public ResponseEntity<String> createNewStapel(@RequestBody CreateNewStapelRequest req) {
		stapelService.saveSet(req.toNewStapel());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Api
	@GetMapping("/get-faelligeKarten")
	public Map<String, Integer> getFaelligeKarten() {
		Map<String, Integer> responseMap = stapelService.getAnzahlFaelligeKartenForEachStapel(LocalDateTime.now());
		return responseMap;
	}

	// TODO: muss noch per username
	@AngularApi
	@GetMapping("/sets-available")
	public ResponseEntity<Boolean> setsAvailable() {
		return ResponseEntity.ok(stapelService.areKarteiSetsAvailable());
	}

	@AngularApi
	@GetMapping("/get-all-stapel-by-username")
	public ResponseEntity<List<StapelDashboardDto>> getSetsByUsername(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Stapel> stapel = stapelService.findByUsername(username);
		List<StapelDashboardDto> res = stapelService.prepareDashboardInfo(stapel);
		return ResponseEntity.ok(res);
	}
}
