package com.studyhub.mindmap.domain.model;

import java.util.UUID;

public class ChildMindmapNode extends MindmapNode {
    public ChildMindmapNode(String title, String text, NodeType nodeType) {
        super(title, text, nodeType);
    }

    public ChildMindmapNode(UUID nodeId, String title, NodeType nodeType) {
        super(nodeId, title, null, nodeType);
    }
}
