package stepDefinitions;

import Reusable.Utilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoldStepDefenition {

    public static WebDriver driver;
    String goldprice;
    String sharenews;

    @Given("the user opens the browser")
    public void theUserOpensTheBrowser() {

        String driverPath = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @When("the user fetches the Gold price")
    public void theUserFetchesTheGoldPrice() {
        driver.get("https://www.goodreturns.in/gold-rates/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")));

        goldprice=driver.findElement(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")).getText();
        Utilities.logger.info("Gold Price: {}", goldprice);


    }

    @When("the user fetches the Share news")
    public void theUserFetchesTheShareNews() throws InterruptedException {
        driver.get("https://gemini.google.com/app?is_sa=1&is_sa=1&android-min-version=301356232&ios-min-version=322.0&campaign_id=bkws&utm_source=sem&utm_source=google&utm_medium=paid-media&utm_medium=cpc&utm_campaign=bkws&utm_campaign=2024enIN_gemfeb&pt=9008&mt=8&ct=p-growth-sem-bkws&gclsrc=aw.ds&gad_source=1&gad_campaignid=20357620749&gbraid=0AAAAApk5BhknX6vJH7_A-go-w5UE4qpAk&gclid=CjwKCAjwpOfHBhAxEiwAm1SwEnPIiy_bFQwjVaZMmeSq8EjeeHZnlYCah9u2vl0k4qzokTfxTRVCcRoC7tcQAvD_BwE");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")));
        driver.findElement(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")).sendKeys("top 5 corporate news today Indian stocks");
        Thread.sleep(20000);
        try {
            driver.findElement(By.xpath("//*[contains(@class,'mat-mdc-tooltip-trigger send-button-container')]")).click();
        }
        catch(Exception e)
        {
            Utilities.logger.info("Unable to click send in Gemini");
        }
        Thread.sleep(120000);

        try {
            WebElement element2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")));
            sharenews = driver.findElement(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")).getText();
        }
        catch(Exception e1)
        {
            driver.findElement(By.xpath("//*[contains(@class,'mat-mdc-tooltip-trigger send-button-container')]")).click();
            WebElement element3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")));
            driver.findElement(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")).sendKeys("top 5 corporate news today Indian stocks");
            Thread.sleep(5000);
            try {
                WebElement element2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")));
                sharenews = driver.findElement(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")).getText();
            }
            catch (Exception e2)
            {
                sharenews="unable to fetch the share price today";
            }
        }
        Utilities.logger.info("Share News: {}", sharenews);
        System.out.println(sharenews);

    }

    @Then("the user writes the Gold price")
    public void theUserWritesTheGoldPrice() {

        List data = new ArrayList<>();

        data.add(Utilities.currentDate());
        data.add(goldprice);

        Utilities.appendToExcel("src/test/resources/Data/GoldData.xlsx","Sheet1", data);


    }

    @Then("the user writes the Share news")
    public void theUserWritesTheShareNews() {

        List data = new ArrayList<>();

        data.add(Utilities.currentDate());
        data.add(sharenews);

        Utilities.appendToExcel("src/test/resources/Data/StockNews.xlsx","Sheet1", data);


    }
    @Then("user closes the browser")
    public void userClosesTheBrowser() {

        driver.quit();

    }
}
