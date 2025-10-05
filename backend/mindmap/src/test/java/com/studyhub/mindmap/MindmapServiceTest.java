package com.studyhub.mindmap;

import com.studyhub.mindmap.application.service.JWTService;
import com.studyhub.mindmap.application.service.MindmapNodeRepository;
import com.studyhub.mindmap.application.service.MindmapService;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MindmapServiceTest {

	static MindmapService mindmapService;

	@BeforeAll
	static void setup() {
		MindmapNodeRepository mindmapNodeRepository = mock(MindmapNodeRepository.class);
		JWTService mockJwtService = mock(JWTService.class);
		mindmapService = new MindmapService(mindmapNodeRepository, mockJwtService);
	}

	@Test
	@DisplayName("Außer den Mindmaps eines Users werden nur diejnigen gefiltert, die nicht mit einem Modul verlinkt sind")
	void test1() {
		MindmapNode mm1 = MindmapNode.initNoneModulRootNode("Data Science", "blub", NodeType.SUBJECT, "peter");
		MindmapNode mm2 = MindmapNode.initRootNode(UUID.randomUUID(), "modul1", "Mathe", "blub", NodeType.SUBJECT, "peter");
		Set<MindmapNode> origSet = new HashSet<>();
		origSet.add(mm1);
		origSet.add(mm2);

		List<MindmapNode> res = mindmapService.filterOthers(origSet);

		assertThat(res).hasSize(1);

	}


}
