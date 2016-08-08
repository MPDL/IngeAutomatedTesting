package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.LoginPage;

/**
 * Menu containing login, contact, disclaimer, help, and language choice
 * @author apetrova
 *
 */
public class MetaMenuComponent {

	protected WebDriver driver;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkLoginLogout')]")
	private WebElement loginButton;
	
	public MetaMenuComponent(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public LoginPage goToLoginPage() {
		loginButton.click();
		
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
}
