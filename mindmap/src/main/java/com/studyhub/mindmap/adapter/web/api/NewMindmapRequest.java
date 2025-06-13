package com.studyhub.mindmap.adapter.web.api;

import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;

import java.util.UUID;

public record NewMindmapRequest(String title, UUID modulId, String modulName) {
    public MindmapNode toMindmap(String username) {
        return MindmapNode.initRootNode(modulId, modulName, title, null, NodeType.SUBJECT, username);
    }
}
