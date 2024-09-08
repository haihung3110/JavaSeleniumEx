package com.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.CaptureScreenshot;
import utils.PropertiesFileUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Lab7DemoTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Haihung/.wdm/drivers/chromedriver/win64/127.0.6533.88/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test(priority = 0)
    public void TC01_LoginFirstAccount() throws InterruptedException {
        String URL = PropertiesFileUtils.getProperty("base_url");
        String email1 = PropertiesFileUtils.getProperty("email_1");
        String password1 = PropertiesFileUtils.getProperty("password_1");

        driver.get(URL);
        WebElement iconSignIn = driver.findElement(By.xpath("//a[@href='/login']"));
        iconSignIn.click();

        loginPage.enterEmail(email1);
        loginPage.enterPassword(password1);
        loginPage.clickSignIn();

        Thread.sleep(Duration.ofSeconds(4));

        WebElement iconSignOut = driver.findElement(By.xpath("//a[@href='/logout']"));
        Assert.assertTrue(iconSignOut.isDisplayed(), "Sign out button is not displayed");

        iconSignOut.click();
        Thread.sleep(5000);
    }

    @Test(priority = 1)
    public void TC02_LoginSecondAccount() throws InterruptedException {
        String URL = PropertiesFileUtils.getProperty("base_url");
        String email2 = PropertiesFileUtils.getProperty("email_2");
        String password2 = PropertiesFileUtils.getProperty("password_2");

        driver.get(URL);
        WebElement iconSignIn = driver.findElement(By.xpath("//a[@href='/login']"));
        iconSignIn.click();

        loginPage.enterEmail(email2);
        loginPage.enterPassword(password2);
        loginPage.clickSignIn();

        Thread.sleep(Duration.ofSeconds(4));

        WebElement iconSignOut = driver.findElement(By.xpath("//a[@href='/logout']"));
        Assert.assertTrue(iconSignOut.isDisplayed(), "Sign out button is not displayed");

        iconSignOut.click();
        Thread.sleep(5000);
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                CaptureScreenshot.takeScreenshot(driver, result.getName());
                System.out.println("Screenshot captured: " + result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}