package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * Sets up the test suite in accordance to browser
 * specified in the .xml file of the suite.
 * @param browserType name of the browser in low-caps
 * @author apetrova
 *
 */
public class TestSuiteInitialisation {

	private static WebDriver driver;
	private static Logger log4j = LogManager.getLogger(TestSuiteInitialisation.class.getName());
	private static Properties properties;
	
	private final String startPageURL = "http://qa-pubman.mpdl.mpg.de/pubman/faces/HomePage.jsp";
	private final String propertiesFileName = "pubmanTestData";
	
	@Parameters({"browserType"})
	@BeforeSuite
	public void setUpSuite(String browserType) throws FileNotFoundException {
		initialiseDriver(browserType);
		loadProperties();
		
		driver.navigate().to(startPageURL);
		log4j.info("QA PubMan start page loaded.");
	}
	
	private void initialiseDriver(String browserType) {
		switch(browserType) {
			case "firefox":
				log4j.info("Browser type identified as Firefox.");
				driver = initialiseFirefoxDriver();
				break;
			case "chrome":
				log4j.info("Browser type indentified as Chrome.");
				driver = initialiseChromeDriver();
				break;
			default:
				log4j.warn("Browser type was not identified. Launching Firefox instead...");
				driver = initialiseFirefoxDriver();
		}
		driver.manage().window().maximize();
		log4j.info("Window maximised.");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	private FirefoxDriver initialiseFirefoxDriver() {
		log4j.info("Launching Firefox browser...");
		return new FirefoxDriver();
	}
	
	/*
	* TODO provide Jenkins with ChromeDriver
	*/
	private ChromeDriver initialiseChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", "/" + System.getenv("chromeDriver"));
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setCapability(ChromeOptions.CAPABILITY, options);
		log4j.info("Launching Chrome browser...");
		return new ChromeDriver();
	}
	
	private void loadProperties() throws FileNotFoundException {
		String propertiesEnvName = "/" + System.getenv(propertiesFileName);
		properties = new Properties();
		FileInputStream input = new FileInputStream(new File(propertiesEnvName));

		try {	
			properties.load(input);
		} catch (IOException e) {
			log4j.error("Properties file with login data couldn't be loaded");
			e.printStackTrace();
		} finally {
        	if(input != null) {
        		try {
        			input.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static Logger getLogger() {
		return log4j;
	}
	
	public static Properties getProperties() {
		return properties;
	}
	
	@AfterSuite
	public void tearDownSuite() {
		log4j.info("Quitting driver...");
		driver.quit();
	}
}
