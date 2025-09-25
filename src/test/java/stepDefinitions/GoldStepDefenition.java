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
    @Then("the user writes the Gold price")
    public void theUserWritesTheGoldPrice() {

        List data = new ArrayList<>();

        data.add(Utilities.currentDate());
        data.add(goldprice);

        Utilities.appendToExcel("src/test/resources/Data/GoldData.xlsx","Sheet1", data);


    }
    @Then("user closes the browser")
    public void userClosesTheBrowser() {

        driver.quit();

    }
}
