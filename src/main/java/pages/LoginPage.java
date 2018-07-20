package main.java.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.tools.cone.ConeHomePage;

public class LoginPage {

	@FindBy(xpath = "//input[contains(@id, 'inputUsername')]")
	private WebElement usernameInputBox;
	
	@FindBy(xpath = "//input[contains(@id, 'inputSecretPassword')]")
	private WebElement passwordInputBox;
	
	@FindBy(xpath = "//input[contains(@id, 'lnkLogin')]")
	private WebElement submitButton;
	
	protected WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver,  this);
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
	
	// PM-01
	public CombinedHomePage loginAsCombinedUser(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, CombinedHomePage.class);
	}
	
	public ConeHomePage loginCone(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, ConeHomePage.class);
	}
	
	private void login(String username, String password) {
		usernameInputBox.sendKeys(username);
		passwordInputBox.sendKeys(password);
		submitButton.click();
	}
}
