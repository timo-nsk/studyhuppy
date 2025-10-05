package com.studyhub.mindmap.adapter.web.api;

import com.studyhub.mindmap.application.service.MindmapNodeRepository;
import com.studyhub.mindmap.application.service.MindmapNotExistsException;
import com.studyhub.mindmap.application.service.MindmapService;
import com.studyhub.mindmap.application.service.NodeSaveException;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.Tuple;

import java.util.*;

@RestController
@RequestMapping("/api/mindmap/v1")
public class MindmapApiController {

	private static final Logger log = LoggerFactory.getLogger(MindmapApiController.class);
	private final MindmapService mindmapService;
	private final MindmapNodeRepository mindmapNodeRepository;

	public MindmapApiController(MindmapService mindmapService, MindmapNodeRepository mindmapNodeRepository) {
		this.mindmapService = mindmapService;
		this.mindmapNodeRepository = mindmapNodeRepository;
	}

	@GetMapping("/get-all-mindmaps-by-username")
	public ResponseEntity<Set<MindmapNode>> getAllMindmapsByUsername(HttpServletRequest request) {
		try {
			Set<MindmapNode> res = mindmapService.getAllMindmapsByUsername(request);
			return ResponseEntity.ok(res);
		} catch (MindmapNotExistsException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/get-all-mindmaps-groupedby-module")
	public ResponseEntity<Map<String, List<MindmapNode>>> getAllMindmapsGroupedByModule(HttpServletRequest request) {
		Map<String, List<MindmapNode>> res = mindmapService.getAllMindmapsGroupedByModule(request);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/get-all-other-mindmaps-by-username")
	public ResponseEntity<List<MindmapNode>> getAllOtherMindmapsByUsername(HttpServletRequest request) {
		List<MindmapNode> res = mindmapService.getAllOtherMindmapsByUsername(request);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/get-mindmap-by-modulid")
	public ResponseEntity<MindmapNode> getMindmapByModulid(@RequestParam("modulId") UUID modulId) {
		try {
			MindmapNode res =  mindmapService.getMindmapByModulId(modulId);
			return ResponseEntity.ok(res);
		} catch (MindmapNotExistsException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/create-mindmap-test")
	public ResponseEntity<Map<String, List<MindmapNode>>> createMindmap() {
		Map<String, List<MindmapNode>> res = new HashMap<>();
		UUID modulId1 = UUID.randomUUID();
		UUID modulId2 = UUID.randomUUID();
		MindmapNode mm1 = MindmapNode.initRootNode(modulId1, "Mathe für Info 1","Mathe", "cool", NodeType.SUBJECT, "timo");
		MindmapNode child1 = MindmapNode.initChildNode("Analysis", "geil", NodeType.SUBJECT);
		MindmapNode child2 = MindmapNode.initChildNode("Lineare ALgebra", "nice", NodeType.SUBJECT);
		mm1.setChildNodes(List.of(child1, child2));
		mindmapService.saveMindmap(mm1);

		MindmapNode mm2 = MindmapNode.initRootNode(modulId1, "Mathe für Info 1", "Geile sache", "cool", NodeType.SUBJECT, "timo");
		MindmapNode child12 = MindmapNode.initChildNode("aerhaerh", "geil", NodeType.SUBJECT);
		MindmapNode child22 = MindmapNode.initChildNode("aerherh", "nice", NodeType.SUBJECT);
		mm2.setChildNodes(List.of(child12, child22));
		mindmapService.saveMindmap(mm2);
		res.put(modulId1.toString(), List.of(mm1, mm2));

		MindmapNode mm3 = MindmapNode.initRootNode(modulId2, "Deutsch Schule", "Deutsch", "cool", NodeType.SUBJECT, "timo");
		MindmapNode child13 = MindmapNode.initChildNode("Epik", "geil", NodeType.SUBJECT);
		MindmapNode child23 = MindmapNode.initChildNode("Drama", "nice", NodeType.SUBJECT);
		mm3.setChildNodes(List.of(child13, child23));
		mindmapService.saveMindmap(mm3);
		res.put(modulId2.toString(), List.of(mm3));

		System.out.println("init placeholder mindmap");

		return ResponseEntity.ok(res);
	}

	@PostMapping("/create-mindmap")
	public ResponseEntity<Void> createMindmap(@RequestBody NewMindmapRequest req, HttpServletRequest httpServletRequest) {
		try {
			mindmapService.createNewMindmap(req, httpServletRequest);
			System.out.println("hurensohn");
			return ResponseEntity.ok().build();
		} catch(NodeSaveException e) {
			log.error("Could not save node of role ROOT:", e);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/create-new-node")
	public ResponseEntity<Void> createNewNode(@RequestBody NewNodeRequest req) {
		try {
			mindmapService.createNewNode(req);
			return ResponseEntity.ok().build();
		} catch(NodeSaveException e) {
			log.error("Could not save node of role CHILD to parent:", e);
			return ResponseEntity.internalServerError().build();
		}
	}
}