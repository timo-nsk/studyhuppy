package com.studyhub.mindmap.adapter.web.api;

import com.studyhub.mindmap.application.service.MindmapNotExistsException;
import com.studyhub.mindmap.application.service.MindmapService;
import com.studyhub.mindmap.domain.model.MindmapNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class MindmapApiController {

	private final MindmapService mindmapService;

	public MindmapApiController(MindmapService mindmapService) {
		this.mindmapService = mindmapService;
	}

	@GetMapping("/get-mindmap-by-modulid")
	public ResponseEntity<MindmapNode> getMindmapByModulid(@RequestBody UUID modulid) {
		try {
			MindmapNode res =  mindmapService.getMindmapByModulId(modulid);
			return ResponseEntity.ok(res);
		} catch (MindmapNotExistsException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}
}