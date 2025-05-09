package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.db.modul.ModulDto;
import com.studyhub.track.adapter.db.modul.ModulMapper;
import com.studyhub.track.adapter.mail.KlausurReminderDto;
import com.studyhub.track.adapter.web.*;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import org.springframework.http.HttpStatus;
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

	@AngularApi
	@PostMapping("/update")
	public ResponseEntity<String> updateSeconds(@RequestBody ModulUpdateRequest request) {
		modulService.updateSeconds(UUID.fromString(request.fachId()), request.secondsLearned());
		modulEventService.saveEvent(request, "");
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

	@AngularApi
	@PutMapping("/reset")
	public ResponseEntity<Void> reset(@RequestBody String fachId) {
		System.out.println("reset");
		modulService.resetModulTime(UUID.fromString(fachId));
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@AngularApi
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteModul(@RequestParam String fachId) {
		modulService.deleteModul(UUID.fromString(fachId));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@AngularApi
	@GetMapping("get-active-modules")
	public ResponseEntity<List<ModulDto>> getActiveModules() {
		List<ModulDto> l = modulService.findActiveModuleByUsername(true, "tplaceholder")
				.stream().map(ModulMapper::toModulDto).toList();
		return ResponseEntity.ok(l);
	}

	@AngularApi
	@PostMapping("/new-modul")
	public ResponseEntity<Void> newModule(@RequestBody ModulForm modulForm) {
		//int semester = authenticationService.getSemesterOfUser(jwtService.extractUsername("token"));
		int semester = 6;
		Modul modul = modulForm.newModulFromFormData(modulForm, semester);
		/**
		 if (Optional.ofNullable(modulForm.stapelCheckbox()).orElse(false)) {
		 CreateNewStapelRequest request = new CreateNewStapelRequest(modul.getFachId().toString(), modulForm.stapelName(), modulForm.beschreibung(), modulForm.lernstufen(), jwtService.extractUsername(token));
		 request.validateForBindingResult(bindingResult);
		 if(bindingResult.hasErrors()) return "add-module";
		 stapelRequestService.sendCreateNewStapelRequest(request);
		 }
		 **/

		modulService.saveNewModul(modul);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@AngularApi
	@GetMapping("/get-all-by-username")
	public ResponseEntity<List<ModulDto>> getAllByUsername() {
		// TODO: muss später aus auth_token gezogen werden
		String username = "timo";
		List<ModulDto> l = modulService.findAllByUsername(username).stream().map(ModulMapper::toModulDto).toList();
		return ResponseEntity.ok(l);
	}

	@AngularApi
	@PutMapping("/activate")
	public ResponseEntity<Void> activate(@RequestBody  String fachId) {
		modulService.activateModul(UUID.fromString(fachId));
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@AngularApi
	@PutMapping("/deactivate")
	public ResponseEntity<Void> deactivate(@RequestBody  String fachId) {
		modulService.deactivateModul(UUID.fromString(fachId));
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@AngularApi
	@PostMapping("/add-time")
	public ResponseEntity<Void> addTime(@RequestBody AddTimeRequest req) {
		modulService.addTime(UUID.fromString(req.fachId()), req.time());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@AngularApi
	@PostMapping("/add-klausur-date")
	public ResponseEntity<Void> addKlausurDate(@RequestBody KlausurDateRequest req) {
		modulService.addKlausurDate(UUID.fromString(req.fachId()), req.date(), req.time());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
