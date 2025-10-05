package com.studyhuppy.systemtests.stepdefs;

import com.studyhuppy.systemtests.pages.auth.ModulDetailsPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuldetailsStep {
	private WebDriver driver;
	private ModulDetailsPage modulDetailsPage;
	private String modulTitel;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		modulDetailsPage = new ModulDetailsPage(driver);}

	@After
	public void teardown() {
		driver.quit();
	}

	@And("Ich klicke auf einen Fachsemester-Reiter, wo die Module sind")
	public void step_2() {
		modulDetailsPage.clickAnyFachsemesterreiter();
	}

	@And("Ich klicke auf den Modul-Namen und sich die Seite zu den Details des Moduls öffnet")
	public void step_3() throws InterruptedException {
		modulTitel = modulDetailsPage.clickAnyLinkWithModulNamen();

		Thread.sleep(200);
	}

	@And("Ich klicke auf den Button <Modul löschen>")
	public void step_4() throws InterruptedException {
		modulDetailsPage.clickDeleteModulButton();
		Thread.sleep(200);
	}

	@And("Ich gehe auf die Seite {string}")
	public void step_5(String url) throws InterruptedException {
		modulDetailsPage.get(url);
	}

	@Then("Ist das Modul nicht mehr auf module sichtbar")
	public void step_6() {
		String thatModulTitle = driver.findElement(By.linkText(modulTitel)).getText();

		assertThat(thatModulTitle).isEmpty();
	}
}
