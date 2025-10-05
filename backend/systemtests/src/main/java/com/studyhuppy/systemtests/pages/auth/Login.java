package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    private WebDriver driver;

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    private void get(String url) {
        driver.get("http://localhost:4200" + url);
    }

    private void enterUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }

    private void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    private void clickLoginButton() {
        driver.findElement(By.id("login-btn")).click();
    }

    public void systemLogin(String username, String password) {
        get("/login");
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }


}
