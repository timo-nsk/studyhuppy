package com.studyhub.mindmap;

import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeRole;
import com.studyhub.mindmap.domain.model.NodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MindmapMother {

    public static MindmapNode initBasicMindmap() {
        UUID modulId = UUID.fromString("b4a1fe90-8d8f-4c6d-bb38-0e5fcac1c1c0");
        UUID id2 = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        UUID id3 = UUID.fromString("c0a80123-4567-89ab-cdef-1234567890ab");
        UUID id4 = UUID.fromString("e1d7c0e3-2bc0-4af2-9f25-9a3bb9db88ec");
        UUID id5 = UUID.fromString("7d444840-9dc0-11d1-b245-5ffdce74fad2");
        UUID id6 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID id7 = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");


        MindmapNode root = MindmapNode.initRootNode(modulId, "ROOT", "blabla", NodeType.SUBJECT, "peter77");

        MindmapNode child1 = initChildNode(id2, "ch1", "bla", NodeType.SUBJECT);
        MindmapNode child11 = initChildNode(id4, "ch11", "bla", NodeType.SUBJECT);
        MindmapNode child12 = initChildNode(id5, "ch12", "bla", NodeType.SUBJECT);

        MindmapNode child2 = initChildNode(id3, "ch2", "bla", NodeType.SUBJECT);
        MindmapNode child21 = initChildNode(id6, "ch21", "bla", NodeType.SUBJECT);
        MindmapNode child22 = initChildNode(id7, "ch22", "bla", NodeType.SUBJECT);

        child1.setChildNodes(of(child11, child12));
        child2.setChildNodes(of(child21, child22));

        root.setChildNodes(of(child1, child2));

        return root;
    }

    public static MindmapNode initBasicMindmapWithModulId(UUID modulId, String nodeTitel) {
        MindmapNode root = MindmapNode.initRootNode(modulId, nodeTitel, "blabla", NodeType.SUBJECT, "peter77");

        MindmapNode child1 = initChildNode(UUID.randomUUID(), "ch1", "bla", NodeType.SUBJECT);
        MindmapNode child11 = initChildNode(UUID.randomUUID(), "ch11", "bla", NodeType.SUBJECT);
        MindmapNode child12 = initChildNode(UUID.randomUUID(), "ch12", "bla", NodeType.SUBJECT);

        MindmapNode child2 = initChildNode(UUID.randomUUID(), "ch2", "bla", NodeType.SUBJECT);
        MindmapNode child21 = initChildNode(UUID.randomUUID(), "ch21", "bla", NodeType.SUBJECT);
        MindmapNode child22 = initChildNode(UUID.randomUUID(), "ch22", "bla", NodeType.SUBJECT);

        child1.setChildNodes(of(child11, child12));
        child2.setChildNodes(of(child21, child22));

        root.setChildNodes(of(child1, child2));

        return root;
    }

    public static MindmapNode initBasicMindmapForUser(String username) {
        MindmapNode root = MindmapNode.initRootNode(UUID.randomUUID(), "subj", "blabla", NodeType.SUBJECT, username);

        MindmapNode child1 = initChildNode(UUID.randomUUID(), "ch1", "bla", NodeType.SUBJECT);

        MindmapNode child2 = initChildNode(UUID.randomUUID(), "ch2", "bla", NodeType.SUBJECT);

        root.setChildNodes(of(child1, child2));

        return root;
    }

    private static List<MindmapNode> of(MindmapNode... childs) {
        return new ArrayList<>(Arrays.asList(childs));
    }

    public static MindmapNode initChildNode(UUID nodeId, String titel, String text, NodeType nodeType) {
        return new MindmapNode(nodeId, null, titel, text, nodeType, NodeRole.CHILD);
    }

}
