package com.unit1.core;

import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Unit 1 on 11-Nov-17.
 */
@ScenarioScoped
public class WikipediaTasks {

    private WebDriver driver;
    private static int webDriverWaitInSeconds = 5;
    private Properties properties;
    private Context context;

    @Inject
    public WikipediaTasks(final MarionetteDriver marionetteDriver, final Context context) throws IOException {
        this.driver = marionetteDriver.getFirefoxDriver();
        this.context = context;
        this.properties = new Properties();
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/conf/config.properties");
        this.properties.load(inputStream);
    }

    public void openWikipediaHomePage() {
        driver.get(properties.getProperty("wikipediaUrl"));
    }

    public void searchFor(final String searchString) {
        findElement(WikipediaConstants.searchBar).sendKeys(searchString, Keys.RETURN);
        waitForPageToLoad(searchString);
    }

    public void openLink(final String link) {
        final List<WebElement> links = findElements(WikipediaConstants.wikipediaLinks);
        for (final WebElement element : links) {
            if (element.getAttribute("title").equals(link)) {
                element.click();
                waitForPageToLoad(link);
                break;
            }
        }
    }

    public void openHistory() {
        findElement(WikipediaConstants.wikipediaHistory).click();
        new WebDriverWait(driver, webDriverWaitInSeconds).until(
                ExpectedConditions.urlContains("action=history"));
    }

    public void printRevisions(final int numberOfRevisions) {
        final List<WebElement> revisions = findElements(WikipediaConstants.wikipediaRevisions);

        if (revisions.size() < numberOfRevisions) {
            throw new TestException("Number of actual revisions is lower than the desired number. " +
                    "Expected: " + numberOfRevisions + "\nActual: " + revisions.size());
        }

        for (final WebElement element : revisions.subList(0, numberOfRevisions)) {
            context.writeOut(element.getText());
        }
    }

    public boolean isOnPage(final String page) {
        final String currentPage = driver.getCurrentUrl();
        final String expectedPage = properties.get("wikipediaEnglish")
                + page.replace(" ", "_");
        return currentPage.equals(expectedPage);
    }

     private WebElement findElement(final String identifier) {
        WebDriverWait wait = new WebDriverWait(driver, webDriverWaitInSeconds);
        WebElement element = driver.findElement(By.cssSelector(identifier));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return element;
    }

     private List<WebElement> findElements(final String identifier) {
        List<WebElement> elements = driver.findElements(By.cssSelector(identifier));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return elements;
    }

     private void explicitWait(final int secondsToWait) {
        try {
            Thread.sleep(secondsToWait * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void waitForPageToLoad(final String page) {
        new WebDriverWait(driver, webDriverWaitInSeconds).until(
                ExpectedConditions.urlToBe(properties.get("wikipediaEnglish")
                        + page.replace(" ", "_")));
    }

    public void closeBrowser() {
        driver.close();
    }
}
