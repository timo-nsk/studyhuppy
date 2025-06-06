package com.studyhub.mindmap;

import com.studyhub.mindmap.domain.model.ChildMindmapNode;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import com.studyhub.mindmap.domain.model.RootMindmapNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MindmapMother {

    public static RootMindmapNode initBasicMindmap() {
        UUID id1 = UUID.fromString("b4a1fe90-8d8f-4c6d-bb38-0e5fcac1c1c0");
        UUID id2 = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        UUID id3 = UUID.fromString("c0a80123-4567-89ab-cdef-1234567890ab");
        UUID id4 = UUID.fromString("e1d7c0e3-2bc0-4af2-9f25-9a3bb9db88ec");
        UUID id5 = UUID.fromString("7d444840-9dc0-11d1-b245-5ffdce74fad2");
        UUID id6 = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID id7 = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");


        RootMindmapNode root = new RootMindmapNode(id1, "ROOT", "SOME INFOS");

        ChildMindmapNode child1 = new ChildMindmapNode(id2, "ch1", NodeType.SUBJECT);
        ChildMindmapNode child2 = new ChildMindmapNode(id3, "ch2", NodeType.SUBJECT);

        ChildMindmapNode child11 = new ChildMindmapNode(id4, "ch11", NodeType.SUBJECT);
        ChildMindmapNode child12 = new ChildMindmapNode(id5, "ch12", NodeType.SUBJECT);
        ChildMindmapNode child21 = new ChildMindmapNode(id6, "ch21", NodeType.SUBJECT);
        ChildMindmapNode child22 = new ChildMindmapNode(id7, "ch22", NodeType.SUBJECT);

        child1.setChildNodes(of(child11, child12));
        child2.setChildNodes(of(child21, child22));

        root.setChildNodes(of(child1, child2));

        return root;
    }

    private static List<MindmapNode> of(ChildMindmapNode... childs) {
        return new ArrayList<>(Arrays.asList(childs));
    }

}
