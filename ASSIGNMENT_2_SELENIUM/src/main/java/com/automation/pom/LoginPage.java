package com.automation.pom;

import com.automation.utils.PropertiesFileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        Thread.sleep(2000);
    }

    public void enterPassword(String password) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
        Thread.sleep(2000);
    }

    public void clickSignIn() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        Thread.sleep(2000);
    }
}