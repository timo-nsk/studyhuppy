package com.studyhuppy.systemtests.stepdefs;

import com.studyhuppy.systemtests.pages.auth.RegistrationPage;
import com.studyhuppy.systemtests.service.DatabaseCleanUpService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationStep {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private DatabaseCleanUpService cleanUpService;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        registrationPage = new RegistrationPage(driver);
        cleanUpService = new DatabaseCleanUpService();
    }

    @After
    public void tearDown() {
        driver.quit();
        cleanUpService.deleteDummyRegistrationUser();
    }



    @Given("Ich besuche {string}")
    public void ichBesuche(String url) {
        registrationPage.get(url);
    }

    @When("Ich die Email-Adresse {string} eingebe")
    public void ichDieEmailAdresseEingebe(String email) {
        registrationPage.enterEmail(email);
    }


    @And("Ich den Benutzernamen {string} eingebe")
    public void ichDenBenutzernamenEingebe(String username) {
        registrationPage.enterUsername(username);
    }


    @And("Ich das Passwort {string} eingebe")
    public void ichDasPasswortEingebe(String pw) {
        registrationPage.enterPassword(pw);
    }

    @And("Ich das Fachsemester {string} auswähle")
    public void ichDasFachsemesterAuswähle(String semester) {
        registrationPage.enterSemester(semester);
    }

    @And("Ich Benachrichtigungen akzeptiere")
    public void ichBenachrichtigungenAkzeptiere() {
        registrationPage.confirmBenachrichtigungen();
    }

    @And("Ich die AGB akzeptiere")
    public void ichDieAGBAkzeptiere() {
        registrationPage.confirmAgb();
    }

    @And("Ich auf den Button zum Registrieren klicke")
    public void ichAufDenButtonZumRegistrierenKlicke() throws InterruptedException {
        registrationPage.clickRegisterButton();
        Thread.sleep(500);
    }

    @Then("Lande ich auf {string}")
    public void landeIchAuf(String url) {
        boolean landedLogin = registrationPage.landedOnLogin();
        assertThat(landedLogin).isTrue();
    }

    @Then("Öffnet sich ein Popup mit dem Text {string}")
    public void öffnetSichEinConfirmationPopupMitDemText(String message) {
        boolean popupShows = registrationPage.showsRegistationMessage(message);
        assertThat(popupShows).isTrue();
    }
}
