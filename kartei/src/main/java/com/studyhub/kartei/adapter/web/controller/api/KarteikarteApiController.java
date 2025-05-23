package com.studyhub.kartei.adapter.web.controller.api;

import com.studyhub.kartei.adapter.web.controller.request.dto.*;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

@Controller
@RequestMapping("/api")
public class KarteikarteApiController {

	private final StapelService stapelService;
	private final KarteikarteService karteikarteService;
	private final KarteikarteImportService karteikarteImportService;

	public KarteikarteApiController(StapelService stapelService, KarteikarteService karteikarteService, KarteikarteImportService karteikarteImportService) {
		this.stapelService = stapelService;
        this.karteikarteService = karteikarteService;
        this.karteikarteImportService = karteikarteImportService;
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

	@PutMapping("/edit-karte-normal")
	public ResponseEntity<Void> editNormaleKarte(@RequestBody EditNormalKarteikarteRequest editRequest) {
		karteikarteService.editNormalKarteikarte(editRequest);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/edit-karte-choice")
	public ResponseEntity<Void> editChoiceKarte(@RequestBody EditChoiceKarteikarteRequest editRequest) {
		karteikarteService.editChoiceKarteikarte(editRequest);
		return ResponseEntity.ok().build();
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

	@PostMapping("/import-karten")
	public ResponseEntity<String> importKarten(@RequestParam("file") MultipartFile file,
											 @RequestParam("stapelId") String stapelId) {
		System.out.println("ping import");
		try {
			karteikarteImportService.importKarteikarten(file, stapelId);
		} catch (PatternSyntaxException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inkorrektes Format");
        } catch (IOException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Es entstand ein Fehler auf dem Server, versuchen Sie es erneut");
		}
        return ResponseEntity.ok("Karteikarten erfolgreich importiert");
	}
}