package com.studyhub.mindmap.domain.model;

import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@NoArgsConstructor
@Node("RootMindmapNode")
public class RootMindmapNode extends MindmapNode{
    private UUID modulId;

    public RootMindmapNode(String title, String text) {
        super(title, text, NodeType.SUBJECT);
        this.modulId = null;
    }

    public RootMindmapNode(UUID modulId, String title, String text) {
        super(title, text, NodeType.SUBJECT);
        this.modulId = modulId;
    }

    public UUID getModulId() {
        return modulId;
    }
}
