Feature: Neue Benutzer können einen Benutzeraccount registrieren
  Scenario: Eine interessierte Person registriert sich mit validen Eingaben
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "new_user_123" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Lande ich auf "/login"
    Then Öffnet sich ein Popup mit dem Text "Ihr Benutzerkonto wurde erfolgreich angelegt"

  Scenario: Eine interessierte Person will sich mit einem Benutzernamen registrieren, der schon vergeben ist
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "systest" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "Dieser Benutzername ist bereits vergeben"

  Scenario: Eine interessierte Person will sich mit invalid Formulardaten registrieren
    Given Ich besuche "/register"
    And Ich auf den Button zum Registrieren klicke
    Then Erscheinen Fehlermeldungen für die EIngabefelder

  Scenario: Eine interessierte Person will sich mit zu kurzen Benutzenamen registrieren
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "a" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "mindestens 3 Zeichen"

  Scenario: Eine interessierte Person will sich mit zu langen Benutzenamen registrieren
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "höchstens 30 Zeichen"

  Scenario: Eine interessierte Person will sich mit zu kurzen Passwort registrieren
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "peter897" eingebe
    And Ich das Passwort "1234567" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "mindestens 8 Zeichen"

  Scenario: Eine interessierte Person will sich mit zu langen Passwort registrieren
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "peter897" eingebe
    And Ich das Passwort "123456789aaaaaaaaaaaa" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "höchstens 20 Zeichen"

  Scenario: Eine interessierte Person will sich mit ungültiger E-Mail-Adresse registrieren
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail" eingebe
    And Ich den Benutzernamen "peter897" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich die AGB akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "Ungültige E-Mail-Adresse"

  Scenario: Eine interessierte Person will sich registrieren aber akzeptiert die AGB nicht
    Given Ich besuche "/register"
    When Ich die Email-Adresse "mymail@web.de" eingebe
    And Ich den Benutzernamen "peter897" eingebe
    And Ich das Passwort "12345678" eingebe
    And Ich das Fachsemester "1" auswähle
    And Ich Benachrichtigungen akzeptiere
    And Ich auf den Button zum Registrieren klicke
    Then Erscheint eine Fehlermeldung auf der Seite mit der Nachricht "Bitte akzeptieren Sie die AGB"