package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

public class CaptureScreenshot {
    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String imageName = name + ".png";
            File destiny = new File("./screenshots/" + imageName);
            FileHandler.copy(source, destiny);
        } catch (Exception ex) {
            System.out.println("An error occurred while capturing the screenshot!");
            ex.printStackTrace();
        }
    }
}