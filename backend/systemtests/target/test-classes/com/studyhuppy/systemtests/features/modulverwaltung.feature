Feature: Modulverwatlung
  Scenario: Ein Benutzer löscht ein Modul
    Given Ich klicke auf den Optionen-Button
    Then  Ich klicke auf das Dropdown-Menü mit meinen Modulen
    And   Ich wähle das Modul mit dem Namen "Modul C" aus
    And   Ich klicke auf den Löschen-Link mit dem Text "löschen"
    Then  Öffnet sich ein Popup-Fenster mit dem Text "Modul erfolgreich gelöscht"