package com.studyhub.mindmap;

import com.studyhub.mindmap.domain.model.IllegalRootConsistencyException;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.util.Tuple;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MindmapNodeTest {

    private MindmapNode mindmap = MindmapMother.initBasicMindmap();

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
        MindmapNode newChild = MindmapNode.initChildNode("im new", "bla",NodeType.SUBJECT);

        mindmap.addChildNode(parentId, newChild);

        assertThat(mindmap.size()).isEqualTo(8);
        assertThat(mindmap.findChildNode(newChild.getNodeId()).getTitle()).isEqualTo("im new");
    }

    @Test
    @DisplayName("Fehlender Parameterwert für 'modulId' returned das korrekte Tupel als Antwort")
    void test6()  {
        UUID modulId1 = null;
        String titel = "titel";
        String username = "username";
        NodeType nodeType = NodeType.SUBJECT;

        Tuple<List<String>, Boolean> res1 = MindmapNode.checkConsistency(modulId1, titel, nodeType, username);

        assertThat(res1._2()).isFalse();
        assertThat(res1._1().contains("modulId")).isEqualTo(true);
    }

    @Test
    @DisplayName("Fehlender Parameterwert für 'title' returned das korrekte Tupel als Antwort")
    void test7()  {
        UUID modulId = UUID.randomUUID();
        String titel1 = null;
        String titel2 = "";
        String username = "username";
        NodeType nodeType = NodeType.SUBJECT;

        Tuple<List<String>, Boolean> res1 = MindmapNode.checkConsistency(modulId, titel1, nodeType, username);
        Tuple<List<String>, Boolean> res2 = MindmapNode.checkConsistency(modulId, titel2, nodeType, username);

        assertThat(res1._2()).isFalse();
        assertThat(res2._2()).isFalse();
        assertThat(res1._1().contains("title")).isEqualTo(true);
        assertThat(res2._1().contains("title")).isEqualTo(true);
    }

    @Test
    @DisplayName("Fehlender Parameterwert für 'nodeType' returned das korrekte Tupel als Antwort")
    void test8()  {
        UUID modulId = UUID.randomUUID();
        String titel = "titel";
        String username = "username";
        NodeType nodeType1 = null;

        Tuple<List<String>, Boolean> res1 = MindmapNode.checkConsistency(modulId, titel, nodeType1, username);

        assertThat(res1._2()).isFalse();
        assertThat(res1._1().contains("nodeType")).isEqualTo(true);
    }

    @Test
    @DisplayName("Fehlender Parameterwert für 'username' returned das korrekte Tupel als Antwort")
    void test9()  {
        UUID modulId = UUID.randomUUID();
        String titel = "titel";
        String username1 = null;
        String username2 = "";
        NodeType nodeType = NodeType.SUBJECT;

        Tuple<List<String>, Boolean> res1 = MindmapNode.checkConsistency(modulId, titel, nodeType, username1);
        Tuple<List<String>, Boolean> res2 = MindmapNode.checkConsistency(modulId, titel, nodeType, username2);

        assertThat(res1._2()).isFalse();
        assertThat(res2._2()).isFalse();
        assertThat(res1._1().contains("username")).isEqualTo(true);
        assertThat(res2._1().contains("username")).isEqualTo(true);
    }

    @Test
    @DisplayName("Ale Parameterwerte fehlen und das korrekte Tupel als Antwort")
    void test10()  {
        UUID modulId = null;
        String titel1 = null;
        String titel2 = "";
        String username1 = null;
        String username2 = "";
        NodeType nodeType = null;

        Tuple<List<String>, Boolean> res1 = MindmapNode.checkConsistency(modulId, titel1, nodeType, username1);
        Tuple<List<String>, Boolean> res2 = MindmapNode.checkConsistency(modulId, titel2, nodeType, username2);

        assertThat(res1._2()).isFalse();
        assertThat(res2._2()).isFalse();
        assertThat(res1._1().contains("username")).isEqualTo(true);
        assertThat(res1._1().contains("modulId")).isEqualTo(true);
        assertThat(res1._1().contains("nodeType")).isEqualTo(true);
        assertThat(res1._1().contains("title")).isEqualTo(true);
        assertThat(res2._1().contains("username")).isEqualTo(true);
        assertThat(res2._1().contains("modulId")).isEqualTo(true);
        assertThat(res2._1().contains("nodeType")).isEqualTo(true);
        assertThat(res2._1().contains("title")).isEqualTo(true);
    }

    @Test
    @DisplayName("Wenn mit ungültigen Parameter 'modulId' eine RootMindmapNode erstellt wird, wird eine Exception geworfen")
    void test11()  {
        UUID modulId = null;
        String titel = "titel";
        NodeType nodeType = NodeType.SUBJECT;
        String username = "peter";

        assertThrows(IllegalRootConsistencyException.class, () -> {
            MindmapNode root = MindmapNode.initRootNode(null, titel, "info", nodeType, username);
        });
    }

    @Test
    @DisplayName("Wenn mit ungültigen Parameter 'title' eine RootMindmapNode erstellt wird, wird eine Exception geworfen")
    void test12()  {
        UUID modulId = UUID.randomUUID();
        String titel = null;
        NodeType nodeType = NodeType.SUBJECT;
        String username = "peter";

        assertThrows(IllegalRootConsistencyException.class, () -> {
            MindmapNode root = MindmapNode.initRootNode(modulId, titel, "info", nodeType, username);
        });
    }

    @Test
    @DisplayName("Wenn mit ungültigen Parameter 'nodeType' eine RootMindmapNode erstellt wird, wird eine Exception geworfen")
    void test13()  {
        UUID modulId = UUID.randomUUID();
        String titel = "title";
        NodeType nodeType = null;
        String username = "peter";

        assertThrows(IllegalRootConsistencyException.class, () -> {
            MindmapNode root = MindmapNode.initRootNode(modulId, titel, "info", nodeType, username);
        });
    }

    @Test
    @DisplayName("Wenn mit ungültigen Parameter 'username' eine RootMindmapNode erstellt wird, wird eine Exception geworfen")
    void test14()  {
        UUID modulId = UUID.randomUUID();
        String titel = "title";
        NodeType nodeType = NodeType.SUBJECT;
        String username = null;

        assertThrows(IllegalRootConsistencyException.class, () -> {
            MindmapNode root = MindmapNode.initRootNode(modulId, titel, "info", nodeType, username);
        });
    }
}
