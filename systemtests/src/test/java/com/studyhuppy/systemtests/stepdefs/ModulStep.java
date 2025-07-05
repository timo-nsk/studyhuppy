package com.studyhuppy.systemtests.stepdefs;

import com.studyhuppy.systemtests.pages.auth.ModulPage;
import com.studyhuppy.systemtests.service.TimeStringParser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ModulStep {

    private WebDriver driver;
    private ModulPage modulPage;
    private static int start = 0;
    private static int end = 0;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        modulPage = new ModulPage(driver);
    }

    @After
    public void teardown() {
        driver.quit();
    }


    @Given("Ich bin als {string} mit dem Passwort {string} eingeloggt und auf der Seite {string}")
    public void eingeloggt(String username, String password, String url) throws InterruptedException {
        modulPage.login(username, password);
        Thread.sleep(100);
    }

    @When("Ich klicke auf den Pagination-Button mit dem Titel {string}")
    public void ichKlickeAufDenPaginationButtonMitDemTitel(String title) throws InterruptedException {
        modulPage.clickLinkWithTitle(title);
        Thread.sleep(200);
    }

    @And("Sehe ich die die gelernte Zeit auf dem {string}-Span mit der Id {string}")
    public void seheIchDieDieGelernteZeitAufDemGesamtSpanMitDerId(String arg0, String id) {
        String text = modulPage.getGesamtLernzeitTextById(id);
        start = TimeStringParser.parseTextToSeconds(text);
    }

    @And("Ich klicke auf den Button des Moduls Modul A mit der id {string}")
    public void ichKlickeAufDenPlayButtonDesModuls(String modulId) {
        modulPage.findModulPlayButtonByModulIdAndClick(modulId);
    }

    @And("Ich lerne {int} Sekunden")
    public void ichLerneSekunden(int secondsLearned) throws InterruptedException {
        Thread.sleep(2000);
    }

    @And("Ich klicke auf den Button des Moduls {string}")
    public void ichKlickeAufDenStopButtonDesModuls(String modulId) {
        modulPage.findModulPlayButtonByModulIdAndClick(modulId);
    }

    @Then("Ist die gelernte Zeit auf dem Gesamt-Span mit der Id {string} um {int} größer als vor dem Lernen")
    public void istDieGelernteZeitAufDemGesamtSpanMitDerIdUmGrößerAlsVorDemLernen(String id, int timeDelta) {
        String text = modulPage.getGesamtLernzeitTextById(id);
        end = TimeStringParser.parseTextToSeconds(text);

        int diff = end - start;

        assertThat(diff).isGreaterThanOrEqualTo(timeDelta);
    }
}
