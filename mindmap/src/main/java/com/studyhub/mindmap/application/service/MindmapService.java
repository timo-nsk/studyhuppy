package com.studyhub.mindmap.application.service;

import com.studyhub.mindmap.domain.model.MindmapNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MindmapService {

	private final Logger log = LoggerFactory.getLogger(MindmapService.class);
	private final MindmapNodeRepository mindmapNodeRepository;

	public MindmapService(MindmapNodeRepository mindmapNodeRepository) {
		this.mindmapNodeRepository = mindmapNodeRepository;
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
}
