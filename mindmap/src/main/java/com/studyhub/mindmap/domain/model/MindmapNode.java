package com.studyhub.mindmap.domain.model;

import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class MindmapNode {
    @Id
    @GeneratedValue
    private UUID nodeId;
    private String title;
    private String text;
    private NodeType nodeType;

    @Relationship(type = "LINKS_TO", direction = Relationship.Direction.OUTGOING)
    private List<MindmapNode> childNodes;

    public MindmapNode(String title, String text, NodeType nodeType) {
        this.nodeId = null;
        this.title = title;
        this.text = text;
        this.nodeType = nodeType;
        this.childNodes = new ArrayList<>();
    }

    public boolean addChildNode(MindmapNode node) {
        return childNodes.add(node);
    }

    public MindmapNode findChildNode(UUID uuid) {
        for (MindmapNode childNode : childNodes) {
            if (childNode.nodeId.equals(uuid)) {
                return childNode;
            } else {
                MindmapNode result = childNode.findChildNode(uuid);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}
