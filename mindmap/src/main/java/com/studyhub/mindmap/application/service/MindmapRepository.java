package com.studyhub.mindmap.application.service;

import com.studyhub.mindmap.domain.model.MindmapNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface MindmapRepository extends Neo4jRepository<MindmapNode, UUID> {

    MindmapNode findByTitle(String title);
}
