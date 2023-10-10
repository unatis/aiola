package Infra.Common;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Common {

    public enum Browser{
        CHROME,
        FIREFOX
    }

    public enum MessageColor{
        RED,
        BLACK,
        GREEN
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLACK= "\u001B[30m";

    private WebDriver driver = null;
    private WebDriverWait wait = null;

    public String LaunchBrowser(Browser browser){

        String drvUID = "";

        try{

            switch (browser){

                case FIREFOX:

                    System.setProperty("webdriver.gecko.driver", ".//Tools//geckodriver.exe");

                    driver = new FirefoxDriver();

                    break;

                case CHROME:
                    System.setProperty("webdriver.chrome.driver", ".//Tools//chromedriver.exe");

                    driver = new ChromeDriver();
                    break;

                default:
                    driver = new ChromeDriver();
                    break;

            }

            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
            Report("Browser "+cap.getBrowserName()+ " version " + (String)cap.getCapability("browserVersion") + " running successfully");

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return "";
        }

        return drvUID;
    }

    public void NavigateTo(String URL){

        try{
            driver.get(URL);
        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void ExitEnvironment(){

        try{
            driver.quit();

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void SwitchToWindow(String WindowTitle){

        try{
            ArrayList<String> widows = new ArrayList<String>(driver.getWindowHandles());

            for(String window : widows){

                driver.switchTo().window(window);

                if(driver.getTitle().trim().contains(WindowTitle)){
                    Report("Switch to " + WindowTitle + " succeed");
                    break;
                }

            }

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }
    }

    public void CloseBrowser(){

        try{
            if(driver != null)
            {
                driver.quit();
                Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
                Report("Browser "+cap.getBrowserName()+ " version " + (String)cap.getCapability("browserVersion") + " closed successfully");
            }else{
                Report("Browser driver is null");
            }

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void Await(int Seconds){

        try{
            Thread.sleep(Seconds * 1000);
        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public static void Report(String Message){
        System.out.println(Message);
    }

    public static void Report(String Message, MessageColor Color){

        switch(Color){

            case RED:
                System.out.println(ANSI_RED + Message + ANSI_RESET);
                assertTrue(false, "");
                break;

            case BLACK:
                System.out.println(ANSI_BLACK + Message + ANSI_RESET);
                break;

            case GREEN:
                System.out.println(ANSI_GREEN + Message + ANSI_RESET);
                break;
        }

    }

    public WebDriver getDriver(){

        try{
            return driver;

        } catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return null;
        }

    }

    public WebDriverWait getDriverWait(){

        try{
            return wait;

        } catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return null;
        }

    }

    public String executeJavaScript(String scriptText) {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String response = (String) jse.executeScript(scriptText);

        return response;
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locatorKey));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementVisible(String cssLocator) {
        return driver.findElement(By.cssSelector(cssLocator)).isDisplayed();
    }

    public void await(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
            Report("Waiting for " +seconds+ " seconds");
        } catch (Exception ex) {
            Report(ex.getMessage(), MessageColor.RED);
        }
    }

}
