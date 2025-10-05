package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void get(String url) {
        driver.get("http://localhost:4200" + url);
    }

    public void enterUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(By.id("login-btn")).click();
    }

    public boolean landetOnUrl(String endpoint) {
        String url = driver.getCurrentUrl();
        System.out.println(url);
        return url != null && url.contains(endpoint);
    }
}
