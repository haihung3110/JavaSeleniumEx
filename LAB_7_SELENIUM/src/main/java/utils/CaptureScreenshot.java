package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

public class CaptureScreenshot {
    public static void takeScreenshot(WebDriver driver, String testcaseName) {
        try {
            String imageName = testcaseName + ".png";
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destiny = new File("./Screenshots/" + imageName);
            FileHandler.copy(source, destiny);
        } catch (Exception ex) {
            System.out.println("An error occurred while capturing the screenshot!");
            ex.printStackTrace();
        }
    }
}