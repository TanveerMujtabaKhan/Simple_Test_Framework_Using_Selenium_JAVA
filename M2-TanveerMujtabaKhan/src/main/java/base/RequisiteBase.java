package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RequisiteBase {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public Properties prop;
    public String mondayURL;
    public static WebDriverWait wait;
    public static PropertiesConfiguration configuration;

    public WebDriver getDriver() {
        return driver.get();
    }

    /****************************************
     * Method To Launch The Browser and navigate to url Before Each Test With @Before Test Annotation
     *****************************************/
    @BeforeClass
    public WebDriver LaunchBrowserAndnavigateToUrl() {
        prop = new Properties();
        String path = System.getProperty("user.dir") + "//src//main//java//configurations//config.properties";
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            prop.load(fis);
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver(options));
            getDriver().manage().window().maximize();
            getDriver().manage().deleteAllCookies();
            mondayURL = prop.getProperty("mondayURL");
            System.out.println(mondayURL);
            getDriver().get(mondayURL);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return getDriver();
    }

    /****************************************
     * Method To Wait For PageLoad if All Elements Present
     *****************************************/
    public WebElement explicitPresenceOfElement(By elementBy, long timeout) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));

    }

    /****************************************
     * Method To Closing The Current Browser
     *****************************************/

    public void closeBrowser() {

        getDriver().close();
    }

    /****************************************
     * Method To Closing All Browser
     *****************************************/
    @AfterMethod
    public void closeAllBrowser() {

        getDriver().quit();

    }

    public String credentials(String type) {
        prop = new Properties();
        String credentialType = "";
        if (type.equalsIgnoreCase("email")) {
            credentialType = prop.getProperty("userName");
        } else if (type.equalsIgnoreCase("password")) {
            credentialType = prop.getProperty("password");
        }

        return credentialType;
    }

    public void clickElementWithJs(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
