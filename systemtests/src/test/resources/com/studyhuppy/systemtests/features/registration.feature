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
    Then öffnet sich ein Confirmation-Popup mit dem Text "Ihr Benutzerkonto wurde erfolgreich angelegt"