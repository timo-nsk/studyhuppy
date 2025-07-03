package com.studyhuppy.systemtests.stepdefs;


import com.studyhuppy.systemtests.pages.auth.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStep {

    private LoginPage loginPage;
    private WebDriver driver;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("Der Benutzer ruft {string} auf")
    public void pageAufruf(String res) {
        loginPage.get(res);
    }

    @Then("der Benutzer gibt {string} als Benutzernamen ein")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("der Benutzer gibt {string} als Passwort ein")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("der Benutzer klickt auf den Login-Button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("landet der User auf {string}")
    public void landetDerUserAuf(String res) {
        boolean landed = loginPage.landetOnUrl(res);
        assertThat(landed).isTrue();
    }

    @Then("landet der User nicht auf {string}")
    public void landetDerUserNichtAuf(String res) {
        boolean landed = loginPage.landetOnUrl(res);
        assertThat(landed).isFalse();
    }

    @Then("der Fehler {string} wird auf der Seite angezeigt")
    public void derFehlerWirdAufDerSeiteAngezeigt(String error) throws InterruptedException {
        Thread.sleep(100);
        String span = driver.findElement(By.className("bad-credentials-error")).getText();
        assertThat(span).isEqualTo(error);
    }
}
