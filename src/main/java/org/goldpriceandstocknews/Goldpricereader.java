package org.goldpriceandstocknews;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class Goldpricereader {

    public static String getgoldprice() throws InterruptedException, IOException {

            // ✅ Set path to ChromeDriver executable
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Desktop\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");


        // ✅ Create ChromeDriver instance
            WebDriver driver = new ChromeDriver();

            // ✅ Maximize browser window
            driver.manage().window().maximize();

            // ✅ Open a website (Google or Copilot)
            driver.get("https://www.goodreturns.in/gold-rates/");

            // Optional: Close browser after delay

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")));

        String goldprice=driver.findElement(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")).getText();
            System.out.println(goldprice);
            return goldprice;
            // driver.quit();

    }


}
