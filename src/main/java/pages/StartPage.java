package main.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;

public class StartPage extends BasePage {

	@FindBy(xpath = "//input[contains(@id, 'inputUsername')]")
	private WebElement usernameInputBox;
	
	@FindBy(xpath = "//input[contains(@id, 'inputSecretPassword')]")
	private WebElement passwordInputBox;
	
	@FindBy(xpath = "//input[contains(@id, 'lnkLogin')]")
	private WebElement submitButton;
	
	@FindBy(xpath = "//select[contains(@id, 'selSelectLocale')]")
	private WebElement languageDropdown;
	
	public StartPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
		switchToEnglish();
	}
	
	public String getNameOfMostRecentItem() {
		WebElement recentItems = driver.findElement(By.className("gfc-results"));
		WebElement mostRecentItem = recentItems.findElement(By.className("gfc-result"));
		String mostRecentItemTitle = mostRecentItem.findElement(By.className("gf-title")).getText();
		
		return mostRecentItemTitle;
	}
	
	// PM-02
	public DepositorHomePage loginAsDepositor(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, DepositorHomePage.class);
	}
	
	// PM-03
	public ModeratorHomePage loginAsModerator(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, ModeratorHomePage.class);
	}
	
	public CombinedHomePage loginAsCombinedUser(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, CombinedHomePage.class);
	}
	
	private void login(String username, String password) {
		usernameInputBox.sendKeys(username);
		passwordInputBox.sendKeys(password);
		submitButton.click();
	}
	
	private void switchToEnglish() {
		Select languageSelect = new Select(languageDropdown);
		languageSelect.selectByValue("en");
	}
	
}
