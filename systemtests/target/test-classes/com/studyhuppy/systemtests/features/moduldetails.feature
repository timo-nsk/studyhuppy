Feature: Benutzer sieht eine Übersicht mit Informationen und Optionen zum jeweiligen Modul
  Scenario: Ein Benutzer löscht ein zuvor erstelltes Modul
    Given Ich bin als "systest" mit dem Passwort "12345678" eingeloggt und auf der Seite "/module"
    When Ich klicke auf den Pagination-Button mit dem Titel "Meine Module"
    And Ich klicke auf einen Fachsemester-Reiter, wo die Module sind
    And Ich klicke auf den Modul-Namen und sich die Seite zu den Details des Moduls öffnet
    And Ich klicke auf den Button <Modul löschen>
    And Ich gehe auf die Seite "/module/meine-module"
    Then Ist das Modul nicht mehr auf module sichtbar