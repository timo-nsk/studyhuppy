Feature: Benutzer trackt mit erstellten Modulen seinen Lernfortschritt
  Scenario: Ein Benutzer lernt ein Modul
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich klicke auf den Pagination-Button mit dem Titel "Meine Module"
    And Sehe ich die die gelernte Zeit auf dem 'Gesamt:'-Span mit der Id "ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    And Ich klicke auf den Button des Moduls Modul A mit der id "btn-ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    And Ich lerne 2 Sekunden
    And Ich klicke auf den Button des Moduls Modul A mit der id "btn-ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    Then Ist die gelernte Zeit auf dem Gesamt-Span mit der Id "ed706e7e-38c2-48e3-b719-f5e39c2f152b" um 2 größer als vor dem Lernen