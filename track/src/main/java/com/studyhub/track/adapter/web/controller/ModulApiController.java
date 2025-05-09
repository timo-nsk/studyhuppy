package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.mail.KlausurReminderDto;
import com.studyhub.track.adapter.web.Api;
import com.studyhub.track.adapter.web.FachIdRequest;
import com.studyhub.track.adapter.web.ModulUpdateRequest;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ModulApiController {

	private final ModulService modulService;
	private final ModulEventService modulEventService;

	public ModulApiController(ModulService service, ModulEventService modulEventService) {
		this.modulService = service;
		this.modulEventService = modulEventService;
	}

	@Api
	@GetMapping("/modules")
	public ResponseEntity<List<Modul>> getAllModules() {
		return ResponseEntity.ok(modulService.findAll());
	}

	@Api
	@PostMapping("/update")
	public ResponseEntity<String> updateSeconds(@RequestBody ModulUpdateRequest request,
	                                            @CookieValue("auth_token") String authToken) {
		modulService.updateSeconds(UUID.fromString(request.fachId()), request.secondsLearned());
		modulEventService.saveEvent(request, authToken);
		return ResponseEntity.ok("Erfolgreich aktualisiert");
	}

	@Api
	@PostMapping("/get-seconds")
	public ResponseEntity<Integer> getSeconds(@RequestBody FachIdRequest request) {
		return ResponseEntity.ok(modulService.getSecondsForId(UUID.fromString(request.fachId())));
	}

	@Api
	@GetMapping("/module-map")
	public Map<UUID, String> getModuleMap() {
		return modulService.getModuleMap();
	}

	@Api
	@GetMapping("/module-name")
	public String getModulName(String modulFachId) {
		return modulService.findModulNameByFachid(UUID.fromString(modulFachId));
	}

	@Api
	@PostMapping("/data-klausur-reminding")
	public List<KlausurReminderDto> findModuleWithoutKlausurDate(@RequestBody List<String> users) {
		return modulService.findModuleWithoutKlausurDate(users);
	}
}
