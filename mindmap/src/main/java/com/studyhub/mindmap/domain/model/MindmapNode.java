package com.studyhub.mindmap.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
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

    public MindmapNode(UUID nodeId, String title, String text, NodeType nodeType) {
        this.nodeId = nodeId;
        this.title = title;
        this.text = text;
        this.nodeType = nodeType;
        this.childNodes = new ArrayList<>();
    }

    public int size() {
        int c = 1;
        for (MindmapNode childNode : childNodes) {
            c += childNode.size();
        }
        return c;
    }

    public boolean addChildNode(UUID parentId, MindmapNode node) {
        MindmapNode parent = findChildNode(parentId);
        return parent.getChildNodes().add(node);
    }

    public boolean removeSubtreeAt(UUID nodeId) {
        MindmapNode toRemove = findChildNode(nodeId);
        return childNodes.remove(toRemove);
    }

    public boolean removeChildNode(UUID parentId, UUID childId) {
        MindmapNode parent = findChildNode(parentId);
        MindmapNode child = findChildNode(childId);
        return parent.getChildNodes().remove(child);
    }

    public void setChildNodeTitel(UUID nodeId, String title) {
        MindmapNode node = findChildNode(nodeId);
        node.setTitle(title);
    }

    public void setChildNodeText(UUID nodeId, String text) {
        MindmapNode node = findChildNode(nodeId);
        node.setText(text);
    }

    public void setChildNodeType(UUID nodeId, NodeType nodeType) {
        MindmapNode node = findChildNode(nodeId);
        node.setNodeType(nodeType);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public List<MindmapNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<MindmapNode> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public String toString() {
        return "MindmapNode{" +
                "nodeId=" + nodeId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", nodeType=" + nodeType +
                ", childNodes=" + childNodes +
                '}';
    }
}