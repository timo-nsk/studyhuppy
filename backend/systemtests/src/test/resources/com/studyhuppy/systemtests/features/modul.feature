Feature: Benutzer trackt mit erstellten Modulen seinen Lernfortschritt
  Scenario: Ein Benutzer lernt ein Modul
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich klicke auf den Pagination-Button mit dem Titel "Meine Module"
    And Sehe ich die die gelernte Zeit auf dem 'Gesamt:'-Span mit der Id "ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    And Ich klicke auf den Button des Moduls Modul A mit der id "btn-ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    And Ich lerne 2 Sekunden
    And Ich klicke auf den Button des Moduls Modul A mit der id "btn-ed706e7e-38c2-48e3-b719-f5e39c2f152b"
    Then Ist die gelernte Zeit auf dem Gesamt-Span mit der Id "ed706e7e-38c2-48e3-b719-f5e39c2f152b" um 2 größer als vor dem Lernen

  Scenario: Ein Benutzer erstellt ein neues Modul
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "5" Leistungspunkte auswähle
    And Ich "60" als Kontakzeit auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich Lerntage festlegen anklicke
    And Ich Di und Fr anklicke
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Öffnet sich eine Popup-Nachricht mit dem Text "Modul erfolgreich erstellt" erscheint

  Scenario: Ein Benutzer erstellt ein neues Modul ohne Formulardaten
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheinen Fehlermeldungen für die Eingabefelder

  Scenario: Ein Benutzer erstellt ein neues Modul ohne Modulnamen
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich "5" Leistungspunkte auswähle
    And Ich "60" als Kontakzeit auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Bitte geben Sie einen Modulnamen ein"

  Scenario: Ein Benutzer erstellt ein neues Modul ohne Kreditpunkte
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "60" als Kontakzeit auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Bitte geben Sie Kreditpunkte ein"

  Scenario: Ein Benutzer erstellt ein neues Modul mit negativen Kreditpunkte
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "-5" Leistungspunkte auswähle
    And Ich "60" als Kontakzeit auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Kreditpunkte müssen positiv sein"

  Scenario: Ein Benutzer erstellt ein neues Modul ohne Kontaktzeitstunden
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "5" Leistungspunkte auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Bitte geben Sie Kontaktzeitstuden an"

  Scenario: Ein Benutzer erstellt ein neues Modul mit negativen Kontaktzeitstunden
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "5" Leistungspunkte auswähle
    And Ich "-60" als Kontakzeit auswähle
    And Ich "90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Kontaktzeitstuden müssen mindestens '1' betragen"

  Scenario: Ein Benutzer erstellt ein neues Modul ohne Selbststudiumstunden
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "5" Leistungspunkte auswähle
    And Ich "60" als Kontakzeit auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Bitte geben Sie die Selbststudiumstunden an"

  Scenario: Ein Benutzer erstellt ein neues Modul mit negativen Selbststudiumstunden
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich die Popup-Message schließe
    And Ich klicke auf den Pagination-Button mit dem Titel "Modul hinzufügen"
    And Ich den Modulnamen "Modul Test" eingebe
    And Ich "5" Leistungspunkte auswähle
    And Ich "60" als Kontakzeit auswähle
    And Ich "-90" als Selbststudium auswähle
    And Ich klicke auf den Modul-hinzufügen-Button klicke
    Then Erscheint eine Fehlmledung mit dem Text "Selbststudiumstunden müssen mindestens '1' betragen"