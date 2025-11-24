package stepDefinitions;

import Reusable.ConfigLoader;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoldStepDefenition {

    public static WebDriver driver;
    String goldprice;
    String sharenews;

    @Given("the user opens the browser")
    public void theUserOpensTheBrowser() {

        String driverPath = ConfigLoader.get("chromedriver.path");
        System.out.println("driver path:"+ driverPath);
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @When("the user fetches the Gold price")
    public void theUserFetchesTheGoldPrice() {
        driver.get(ConfigLoader.get("gold.news.url"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")));

        goldprice=driver.findElement(By.xpath("//*[@id=\"gr_top_intro_content\"]/div/p[1]")).getText();
        Utilities.logger.info("Gold Price: {}", goldprice);


    }

    @When("the user fetches the Share news")
    public void theUserFetchesTheShareNews() throws InterruptedException {
        driver.get(ConfigLoader.get("share.news.gemini"));
        System.out.println("before entering promt");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        WebElement element1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")));
        driver.findElement(By.xpath("//*[@class='ql-editor ql-blank textarea new-input-ui']")).sendKeys("top "+ConfigLoader.get("number.of.share.news")+ " corporate news today Indian stocks and number each share news as 1. ,2.,3. etc");
        Thread.sleep(20000);
        System.out.println("after entering promt");
        try {
            driver.findElement(By.xpath("//*[contains(@class,'mat-mdc-tooltip-trigger send-button-container')]")).click();
            System.out.println("prompt sent");
        }
        catch(Exception e)
        {
            System.out.println("inside catch send prompt");
            Utilities.logger.info("Unable to click send in Gemini");
        }
        Thread.sleep(120000);

        try {
            System.out.println("before getting share news");
            WebElement element2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")));
            sharenews = driver.findElement(By.xpath("//*[@class='markdown markdown-main-panel enable-updated-hr-color']")).getText();
            System.out.println("after getting share news");
            System.out.println("share news is "+sharenews);
        }
        catch(Exception e1)
        {
           e1.printStackTrace();
            /*try {
                driver.findElement(By.xpath("//*[contains(@class,'mat-mdc-tooltip-trigger send-button-container')]")).click();
            }
            catch(Exception e)
            {
                Utilities.logger.info("Unable to click send in gemini");
            }
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
            }*/
        }
        Utilities.logger.info("Share News: {}", sharenews);
        System.out.println(sharenews);

    }

    @Then("the user writes the Gold price")
    public void theUserWritesTheGoldPrice() {

        List data = new ArrayList<>();

        data.add(Utilities.currentDate());
        data.add(goldprice);

        Utilities.appendToExcel(
                ConfigLoader.get("gold.data.path"),
                ConfigLoader.get("gold.data.sheet"),
                data
        );


    }

    @Then("the user writes the Share news")
    public void theUserWritesTheShareNews() {

        List data = new ArrayList<>();

        data.add(Utilities.currentDate());
        data.add(sharenews);

        Utilities.appendToExcel(
                ConfigLoader.get("stock.news.path"),
                ConfigLoader.get("stock.news.sheet"),
                data
        );




    }
    @Then("user closes the browser")
    public void userClosesTheBrowser() {

        driver.quit();

    }
}
