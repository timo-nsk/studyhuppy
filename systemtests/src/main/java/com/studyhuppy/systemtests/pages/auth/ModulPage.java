package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        Thread.sleep(100);
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
}
