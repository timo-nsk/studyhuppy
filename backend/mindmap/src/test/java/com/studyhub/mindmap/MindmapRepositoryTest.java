package com.studyhub.mindmap;

import com.studyhub.mindmap.application.service.MindmapNodeRepository;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataNeo4jTest
@ActiveProfiles("test")
public class MindmapRepositoryTest {

    static Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:latest");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        container.start();
        registry.add("spring.neo4j.uri", container::getBoltUrl);
        registry.add("spring.neo4j.authentication.password", container::getAdminPassword);
    }

    @Autowired
    private MindmapNodeRepository mindmapRepository;

    private MindmapNode mindmap = MindmapMother.initBasicMindmap();

    @Test
    @DisplayName("Eine Mindmap kann gespeichert werden")
    void test1() {
        MindmapNode res = mindmapRepository.save(mindmap);

        assertThat(res.getModulId())
                .describedAs("Die Modul-Id darf nicht null sein, da es beim Speichern generiert werden sollte")
                .isNotNull();
        assertThat(res.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("Eine Mindmap wird anhand der modulId gefunden")
    void test2() {
        UUID modulId1 = UUID.fromString("c0a80123-4667-89ab-cdef-1234567890ab");
        UUID modulId2 = UUID.fromString("d0a80123-4667-89ab-cdef-1234567890ab");
        UUID modulId3 = UUID.fromString("c1a80123-4667-89ab-cdef-1234567890ab");
        MindmapNode mm1 = MindmapMother.initBasicMindmapWithModulId(modulId1, "roooooot");
        MindmapNode mm2 = MindmapMother.initBasicMindmapWithModulId(modulId2, "rootyroot");
        MindmapNode mm3 = MindmapMother.initBasicMindmapWithModulId(modulId3, "plemplem");
        mindmapRepository.save(mm1);
        mindmapRepository.save(mm2);
        mindmapRepository.save(mm3);

        MindmapNode res = mindmapRepository.findByModulId(modulId2).get();

        assertThat(res.getTitle()).isEqualTo("rootyroot");
        assertThat(res.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("Wenn ein Child hinzugefügt wird, wird der veränderte Baum korrekt abgespeichert")
    void test3() {
        MindmapNode newChild = MindmapMother.initChildNode(UUID.randomUUID(), "newchild", "bla", NodeType.SUBJECT);
        UUID rootId = UUID.fromString("b4a1fe90-8d8f-4c6d-bb38-0e5fcac1c1c0");
        UUID parentId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        mindmapRepository.save(mindmap);
        MindmapNode res = mindmapRepository.findByModulId(rootId).get();

        res.addChildNode(parentId, newChild);

        MindmapNode finalRes = mindmapRepository.save(res);
        assertThat(finalRes.size()).isEqualTo(8);
        assertThat(mindmapRepository.findByModulId(rootId).get().findChildNode(newChild.getNodeId()).getTitle()).isEqualTo("newchild");
    }

    @Test
    @DisplayName("Alle Mindmaps werden für einen User gefunden")
    void test4() {
        MindmapNode mm1 = MindmapMother.initBasicMindmapForUser("peter77");
        MindmapNode mm2 = MindmapMother.initBasicMindmapForUser("susi83746");
        MindmapNode mm3 = MindmapMother.initBasicMindmapForUser("peter77");
        MindmapNode mm4 = MindmapMother.initBasicMindmapForUser("flutschfinger1");

        mindmapRepository.save(mm1);
        mindmapRepository.save(mm2);
        mindmapRepository.save(mm3);
        mindmapRepository.save(mm4);

        assertThat(mindmapRepository.findAllByUsername("peter77").get()).hasSize(2);
    }

    @Test
    @DisplayName("Wenn keine Mindmaps für einen User existieren, wird ein leeres Set zurückgegeben")
    void test5() {
        MindmapNode mm1 = MindmapMother.initBasicMindmapForUser("peter77");
        MindmapNode mm2 = MindmapMother.initBasicMindmapForUser("susi83746");
        MindmapNode mm3 = MindmapMother.initBasicMindmapForUser("peter77");
        MindmapNode mm4 = MindmapMother.initBasicMindmapForUser("flutschfinger1");

        mindmapRepository.save(mm1);
        mindmapRepository.save(mm2);
        mindmapRepository.save(mm3);
        mindmapRepository.save(mm4);

        assertThat(mindmapRepository.findAllByUsername("peter").get()).isNotNull();
        assertThat(mindmapRepository.findAllByUsername("peter").get()).hasSize(0);
    }
}