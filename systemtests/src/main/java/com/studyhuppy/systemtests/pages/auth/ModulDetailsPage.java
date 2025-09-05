package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ModulDetailsPage {
	private WebDriver driver;

	public ModulDetailsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void login(String username, String password) throws InterruptedException {
		driver.get("http://localhost:4200/login");
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("login-btn")).click();
	}

	public void get(String url) {
		driver.get("http://localhost:4200" + url);
	}

	public void clickLinkWithTitle(String title) {
		driver.findElement(By.linkText(title)).click();
	}

	public void clickAnyFachsemesterreiter() {
		waitForAngular(); // Angular fertig rendern lassen

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		By accordionLocator = By.cssSelector("button.accordion");

		wait.until(ExpectedConditions.visibilityOfElementLocated(accordionLocator));

		List<WebElement> fachsemesterReiter = driver.findElements(accordionLocator);

		if (fachsemesterReiter.isEmpty()) {
			throw new RuntimeException("Kein Fachsemesterreiter gefunden!");
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fachsemesterReiter.get(0));
		fachsemesterReiter.get(0).click();
	}



	public String clickAnyLinkWithModulNamen() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("accordion")));

		List<WebElement> modulCardContainers = driver.findElements(By.className("accordion"));

		System.out.println(modulCardContainers);

		WebElement anyModulCard = modulCardContainers.get(0);

		WebElement linkToDetails = anyModulCard.findElement(By.tagName("a"));

		linkToDetails.click();

		return linkToDetails.getText();
	}

	public void clickDeleteModulButton() {
		driver.findElement(By.id("delete-module-btn")).click();
	}

	public void waitForAngular() {
		new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
				((JavascriptExecutor) driver).executeScript(
						"return (window.getAllAngularTestabilities ? " +
								"window.getAllAngularTestabilities().every(x=>x.isStable()) : true);"
				).equals(true)
		);
	}
}