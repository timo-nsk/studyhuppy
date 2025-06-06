package com.studyhub.mindmap.application.service;

import com.studyhub.mindmap.domain.model.MindmapNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;
import java.util.UUID;

public interface MindmapNodeRepository extends Neo4jRepository<MindmapNode, UUID> {

    MindmapNode findByModulId(UUID modulId);
}
