package com.studyhub.mindmap.domain.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.yaml.snakeyaml.util.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@DirectedAcyclicTree
public class MindmapNode {
    @Id
    @GeneratedValue
    private UUID nodeId;
    private UUID modulId;
    private String title;
    private String text;
    private NodeType nodeType;
    private NodeRole nodeRole;
    private String username;

    @Relationship(type = "LINKS_TO", direction = Relationship.Direction.OUTGOING)
    private List<MindmapNode> childNodes;

    public MindmapNode(UUID modulId, String title, String text, NodeType nodeType, NodeRole nodeRole, String username) {
        this.nodeId = UUID.randomUUID();
        this.modulId = modulId;
        this.title = title;
        this.text = text;
        this.nodeType = nodeType;
        this.nodeRole = nodeRole;
        this.username = username;
        this.childNodes = new ArrayList<>();
    }

    public MindmapNode(UUID nodeId, UUID modulId, String title, String text, NodeType nodeType, NodeRole nodeRole) {
        this.nodeId = nodeId;
        this.modulId = modulId;
        this.title = title;
        this.text = text;
        this.nodeType = nodeType;
        this.nodeRole = nodeRole;
        this.childNodes = new ArrayList<>();
    }

    /**
     * This factory method must be used when creating a new mindmap.
     * A root mindmap node has a module ID and a node ID.
     * The module ID is required for linking to the module in the module service.
     * The root node can additionally be identified by the username.
     *
     * @param modulId ID of the module, which has been created in the module service. Can be 'null'
     * @param title Title of the node
     * @param text Accompanying information for the node
     * @param nodeType Type of the node
     * @param username Username of the user
     * @return Root of the mindmap
     * @throws IllegalRootConsistencyException when parameters are invalid
     */
    @RootFactoryMethod
    public static MindmapNode initRootNode(UUID modulId, String title, String text, NodeType nodeType, String username) {
        Tuple<List<String>, Boolean> resTuple = checkConsistency(modulId, title, nodeType, username);

        if (!resTuple._2()) {
            throw new IllegalRootConsistencyException(resTuple._1().stream().toString());
        }

        return new MindmapNode(modulId, title, text, nodeType, NodeRole.ROOT, username);
    }

    /**
     * A child mindmap node does not have a module ID.
     * It can be found in the tree by its node ID.
     *
     * @param title Title of the node
     * @param text Accompanying information for the node
     * @param nodeType Type of the node
     * @return Child for a mindmap
     * @throws IllegalRootConsistencyException when parameters are invalid
     */
    @ChildFactoryMethod
    public static MindmapNode initChildNode(String title, String text, NodeType nodeType) {
        Tuple<List<String>, Boolean> resTuple = checkConsistency(UUID.randomUUID(), title, nodeType, "dummy");

        if (!resTuple._2()) {
            throw new IllegalRootConsistencyException(resTuple._1().stream().toString());
        }

        return new MindmapNode(UUID.randomUUID(), null, title, text, nodeType, NodeRole.CHILD);
    }


    public MindmapNode() {}

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

    public UUID getModulId() {
        return modulId;
    }

    public void setModulId(UUID modulId) {
        this.modulId = modulId;
    }

    public NodeRole getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(NodeRole nodeRole) {
        this.nodeRole = nodeRole;
    }

    public UUID getNodeId() {
        return nodeId;
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

    public static Tuple<List<String>, Boolean> checkConsistency(UUID modulId, String title, NodeType nodeType, String username) {
        List<String> mustBeSet = new ArrayList<>();

        if(modulId == null || modulId.toString().isEmpty()) {
            mustBeSet.add("modulId");
        }
        if(title == null || title.isEmpty()) {
            mustBeSet.add("title");
        }
        if(nodeType == null || nodeType.name().isEmpty()) {
            mustBeSet.add("nodeType");
        }
        if(username == null || username.isEmpty()) {
            mustBeSet.add("username");
        }

        boolean isValid = mustBeSet.isEmpty();

        return new Tuple<>(mustBeSet, isValid);
    }
}