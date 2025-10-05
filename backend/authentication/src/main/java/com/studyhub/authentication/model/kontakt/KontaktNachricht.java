package com.studyhub.authentication.model.kontakt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
public class KontaktNachricht {
    @Id
    private Long id;
    private UUID nachrichtId;
    private Betreff betreff;
    private String nachricht;
    private BearbeitungStatus bearbeitungStatus;
}
