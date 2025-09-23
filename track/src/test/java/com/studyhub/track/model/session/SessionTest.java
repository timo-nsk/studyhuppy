package com.studyhub.track.model.session;

import com.studyhub.track.domain.model.session.Block;
import com.studyhub.track.domain.model.session.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SessionTest {
    @Test
    @DisplayName("Für eine Session wird die totale Sessionzeit berechnet")
    void test_1() {
        UUID uuid = UUID.randomUUID();
        Block b1 = new Block(uuid, uuid, "Modul", 30, 20);
        Block b2 = new Block(uuid, uuid, "Modul", 40, 20);
        Block b3 = new Block(uuid, uuid, "Modul", 50, 20);
        List<Block> blocks = List.of(b1, b2, b3);
        Session session = new Session(UUID.randomUUID(), "tuser", "tsession", "beschreibung", blocks);

        int total = session.getTotalZeit();

        assertThat(total).isEqualTo(180);
    }
}
