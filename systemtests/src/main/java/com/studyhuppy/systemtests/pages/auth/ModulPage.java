package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ModulPage {

    private WebDriver driver;

    public ModulPage(WebDriver driver) {
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

    public void findModulPlayButtonByModulIdAndClick(String modulId) {
        driver.findElement(By.id(modulId)).click();
    }

    public String getGesamtLernzeitTextById(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public void enterModulName(String modulName) {
        driver.findElement(By.id("modul-name")).sendKeys(modulName);
    }

    public void enterLeistungspunkte(String leistungspunkte) {
        driver.findElement(By.id("cp")).sendKeys(leistungspunkte);
    }

    public void enterKontaktzeit(String kzeit) {
        driver.findElement(By.id("kontaktzeit")).sendKeys(kzeit);
    }

    public void enterSelbststudiumzeit(String szeit) {
        driver.findElement(By.id("selbststudium")).sendKeys(szeit);
    }

    public void klickLerntage() {
        driver.findElement(By.id("lerntage-checkbox")).click();
    }

    public void klickLerntageBox(String id) {
        driver.findElement(By.id(id)).click();
    }

    public void clickModulHinzufügenButton() {
        WebElement button = driver.findElement(By.cssSelector(".sh-form-btn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public boolean showsPopupMessage(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement popup = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("mat-mdc-simple-snack-bar")));

        return popup.getText().contains(text);
    }

    public void closeSnackbarMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mat-mdc-button")));
        WebElement popup = driver.findElement(By.className("mat-mdc-button"));
        driver.findElement(By.className("mat-mdc-button")).click();
    }
}
