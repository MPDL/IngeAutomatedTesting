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

	@FindBy(name = "j_username")
	private WebElement usernameInputBox;
	
	@FindBy(name = "j_password")
	private WebElement passwordInputBox;
	
	@FindBy(name = "Abschicken")
	private WebElement submitButton;
	
	protected WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver,  this);
	}
	
	public DepositorHomePage loginAsDepositor(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, DepositorHomePage.class);
	}
	
	public ModeratorHomePage loginAsModerator(String username, String password) {
		login(username, password);
		return PageFactory.initElements(driver, ModeratorHomePage.class);
	}
	
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
