package com.studyhub.mindmap.adapter.web.api;

import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;

import java.util.UUID;

public class NewNodeRequest {
	private UUID parentNodeId;
	private String title;
	private NodeType nodeType;

	public NewNodeRequest(UUID parentNodeId, String title, NodeType nodeType) {
		this.parentNodeId = parentNodeId;
		this.title = title;
		this.nodeType = nodeType;
	}

	public MindmapNode toChildNode() {
		return MindmapNode.initChildNode(title, null, nodeType);
	}

	public UUID getParentId() {
		return this.parentNodeId;
	}

	public void setParentNodeId(UUID parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public String toString() {
		return "NewNodeRequest{" +
				"parentNodeId=" + parentNodeId +
				", title='" + title + '\'' +
				", nodeType=" + nodeType +
				'}';
	}
}
