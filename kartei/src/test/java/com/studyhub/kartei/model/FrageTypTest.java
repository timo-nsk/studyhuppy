package com.studyhub.kartei.model;

import com.studyhub.kartei.domain.model.FrageTyp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FrageTypTest {

	@Test
	@DisplayName("displayNames kann bekommen werden")
	void test_1() {
		FrageTyp normal = FrageTyp.NORMAL;
		FrageTyp sc = FrageTyp.SINGLE_CHOICE;
		FrageTyp mc = FrageTyp.MULTIPLE_CHOICE;

		String normalDisplayName = normal.getDisplayName();
		String scDisplayName = sc.getDisplayName();
		String mcDisplayName = mc.getDisplayName();

		assertThat(normalDisplayName).isEqualTo("Normal");
		assertThat(scDisplayName).isEqualTo("Single Choice");
		assertThat(mcDisplayName).isEqualTo("Multiple Choice");
	}

	@Test
	@DisplayName("allTypes() returned einer Liste mit allen Enum Objekten im Enum")
	void test_2() {
		FrageTyp normal = FrageTyp.NORMAL;
		FrageTyp sc = FrageTyp.SINGLE_CHOICE;
		FrageTyp mc = FrageTyp.MULTIPLE_CHOICE;

		List<FrageTyp> types = FrageTyp.allTypes();

		assertThat(types.get(0)).isEqualTo(normal);
		assertThat(types.get(1)).isEqualTo(sc);
		assertThat(types.get(2)).isEqualTo(mc);
	}
}