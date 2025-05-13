package com.studyhub.track.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.AngularApi;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.application.service.ModulStat;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics/api/v1")
public class StatisticApiController {

	private final ModulEventService modulEventService;
	private final ModulService modulService;
	private final JWTService jwtService;

	public StatisticApiController(ModulEventService modulEventService, ModulService modulService, JWTService jwtService) {
		this.modulEventService = modulEventService;
        this.modulService = modulService;
		this.jwtService = jwtService;
	}

	@AngularApi
	@GetMapping("/chart")
	public ResponseEntity<Map<LocalDate, List<ModulStat>>> getStats(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		return ResponseEntity.ok(modulEventService.getStatisticsForRecentDays(7, username));
	}

	@AngularApi
	@GetMapping("/get-total-study-time")
	public ResponseEntity<Integer> getTotalStudyTime(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		return ResponseEntity.ok(modulService.getTotalStudyTimeForUser(username));
	}

	@AngularApi
	@GetMapping("/get-total-study-time-per-semester")
	public ResponseEntity<Map<Integer, Integer>> getTotalStudyTimePerSemester(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		Map<Integer, Integer> res = modulService.getTotalStudyTimePerFachSemester(username);
		return ResponseEntity.ok(res);
	}

	@AngularApi
	@GetMapping("/get-number-active-module")
	public ResponseEntity<String> getNumberActiveModule(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		String n = String.valueOf(modulService.countActiveModules(username));
		return ResponseEntity.ok(n);
	}
	@AngularApi
	@GetMapping("/get-number-not-active-module")
	public ResponseEntity<String> getNumberNotActiveModule(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		String n = String.valueOf(modulService.countNotActiveModules(username));
		return ResponseEntity.ok(n);
	}

	@AngularApi
	@GetMapping("/get-max-studied-modul")
	public ResponseEntity<String> getMaxStudiedModul(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		String maxModul = modulService.findModulWithMaxSeconds(username);
		return ResponseEntity.ok(maxModul);
	}

	@AngularApi
	@GetMapping("/get-min-studied-modul")
	public ResponseEntity<String> getMinStudiedModul(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		String minModul = modulService.findModulWithMinSeconds(username);
		return ResponseEntity.ok(minModul);
	}
}
