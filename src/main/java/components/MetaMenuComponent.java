package main.java.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;

/**
 * Menu containing login, contact, disclaimer, help, and language choice
 * @author apetrova
 *
 */
public class MetaMenuComponent {

	protected WebDriver driver;
	
	@FindBy(xpath = "//input[contains(@id, 'lnkLogout')]")
	private WebElement loginButton;
	
	public MetaMenuComponent(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public LoginPage goToLoginPage() {
		loginButton.click();
		
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public StartPage logout() {
		loginButton.click();
		
		return PageFactory.initElements(driver, StartPage.class);
	}
	
}
