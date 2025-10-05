package com.studyhub.kartei.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DateiLader {
    public MockMultipartFile ladeDateiAlsMultipart(String pfad) throws IOException {
        File file = new File(pfad);
        FileInputStream input = new FileInputStream(file);

        return new MockMultipartFile(
                "file",
                file.getName(),
                "text/plain",
                input
        );
    }
}

