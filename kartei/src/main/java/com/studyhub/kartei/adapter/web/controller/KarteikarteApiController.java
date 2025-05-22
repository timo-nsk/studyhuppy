package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.KarteikarteUpdateException;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.UpdateInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api")
public class KarteikarteApiController {

	private final StapelService stapelService;
	private final KarteikarteService karteikarteService;

	public KarteikarteApiController(StapelService stapelService, KarteikarteService karteikarteService) {
		this.stapelService = stapelService;
        this.karteikarteService = karteikarteService;
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

	@PostMapping("/update-karteikarte")
	public ResponseEntity<Void> updateKarteikarte(@RequestBody UpdateInfo updateInfo) {
		boolean success = karteikarteService.updateKarteikarteForNextReview(updateInfo);
		if (!success) throw new KarteikarteUpdateException("Karteikarte could not be updated.");

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/delete-karte")
	public ResponseEntity<Void> deleteKarteikarte(@RequestBody DeleteKarteRequest deleteKRequest) {
		stapelService.deleteKarteikarteByFachId(UUID.fromString(deleteKRequest.stapelId()), UUID.fromString(deleteKRequest.karteId()));
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/remove-antwort-from-karte")
	public ResponseEntity<Void> removeAntwortFromKarte(@RequestBody RemoveAntwortRequest removeRequest) {
		try {
			stapelService.removeAntwortFromKarte(removeRequest);
		} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/edit-karte-normal")
	public ResponseEntity<Void> editNomraleKarte(@RequestBody EditNormalKarteikarteRequest editRequest) {
		karteikarteService.editNormalKarteikarte(editRequest);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/edit-karte-normal")
	public ResponseEntity<Void> editChoiceKarte(@RequestBody EditChoiceKarteikarteRequest editRequest) {
		karteikarteService.editChoiceKarteikarte(editRequest);
		return ResponseEntity.ok().build();
	}

}

