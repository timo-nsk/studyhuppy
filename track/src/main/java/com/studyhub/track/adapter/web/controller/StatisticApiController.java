package com.studyhub.track.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.AngularApi;
import com.studyhub.track.adapter.web.Api;
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

	@Api
	@GetMapping("/api/stats")
	public Map<LocalDate, List<ModulStat>> getStats(@CookieValue("auth_token") String token) {
		return modulEventService.getStatisticsForRecentDays(7, token);
	}

	@AngularApi
	@GetMapping("/get-total-study-time")
	public ResponseEntity<String> getTotalStudyTime(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = jwtService.extractTokenFromHeader(header);
		String username = jwtService.extractUsername(token);
		String totalStudyTime = modulService.getTotalStudyTimeForUser(username);
		return ResponseEntity.ok(totalStudyTime);
	}

	@AngularApi
	@GetMapping("/get-total-study-time-per-semester")
	public ResponseEntity<Map<Integer, Integer>> getTotalStudyTimePerSemester(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = jwtService.extractTokenFromHeader(header);
		String username = jwtService.extractUsername(token);
		Map<Integer, Integer> res = modulService.getTotalStudyTimePerFachSemester(username);
		return ResponseEntity.ok(res);
	}

	@AngularApi
	@GetMapping("/get-number-active-module")
	public ResponseEntity<String> getNumberActiveModule(HttpServletRequest request) {
		String username = extractUsernameFromHeader(request);
		String n = String.valueOf(modulService.countActiveModules(username));
		System.out.println(n);
		return ResponseEntity.ok(n);
	}
	@AngularApi
	@GetMapping("/get-number-not-active-module")
	public ResponseEntity<String> getNumberNotActiveModule(HttpServletRequest request) {
		String username = extractUsernameFromHeader(request);
		String n = String.valueOf(modulService.countNotActiveModules(username));
		System.out.println(n);
		return ResponseEntity.ok(n);
	}

	@AngularApi
	@GetMapping("/get-max-studied-modul")
	public ResponseEntity<String> getMaxStudiedModul(HttpServletRequest request) {
		String username = extractUsernameFromHeader(request);
		String maxModul = modulService.findModulWithMaxSeconds(username);
		System.out.println("ping4");
		return ResponseEntity.ok(maxModul);
	}

	@AngularApi
	@GetMapping("/get-min-studied-modul")
	public ResponseEntity<String> getMinStudiedModul(HttpServletRequest request) {
		String username = extractUsernameFromHeader(request);
		String minModul = modulService.findModulWithMinSeconds(username);
		System.out.println("ping5");
		return ResponseEntity.ok(minModul);
	}

	private String extractUsernameFromHeader(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		String token = jwtService.extractTokenFromHeader(header);
		return jwtService.extractUsername(token);
	}
}
