package com.studyhuppy.systemtests.pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPage {

    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void get(String url) {
        driver.get("http://localhost:4200" + url);
    }

    public void enterEmail(String email) {
        driver.findElement(By.id("mail")).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void enterUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }

    public void enterSemester(String semester) {
        driver.findElement(By.id("semester")).sendKeys(semester);
    }

    public void confirmBenachrichtigungen() {
        driver.findElement(By.id("subscription")).click();
    }

    public void confirmAgb() {
        driver.findElement(By.id("agb")).click();
    }

    public void clickRegisterButton() {
        driver.findElement(By.className("sh-form-btn-content")).click();
    }
    public boolean landedOnLogin() {
        String url = driver.getCurrentUrl();
        return url.contains("/login");
    }

    public boolean showsRegistationMessage(String message) {
        String m = driver.findElement(By.className("cdk-overlay-container")).getText();
        return m.contains(message);
    }

    public WebElement findUserAlreadyExistsSpan() {
        return driver.findElement(By.id("user-exists-err"));
    }
}
