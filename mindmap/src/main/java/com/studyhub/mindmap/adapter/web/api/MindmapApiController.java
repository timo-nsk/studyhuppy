package com.studyhub.mindmap.adapter.web.api;

import com.studyhub.mindmap.application.service.MindmapNotExistsException;
import com.studyhub.mindmap.application.service.MindmapService;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/mindmap/v1")
public class MindmapApiController {

	private final MindmapService mindmapService;

	public MindmapApiController(MindmapService mindmapService) {
		this.mindmapService = mindmapService;
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

	@GetMapping("/create-mindmap")
	public ResponseEntity<Void> createMindmap() {
		MindmapNode res = MindmapNode.initRootNode(UUID.randomUUID(), "Mathe", "cool", NodeType.SUBJECT, "timo");
		MindmapNode child1 = MindmapNode.initChildNode("Analysis", "geil", NodeType.SUBJECT);
		MindmapNode child2 = MindmapNode.initChildNode("Lineare ALgebra", "nice", NodeType.SUBJECT);
		res.setChildNodes(List.of(child1, child2));
		mindmapService.saveMindmap(res);

		System.out.println("init placeholder mindmap");

		return ResponseEntity.ok().build();
	}

	@PostMapping("/create-new-node")
	public ResponseEntity<Void> createNewNode(@RequestBody NewNodeRequest req) {
		System.out.println(req.toString());

		mindmapService.createNewNode(req);

		return ResponseEntity.ok().build();
	}
}