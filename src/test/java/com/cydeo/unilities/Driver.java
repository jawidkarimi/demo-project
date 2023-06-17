package com.cydeo.unilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class Driver {


    private Driver(){}

    private static WebDriver driver; // default value = null
    private static String browserType;

    public static WebDriver getDriver(){

        if(driver == null) {
            if(System.getProperty("BROWSER")==null){
            browserType = ConfigurationReader.getProperty("browser");
            } else {
                browserType=System.getProperty("BROWSER");
            }
            System.out.println("Remote: " + browserType);


            switch (browserType){

                case "remote-chrome":
                    try {
                        //assign your grid server address
                        String gridAddress = "52.53.148.211";
                        URL url = new URL("http://"+gridAddress+":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("chrome");
                        driver = new RemoteWebDriver(url,desiredCapabilities);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case "remote-firefox":
                    try {
                        //assign your grid server address
                        String gridAddress = "52.53.148.211";
                        URL url = new URL("http://"+gridAddress+":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("firefox");
                        driver = new RemoteWebDriver(url,desiredCapabilities);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(options);
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
            }
        }
        return driver;
    }

    public static void closeDriver(){
        if(driver!=null){
            driver.quit();
            driver = null;
        }
    }

}
