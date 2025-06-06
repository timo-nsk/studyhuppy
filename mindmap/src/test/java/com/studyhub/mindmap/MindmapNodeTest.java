package com.studyhub.mindmap;

import com.studyhub.mindmap.domain.model.ChildMindmapNode;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import com.studyhub.mindmap.domain.model.RootMindmapNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MindmapNodeTest {

    private RootMindmapNode mindmap = MindmapMother.initBasicMindmap();

    @Test
    @DisplayName("Ein Kindknoten wird im Baum gefunden")
    void test1() {
        UUID id3 = UUID.fromString("c0a80123-4567-89ab-cdef-1234567890ab");

        MindmapNode found = mindmap.findChildNode(id3);

        assertThat(found.getTitle()).isEqualTo("ch2");
    }

    @Test
    @DisplayName("Ein Kindknoten mit Teilbaum wird aus dem Baum entfernt")
    void test2() {
        UUID id2 = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        boolean success = mindmap.removeSubtreeAt(id2);

        assertThat(success).isTrue();
        assertThat(mindmap.size()).isEqualTo(4);
        System.out.println(mindmap.toString());
    }

    @Test
    @DisplayName("Ein Blatt wird erfolgreich aus dem Baum entfernt")
    void test3() {
        UUID parent = UUID.fromString("c0a80123-4567-89ab-cdef-1234567890ab");
        UUID child = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        boolean success = mindmap.removeChildNode(parent, child);

        assertThat(success).isTrue();
        assertThat(mindmap.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Initialisierte Mindmap hat eine Wurzel-Node mit Modul-Id")
    void test4() {
        assertThat(mindmap.getModulId().toString()).isEqualTo("b4a1fe90-8d8f-4c6d-bb38-0e5fcac1c1c0");
    }

    @Test
    @DisplayName("Einem Elternknoten kann ein Kindknoten hinzugefügt werden")
    void test5() {
        UUID parentId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        UUID childId = UUID.fromString("550e8401-e29b-41d4-a716-446655440000");
        ChildMindmapNode newChild = new ChildMindmapNode(childId, "im new", NodeType.SUBJECT);

        mindmap.addChildNode(parentId, newChild);

        assertThat(mindmap.size()).isEqualTo(8);
        assertThat(mindmap.findChildNode(childId).getTitle()).isEqualTo("im new");
    }
}
