package main.java.components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import main.java.pages.StartPage;

/**
 * Menu containing: login/logout, contact, disclaimer, privacy policy, guide,
 * help, and language choice
 * 
 * @author apetrova
 *
 */
//TODO: Merge with LoginPage
public class MetaMenuComponent {

	protected WebDriver driver;
	protected final Logger log4j = Logger.getLogger(MetaMenuComponent.class);

	@FindBy(xpath = "//input[contains(@id, 'lnkLogin')]")
	private WebElement loginButton;

	@FindBy(xpath = "//input[contains(@id, 'lnkLogout')]")
	private WebElement logoutButton;

	public MetaMenuComponent(WebDriver driver) {
		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	// PM-91, PM-92
	public StartPage logout() {
		if (driver.findElements(By.xpath("//input[contains(@id, 'lnkLogin')]")).isEmpty()) {
			WebElement logoutButton = driver.findElement(By.xpath("//input[contains(@id, 'lnkLogout')]"));
			logoutButton.click();

			new WebDriverWait(driver, 20).until(ExpectedConditions.stalenessOf(logoutButton));
			new WebDriverWait(driver, 20).until(webDriver -> ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState").equals("complete"));

			return PageFactory.initElements(driver, StartPage.class);
		} else {
			log4j.error("User already logged out.");
			return PageFactory.initElements(driver, StartPage.class);
		}
	}

}
