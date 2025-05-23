package com.studyhub.kartei.application;

import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.KarteikarteImportService;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.util.DateiLader;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.io.FileWriter;
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
        write("src/test/resources/com/studyhub/kartei/service/application/large1.csv", 1);
        write("src/test/resources/com/studyhub/kartei/service/application/large2.txt", 1);
    }

    @AfterAll
    static void tearDown() {
        write("src/test/resources/com/studyhub/kartei/service/application/large1.csv", 0);
        write("src/test/resources/com/studyhub/kartei/service/application/large2.txt", 0);
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

    @Test
    @DisplayName("Dateien ohne Notizen aber mit Fragen und Antworten werden erfolgreich in eine List<String[]>-Datenstruktur transformiert")
    void test_3() throws IOException {
        MockMultipartFile multipartFile = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen_no_notiz.csv");

        List<String[]> res = service.processFile(multipartFile);

        assertThat(res.get(0)).hasSize(3);
        assertThat(res.get(1)).hasSize(2);
        assertThat(res.get(1)[0]).isEqualTo("frage2");
        assertThat(res.get(2)[1]).isEqualTo("aw3");
    }

    @Test
    @DisplayName("Dateien, die nicht .txt oder .csv und unter, werden angelehnt")
    void test_4() throws IOException {
        MockMultipartFile multipartFile = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/wrong_dateiendung.yml");

        boolean res = service.validateFile(multipartFile);

        assertThat(res).isFalse();
    }

    @Test
    @DisplayName("Dateien, die .txt oder .csv  und unter 1MB groß sind, werden akzeptiert ")
    void test_5() throws IOException {
        MockMultipartFile multipartFile1 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen.csv");
        MockMultipartFile multipartFile2 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen2.txt");

        boolean res1 = service.validateFile(multipartFile1);
        boolean res2 = service.validateFile(multipartFile2);

        assertThat(res1).isTrue();
        assertThat(res2).isTrue();
    }

    @Test
    @DisplayName("Leere Dateien werden abgelehnt")
    void test_6() throws IOException {
        MockMultipartFile multipartFile1 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/empty.csv");

        boolean res = service.validateFile(multipartFile1);

        assertThat(res).isFalse();
    }

    @Test
    @DisplayName("Daten über 1MB Größe werden abgelehnt")
    void test_7() throws IOException {
        MockMultipartFile multipartFile1 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/test_fragen.csv");

        boolean res = service.validateFile(multipartFile1);

        assertThat(res).isTrue();
    }

    @Test
    @DisplayName("Dateien, die .txt oder .csv  und über 1MB groß sind, werden abgelehnt ")
    void test_8() throws IOException {
        MockMultipartFile multipartFile1 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/large1.csv");
        MockMultipartFile multipartFile2 = dl.ladeDateiAlsMultipart("src/test/resources/com/studyhub/kartei/service/application/large2.txt");

        boolean res1 = service.validateFile(multipartFile1);
        boolean res2 = service.validateFile(multipartFile2);

        assertThat(res1).isFalse();
        assertThat(res2).isFalse();
    }

    @Test
    @DisplayName("Beispiel-Input werden korrekt von der Regex gematched")
    void test_9() throws IOException {
        String s1 = "a";
        String s2 = "a;";
        String s3 = "a;b";
        String s4 = "a;b;";
        String s5 = "a;b;c";
        String s6 = ";;;";
        String s7 = ";b;c";
        String s8 = ";c;";
        String s9 = ";;a";
        String s10 = "a;;";
        String s11 = "abc";

        assertThat(service.validFormat(s1)).isFalse();
        assertThat(service.validFormat(s2)).isFalse();
        assertThat(service.validFormat(s3)).isTrue();
        assertThat(service.validFormat(s4)).isFalse();
        assertThat(service.validFormat(s5)).isTrue();
        assertThat(service.validFormat(s6)).isFalse();
        assertThat(service.validFormat(s7)).isFalse();
        assertThat(service.validFormat(s8)).isFalse();
        assertThat(service.validFormat(s9)).isFalse();
        assertThat(service.validFormat(s10)).isFalse();
        assertThat(service.validFormat(s11)).isFalse();

    }

    private static void write(String file, int writeFlag) {
        if (writeFlag == 0) {
            try (FileWriter writer = new FileWriter(file)) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (writeFlag == 1) {
            try (FileWriter writer = new FileWriter(file)) {
                for(int i = 0; i <= 1_048_577; i++) {
                    writer.write("a");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}