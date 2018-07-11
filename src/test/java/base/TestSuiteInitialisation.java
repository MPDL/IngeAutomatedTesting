package test.java.base;

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
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
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
	
	private static final String startPageURL = "https://dev.inge.mpdl.mpg.de/pubman/faces/HomePage.jsp";
	private final String propertiesFileName = "ingeTestData";
	
	@Parameters({"browserType"})
	@BeforeSuite
	public void setUpSuite(String browserType) throws FileNotFoundException {
		initialiseDriver(browserType);
		loadProperties();
		
		driver.navigate().to(startPageURL);
		log4j.info("PubMan start page loaded.");
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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	private FirefoxDriver initialiseFirefoxDriver() {
		log4j.info("Launching Firefox browser...");
		System.setProperty("webdriver.gecko.driver", "/" + System.getenv("geckodriver"));
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette", true);
		
		FirefoxBinary binary = new FirefoxBinary();
		options.setBinary(binary);
		options.setHeadless(true);
		FirefoxProfile profile = initFirefoxProfile();
		options.setProfile(profile);

		return new FirefoxDriver(options);
	}
	
	private FirefoxProfile initFirefoxProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList",2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir","./target/downloads");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);  
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.closeWhenDone", true);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		profile.setPreference("pdfjs.disabled", true);
		
		return profile;
	}
	
	/*
	* TODO provide Jenkins with ChromeDriver
	*/
	private ChromeDriver initialiseChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.setBinary("/" + System.getenv("chromeDriver"));
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
        	if (input != null) {
        		try {
        			input.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }
	}
	
	public static String getStartPageURL() {
		return startPageURL;
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
