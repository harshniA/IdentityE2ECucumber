package com.identity.pages;

import com.google.common.base.Function;
import com.identity.commonUtils.CommonUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Page extends CommonUtils {

    public static WebDriver _driver = null;

    public static WebDriverWait webdriverWait;

    public Page(WebDriver driver) {
        _driver = driver;
        webdriverWait = new WebDriverWait(_driver, 15);

    }

    public static String getCurrentWindowHandle() {
        return _driver.getWindowHandle();
    }

    public static void windowsHandleToChildUsingParent() {
        String parentWindowHandle = getCurrentWindowHandle();
        Set<String> windowSet = _driver.getWindowHandles();
        for (String winHandle : windowSet) {
            System.out.println("parent window handles value - " + parentWindowHandle);
            System.out.println("all window handles values - " + winHandle);
            if (!winHandle.equals(parentWindowHandle))
                _driver.switchTo().window(winHandle);
        }
    }

    public String getTitle() {
        return _driver.getTitle();
    }

    public WebElement getElementByJs(String jQuerySelector) {
        JavascriptExecutor js = (JavascriptExecutor) _driver;
        return (WebElement) js.executeScript(jQuerySelector);
    }

    public void scrollToElement(WebElement e) throws InterruptedException {
        ((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", e);
        Thread.sleep(500);
    }

    public void executeJS(String code) {
        JavascriptExecutor js = (JavascriptExecutor) _driver;
        js.executeScript(code, "");

    }

    public boolean IfAlertPresent() {
        try {
            _driver.switchTo().alert();
            return true;

        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public WebElement waitUntil(ExpectedCondition expectedCondition, Class... classes) {
        FluentWait wait = webdriverWait.pollingEvery(1, TimeUnit.SECONDS).withTimeout(20, TimeUnit.SECONDS);
        if (classes != null) {
            for (Class c : classes) {
                wait.ignoring(c);
            }
        }
        return (WebElement) wait.until(expectedCondition);
    }

    public void clickElementWithRetry(final WebElement element) {
        FluentWait<WebElement> customWait = new FluentWait<WebElement>(element)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(200, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);


        customWait

                .until(new Function<WebElement, Boolean>() {

                    public Boolean apply(WebElement element) {

                        try {

                            element.click();

                            return true;

                        } catch (Exception e) {

                            return false;

                        }

                    }

                });

    }
}

