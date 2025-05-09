package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.adapter.web.validation.NoBlankAntworten;
import com.studyhub.kartei.domain.model.Antwort;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AnyChoiceKarteikarteForm(@NotBlank(message = "Bitte geben Sie eine Frage ein")
                                          String frage,

                                       @NoBlankAntworten
                                       List<Antwort> antworten,

                                       Integer wahrheitIndex,

                                       Integer anzahlAntworten,

                                       String notiz,

                                       FrageTyp frageTyp) {


    public Karteikarte toKarteikarte() throws InvalidAntwortenListFormatException{
        if(frageTyp == FrageTyp.SINGLE_CHOICE) setWahrheit();
        return Karteikarte.initNewSingleChoiceKarteikarte(frage(), antworten(), notiz(), frageTyp());
    }

    public void setWahrheit() {
        if (antworten() == null) throw new InvalidAntwortenListFormatException("Beim umwandeln der Formulardaten in eine Single Choice Kartekarte" +
                "waren die Liste der Antworten leer");

        for (int i = 0; i < antworten().size(); i++) {
            antworten().get(i).setWahrheit(i == wahrheitIndex());
        }

        boolean valid = validAntwortenForSingleChoice();

        if (!valid)  throw new InvalidAntwortenListFormatException("Beim umwandeln der Formulardaten in eine Single Choice Kartekarte" +
                "waren die Liste der Antworten leer");
    }

    private boolean validAntwortenForSingleChoice() {
        int count = 0;
        for (Antwort aw: antworten
             ) {
            if(aw.isWahrheit()) count++;
        }
        System.out.println(count);
        return count == 1;
    }
}
