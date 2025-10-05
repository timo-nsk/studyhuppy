Feature: User-Authentifizierung
  Scenario: Ein Benutzer mit dem Benutzernamen "systets" und dem Passwort "12345678" ist registriert
    Given Der Benutzer ruft "/login" auf
    When der Benutzer gibt "systest" als Benutzernamen ein
    And der Benutzer gibt "12345678" als Passwort ein
    And der Benutzer klickt auf den Login-Button
    Then landet der User auf "/module"

  Scenario: Ein unregistrierter Benutzer versucht sich anzumelden
    Given Der Benutzer ruft "/login" auf
    When der Benutzer gibt "nichtregistriert" als Benutzernamen ein
    And der Benutzer gibt "12345678" als Passwort ein
    And der Benutzer klickt auf den Login-Button
    Then landet der User nicht auf "/module"
    Then der Fehler "Benutzer nicht gefunden" wird auf der Seite angezeigt

  Scenario: Ein Benutzer mit dem Benutzernamen "systets" gibt sein falsches Passwort "12345679" ein
    Given Der Benutzer ruft "/login" auf
    When der Benutzer gibt "systest" als Benutzernamen ein
    And der Benutzer gibt "12345679" als Passwort ein
    And der Benutzer klickt auf den Login-Button
    Then landet der User nicht auf "/module"
    Then der Fehler "Passwort ungültg" wird auf der Seite angezeigt