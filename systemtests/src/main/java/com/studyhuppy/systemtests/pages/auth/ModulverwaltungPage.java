package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ModulverwaltungPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ModulverwaltungPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(2));
    }

    public void clickOptionenButton() {
        driver.findElement(By.id("optionen-btn")).click();
    }

    public void clickModulDropdown() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("modul-dropdown")));
        driver.findElement(By.id("modul-dropdown")).click();
    }

    public void selectModulFromDropdown(String modulName) {
        List<WebElement> elements =  driver.findElements(By.tagName("option"));

        for (WebElement element : elements) {
            if (element.getText().equals(modulName)) {
                element.click();
                break;
            }
        }
    }

    public boolean showsDeleteModulPopup(String text) {
        WebElement popup = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("mat-mdc-simple-snack-bar")));

        return popup.getText().contains(text);
    }

}
