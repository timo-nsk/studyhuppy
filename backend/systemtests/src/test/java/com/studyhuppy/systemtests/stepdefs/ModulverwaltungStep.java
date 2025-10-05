package com.studyhuppy.systemtests.stepdefs;

import com.studyhuppy.systemtests.pages.auth.Login;
import com.studyhuppy.systemtests.pages.auth.LoginPage;
import com.studyhuppy.systemtests.pages.auth.ModulverwaltungPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class ModulverwaltungStep {

    private ModulverwaltungPage modulverwaltungPage;
    private WebDriver driver;
    private Login login;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        modulverwaltungPage = new ModulverwaltungPage(driver);
        login = new Login(driver);
        login.systemLogin("systest", "12345678");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("Ich klicke auf den Optionen-Button")
    public void ichKlickeAufDenOptionenButton() {
        modulverwaltungPage.clickOptionenButton();
    }

    @Then("Ich klicke auf das Dropdown-Menü mit meinen Modulen")
    public void ichKlickeAufDasDropdownMenüMitMeinenModulen() {
        modulverwaltungPage.clickModulDropdown();
    }

    @And("Ich wähle das Modul mit dem Namen {string} aus")
    public void ichWähleDasModulMitDemNamenAus(String modulName) {
        modulverwaltungPage.selectModulFromDropdown(modulName);
    }

    @And("Ich klicke auf den Löschen-Link mit dem Text {string}")
    public void ichKlickeAufDenLöschenLinkMitDemText(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }

    @Then("Öffnet sich ein Popup-Fenster mit dem Text {string}")
    public void öffnetSichEinPopupFensterMitDemText(String text) {
        boolean res = modulverwaltungPage.showsDeleteModulPopup(text);
        assertThat(res).isTrue();
    }
}
