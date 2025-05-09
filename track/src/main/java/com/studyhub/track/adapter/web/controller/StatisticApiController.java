package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.web.Api;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulStat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticApiController {

	private final ModulEventService modulEventService;

	public StatisticApiController(ModulEventService modulEventService) {
		this.modulEventService = modulEventService;
	}

	@Api
	@GetMapping("/api/stats")
	public Map<LocalDate, List<ModulStat>> getStats(@CookieValue("auth_token") String token) {
		return modulEventService.getStatisticsForRecentDays(7, token);
	}
}
