package com.studyhub.track.model.semester;

import com.studyhub.track.domain.model.semester.SemesterPhase;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemesterPhaseTest {
    @Test
    void testEnumValues() {
        SemesterPhase[] values = SemesterPhase.values();
        assertEquals(2, values.length, "Es sollten genau 2 Enum-Werte existieren.");
        assertArrayEquals(
                new SemesterPhase[]{SemesterPhase.VORLESUNG, SemesterPhase.KLAUSUR},
                values,
                "Die Enum-Werte sollten VORLESUNG und KLAUSUR sein."
        );
    }

    @Test
    void testValueOf() {
        assertEquals(SemesterPhase.VORLESUNG, SemesterPhase.valueOf("VORLESUNG"));
        assertEquals(SemesterPhase.KLAUSUR, SemesterPhase.valueOf("KLAUSUR"));
    }

    @Test
    void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> SemesterPhase.valueOf("INVALID"));
    }
}
