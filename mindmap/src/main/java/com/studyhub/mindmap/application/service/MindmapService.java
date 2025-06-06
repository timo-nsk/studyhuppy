package com.studyhub.mindmap.application.service;

import com.studyhub.mindmap.domain.model.MindmapNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MindmapService {

	private final Logger log = LoggerFactory.getLogger(MindmapService.class);
	private final MindmapNodeRepository mindmapNodeRepository;
	private final JWTService jwtService;

	public MindmapService(MindmapNodeRepository mindmapNodeRepository, JWTService jwtService) {
		this.mindmapNodeRepository = mindmapNodeRepository;
		this.jwtService = jwtService;
	}

	public MindmapNode getMindmapByModulId(UUID modulId) {
		Optional<MindmapNode> node = mindmapNodeRepository.findByModulId(modulId);
		if (node.isPresent()) {
			log.info("Found MindmapNode with modulId: %s".formatted(modulId));
			return node.get();
		} else {
			throw new MindmapNotExistsException("Mindmap with modulId " + modulId + " does not exist.");
		}
	}

	public Set<MindmapNode> getAllMindmapsByUsername(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		Optional<Set<MindmapNode>> set = mindmapNodeRepository.findAllByUsername(username);
		if (set.isPresent()) {
			return mindmapNodeRepository.findAllByUsername(username).get();
		} else {
			throw new MindmapNotExistsException("No mindmaps found for user: %s".formatted(username));
		}

	}

	public void saveMindmap(MindmapNode res) {
		mindmapNodeRepository.save(res);
	}
}
