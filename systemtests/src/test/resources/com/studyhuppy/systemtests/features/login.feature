Feature: User-Authentifizierung
  Scenario: Ein Benutzer mit dem Benutzernamen "systets" und dem Passwort "12345678" ist registriert
    Given Der Benutzer ruft "/login" auf
    When der Benutzer gibt "systest" als Benutzernamen ein
    And der Benutzer gibt "12345678" als Passwort ein
    And der Benutzer klickt auf den Login-Button
    Then landet der User auf "/module"