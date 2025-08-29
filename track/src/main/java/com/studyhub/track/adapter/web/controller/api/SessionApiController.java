package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.adapter.web.controller.request.dto.SessionDeleteRequest;
import com.studyhub.track.adapter.web.controller.request.dto.SessionInfoDto;
import com.studyhub.track.adapter.web.controller.request.dto.SessionRequest;
import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.SessionService;
import com.studyhub.track.domain.model.session.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/session/v1")
public class SessionApiController {

	private final SessionService sessionService;
	private final JWTService jwtService;

	public SessionApiController(SessionService sessionService, JWTService jwtService) {
		this.sessionService = sessionService;
		this.jwtService = jwtService;
	}

	@GetMapping("/get-sessions")
	public ResponseEntity<List<Session>> getSessions(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<Session> sessions = sessionService.getSessionsByUsername(username);

		if (sessions.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(sessions);
		}
	}

	@GetMapping("/get-session-by-id")
	public ResponseEntity<Session> getSessionById(@RequestParam UUID sessionId) {
		Session session = sessionService.getSessionByFachId(sessionId);
		return ResponseEntity.ok(session);
	}

	@GetMapping("/get-lernplan-session-data")
	public ResponseEntity<List<SessionInfoDto>> getLernplanSessionData(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		List<SessionInfoDto> sessionInfo = sessionService.getLernplanSessionDataOfUser(username);

		if (sessionInfo.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(sessionInfo);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Void> createSession(@RequestBody SessionRequest sessionRequest, HttpServletRequest request) {
		System.out.println(sessionRequest);
		String username = jwtService.extractUsernameFromHeader(request);
		boolean success = sessionService.save(sessionRequest.toEntity(username));

		if (success) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/delete-session")
	public ResponseEntity<Void> deleteSession(@RequestBody SessionDeleteRequest deleteRequest) {
		System.out.println(deleteRequest);
		sessionService.deleteSession(deleteRequest.fachId());
		return ResponseEntity.ok().build();
	}
}
