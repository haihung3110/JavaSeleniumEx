package com.automation.testcase;

import com.automation.base.DriverInstance;
import com.automation.pom.LoginPage;
import com.automation.utils.CaptureScreenshot;
import com.automation.utils.PropertiesFileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TC_LoginTest extends DriverInstance {

    @Test(dataProvider = "Excel")
    public void TC01_LoginFirstAccount(String email, String password) throws InterruptedException {
        driver.get(PropertiesFileUtils.getProperty("url"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement iconSignIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PropertiesFileUtils.getProperty("icon_signin"))));
        iconSignIn.click();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickSignIn();

        WebElement iconSignout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PropertiesFileUtils.getProperty("icon_signout"))));
        if (iconSignout.isDisplayed()) {
            iconSignout.click();
        }

        Thread.sleep(2000);
    }

    @DataProvider(name = "Excel")
    public Object[][] testDataGenerator() throws IOException {
        String excelPath = "src/test/resources/assignment2_data_test.xlsx";
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        List<Object[]> dataList = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell emailCell = row.getCell(0);
            Cell passwordCell = row.getCell(1);

            if (emailCell != null && passwordCell != null) {
                String email = emailCell.getStringCellValue().trim();
                String password = passwordCell.getStringCellValue().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    dataList.add(new Object[]{email, password});
                }
            }
        }

        workbook.close();
        fis.close();

        return dataList.toArray(new Object[0][]);
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                String email = (String) result.getParameters()[0];
                int index = email.indexOf('@');
                String accountName = email.substring(0, index);
                CaptureScreenshot.takeScreenshot(driver, accountName);
            } catch (Exception e) {
                System.out.println("An error occurred while taking the screenshot: " + e.getMessage());
            }
        }
    }
}