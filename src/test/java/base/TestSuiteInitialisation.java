package test.java.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * Sets up the test suite in accordance to browser specified in the .xml file of
 * the suite.
 * 
 * @param browserType
 *            name of the browser in low-caps
 * @author apetrova
 *
 */
public class TestSuiteInitialisation {

	private static WebDriver driver;
	private static Logger log4j = LogManager.getLogger(TestSuiteInitialisation.class.getName());

	/** properties file with required login information for test user and admin **/
	public static final String propertiesFileName = "ingeTestData.properties";
	private static Properties properties;

	private static final boolean HEADLESS = true;

	private static final String QA_PURE_URL = "https://qa.pure.mpdl.mpg.de/pubman/faces/HomePage.jsp";
	private static final String DEV_PURE_URL = "https://dev.inge.mpdl.mpg.de/pubman/faces/HomePage.jsp";

	// The startPageURL defines on which server the selenium tests are executed!
//	private static final String startPageURL = QA_PURE_URL;
	 private static final String startPageURL = DEV_PURE_URL;

	@Parameters({ "browserType" })
	@BeforeSuite
	public void setUpSuite(String browserType) throws FileNotFoundException {
		initialiseDriver(browserType);
		loadProperties();

		driver.navigate().to(startPageURL);
		log4j.info("PubMan start page loaded.");
		log4j.info("Running Tests on: " + startPageURL);
	}

	private void initialiseDriver(String browserType) {
		switch (browserType) {
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

	private WebDriver initialiseFirefoxDriver() {
		log4j.info("Launching Firefox browser...");
		// The system property webdriver.gecko.driver must be set to the
		// webdriver-executable-file -> this is done by Maven!
		String geckoDriverSystemPropertyName = "webdriver.gecko.driver";
		String geckodriverPath = System.getProperty(geckoDriverSystemPropertyName);
		if (geckodriverPath != null) {
			log4j.info("Found system property '" + geckoDriverSystemPropertyName + "': " + geckodriverPath);
		} else {
			log4j.error("System property '" + geckoDriverSystemPropertyName + "' not found.");
		}

		FirefoxOptions options = new FirefoxOptions();
		
		// Set a different binary if another Version of Firefox should be used for the tests
//		options.setBinary("C:/Program Files/Firefox Nightly/firefox.exe");
//		options.setBinary("C:/Program Files/Firefox Developer Edition/firefox.exe");
		
		options.setCapability("marionette", true);
		options.setHeadless(HEADLESS);
		FirefoxProfile profile = initFirefoxProfile();
		options.setProfile(profile);

		WebDriver webDriver = new FirefoxDriver(options);
		Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
		
		String browserName = capabilities.getBrowserName();
		String browserVersion = capabilities.getVersion();
		log4j.info("Browser version: " + browserVersion + " (" + browserName + ")");
		
		return webDriver;
	}

	private FirefoxProfile initFirefoxProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", "./target/downloads");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
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

	private WebDriver initialiseChromeDriver() {
		log4j.info("Launching Chrome browser...");
		// The system property webdriver.chrome.driver must be set to the
		// webdriver-executable-file -> this is done by Maven!
		String chromeDriverSystemPropertyName = "webdriver.chrome.driver";
		String chromedriverPath = System.getProperty(chromeDriverSystemPropertyName);
		if (chromedriverPath != null) {
			log4j.info("Found system property '" + chromeDriverSystemPropertyName + "': " + chromedriverPath);
		} else {
			log4j.error("System property '" + chromeDriverSystemPropertyName + "' not found.");
		}

		ChromeOptions options = new ChromeOptions();
		
		options.setCapability("marionette", true);
		options.setHeadless(HEADLESS);
		options.addArguments("--window-size=1920,1200");

		// Without the two following proxy-options the tests do not run in headless mode or are very slow:
		// Set proxy-server -> 'direct://' means: Do not use a proxy for all connections
		options.addArguments("--proxy-server='direct://'");
		// Set which addresses should not be proxied -> * means: All. Do not use a proxy
		// without any exception
		options.addArguments("--proxy-bypass-list=*");

		WebDriver webDriver = new ChromeDriver(options);
		Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
		
		String browserName = capabilities.getBrowserName();
		String browserVersion = capabilities.getVersion();
		log4j.info("Browser version: " + browserVersion + " (" + browserName + ")");
		
		return webDriver;
	}

	private void loadProperties() {
		log4j.info("Reading properties file");
		properties = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);

		try {
			properties.load(input);
			log4j.info("Successfully loaded " + propertiesFileName);
		} catch (IOException e) {
			log4j.error("Properties file with login data couldn't be loaded", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log4j.error("Error closing the inputStream", e);
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
