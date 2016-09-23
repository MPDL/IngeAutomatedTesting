package test.java.base;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Superclass for all test classes. Prepares the driver instance and user data
 * @author apetrova
 *
 */
public abstract class BaseTest {

	protected WebDriver driver;
	protected Logger log4j;
	protected Properties properties;
	
	protected String testCaseID;
	protected String startPageURL = "http://qa-pubman.mpdl.mpg.de/pubman/faces/HomePage.jsp";
	
	protected String depositorName;
	protected String depositorUsername;
	protected String depositorPassword;
	
	protected String moderatorName;
	protected String moderatorUsername;
	protected String moderatorPassword;
	
	protected String modDepName;
	protected String modDepUsername;
	protected String modDepPassword;
	
	public void setup() {
		configureDriver();
		configureUsers();
		log4j.info("Testing " + this.getClass().getSimpleName());
	}
	
	private void configureDriver() {
		log4j = TestSuiteInitialisation.getLogger();
		driver = TestSuiteInitialisation.getDriver();
		if (driver == null) {
			log4j.warn("Main driver was not found: starting backup Firefox driver.");
			driver = new FirefoxDriver();
			driver.navigate().to(startPageURL);
		}
	}
	
	private void configureUsers() {
		properties = TestSuiteInitialisation.getProperties();
		
		depositorName = getPropertyValue("depositorName");
		depositorUsername = getPropertyValue("depositorUsername");
		depositorPassword = getPropertyValue("depositorPassword");
		
		moderatorName = getPropertyValue("moderatorName");
		moderatorUsername = getPropertyValue("moderatorUsername");
		moderatorPassword = getPropertyValue("moderatorPassword");
		
		modDepName = getPropertyValue("modDepName");
		modDepUsername = getPropertyValue("modDepUsername1");
		modDepPassword = getPropertyValue("modDepPassword1");
	}
	
	public String getPageHeadline() {
		return driver.getTitle();
	}
	
	public String getPropertyValue(String key) {
		return properties.getProperty(key);
	}
	
	public String getTimeStamp() {
		long time = new Date().getTime();
		Timestamp timeStamp = new Timestamp(time);
		return timeStamp.toString();
	}
	
	public final String getFilepath(String fileName) {
		fileName = "/" + fileName;
		String filepath = getClass().getResource(fileName).getPath();
		if (driver instanceof FirefoxDriver)
			filepath = "file:" + filepath;
		if (driver instanceof ChromeDriver)
			filepath = filepath.substring(1, filepath.length());
		return filepath;
	}
	
}
