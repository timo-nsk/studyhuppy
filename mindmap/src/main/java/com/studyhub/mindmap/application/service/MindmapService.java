package com.studyhub.mindmap.application.service;

import com.studyhub.mindmap.adapter.modul.ModulApiAdatper;
import com.studyhub.mindmap.adapter.web.api.NewMindmapRequest;
import com.studyhub.mindmap.adapter.web.api.NewNodeRequest;
import com.studyhub.mindmap.domain.model.MindmapNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MindmapService {

	private final Logger log = LoggerFactory.getLogger(MindmapService.class);
	private final MindmapNodeRepository mindmapNodeRepository;
	private final JWTService jwtService;
	private final ModulApiAdatper modulApiAdatper;

	public MindmapService(MindmapNodeRepository mindmapNodeRepository, JWTService jwtService, ModulApiAdatper modulApiAdatper) {
		this.mindmapNodeRepository = mindmapNodeRepository;
		this.jwtService = jwtService;
        this.modulApiAdatper = modulApiAdatper;
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

	public void createNewNode(NewNodeRequest req) throws RuntimeException {
		MindmapNode newChildNode = req.toChildNode();
		UUID parentId = req.getParentId();

		Optional<MindmapNode> parentNode = mindmapNodeRepository.findById(parentId);

		if (parentNode.isPresent()) {
			MindmapNode res = parentNode.get();
			res.getChildNodes().add(newChildNode);

			mindmapNodeRepository.save(res);
		} else {
			throw new RuntimeException();
		}
	}

	public void createNewMindmap(NewMindmapRequest req1, HttpServletRequest req2) throws RuntimeException {
		String username = jwtService.extractUsernameFromHeader(req2);
		MindmapNode newRootNode = req1.toMindmap(username);
		mindmapNodeRepository.save(newRootNode);
	}

	public Map<String, List<MindmapNode>> getAllMindmapsGroupedByModule(HttpServletRequest request) {
		String username = jwtService.extractUsernameFromHeader(request);
		Set<MindmapNode> nodes = mindmapNodeRepository.findAllByUsername(username).get();
		List<String> modulIds = new ArrayList<>();
		nodes.stream().map(MindmapNode::getModulId).distinct().map(e -> modulIds.add(String.valueOf(e))).toList();
		Map<String, List<MindmapNode>> res = new HashMap<>();
		for(String modulId : modulIds) {
			List<MindmapNode> mindmapList = new ArrayList<>();
			String titel = "";
			for(MindmapNode node : nodes) {
				if(node.getModulId().toString().equals(modulId)) {
					mindmapList.add(node);
					titel = node.getModulTitel();
				}
			}
			res.put(titel, mindmapList);
		}
		return res;
	}
}
