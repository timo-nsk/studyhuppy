package com.studyhub.kartei.service.application;


import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class KarteikarteImportService {

    private final int MAX_BYTE = 1_048_576; // 1MB

    private final String FORMAT_REGEX = "^[^;]+;[^;]+$|^[^;]+;[^;]+;[^;]+$";

    private final StapelRepository stapelRepository;

    public KarteikarteImportService(StapelRepository stapelRepository) {
        this.stapelRepository = stapelRepository;
    }

    public void importKarteikarten(MultipartFile file, String stapelId)  throws IOException, PatternSyntaxException {
        List<String[]> zeilen = processFile(file);
        Stapel stapel = stapelRepository.findByFachId(UUID.fromString(stapelId));

        for (String[] zeile : zeilen) {
            String frage = zeile[0];
            String antwort = zeile[1];
            String notiz = null;

            if(zeile.length == 3) notiz = zeile[2];

            Karteikarte k = Karteikarte.initNewKarteikarte(frage, antwort, notiz, FrageTyp.NORMAL);
            stapel.addKarteikarte(k);
        }
        stapelRepository.save(stapel);
    }

    public List<String[]> processFile(MultipartFile file) throws IOException, PatternSyntaxException, InvalidFileException, InvalidFormatException {
        boolean validFile = validateFile(file);
        if (!validFile) throw new InvalidFileException("");


        List<String[]> zeilen = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            boolean validFormat = validFormat(line);
            if (!validFormat) throw new InvalidFormatException("");
            String[] values = line.split(";");
            zeilen.add(values);
        }
        return zeilen;
    }

    public boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String filename = file.getOriginalFilename();

        boolean validExtension = filename.endsWith(".txt") || filename.endsWith(".csv");
        boolean validSize = file.getSize() <= MAX_BYTE;

        return validExtension && validSize;
    }

    public boolean validFormat(String s) {
        return Pattern.compile(FORMAT_REGEX).matcher(s).matches();
    }
}
