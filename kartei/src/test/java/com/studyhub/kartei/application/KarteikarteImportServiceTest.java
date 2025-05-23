package com.studyhub.kartei.application;

import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.KarteikarteImportService;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.util.DateiLader;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class KarteikarteImportServiceTest {

    private static KarteikarteImportService service;
    private static StapelRepository repository;
    private static DateiLader dl;

    @BeforeAll
    static void init() {
        repository = mock(StapelRepository.class);
        service = new KarteikarteImportService(repository);
        dl = new DateiLader();
    }

    @Test
    @DisplayName("Daten aus der MultipartFile werden korrekt als List<String[]>-Datenstruktur transformiert")
    void test_1() throws IOException {
        MockMultipartFile multipartFile = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen.csv");

        List<String[]> res = service.processFile(multipartFile);

        assertThat(res.get(0)[0]).isEqualTo("frage1");
        assertThat(res.get(1)[1]).isEqualTo("aw2");
        assertThat(res.get(2)[2]).isEqualTo("notiz3");
    }

    @Test
    @DisplayName("Nachdem die Daten transformiert wurden, werden sie erfolgreich dem gewünschten Stapel hinzugefügt")
    void test_2() throws IOException {
        MockMultipartFile multipartFile = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen.csv");
        Stapel s = StapelMother.initSetWithoutKarteikarten();
        UUID stapelId = s.getFachId();
        when(repository.findByFachId(stapelId)).thenReturn(s);

        service.importKarteikarten(multipartFile, String.valueOf(stapelId));

        assertThat(s.getKarteikarten()).hasSize(3);
        verify(repository).save(s);
    }
}
