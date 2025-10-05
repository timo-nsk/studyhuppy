package com.studyhub.track.model.lernplan;

import com.studyhub.track.domain.model.lernplan.TagDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagDtoTest {
    @Test
    @DisplayName("Objekt wird konstruiert")
    void test_1() {
        TagDto tagDto = new TagDto("a", "b", "c");
    }
}
