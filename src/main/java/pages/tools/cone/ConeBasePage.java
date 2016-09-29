package main.java.pages.tools.cone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.LoginPage;

/**
 * Components and actions common to every CoNE page
 * @author apetrova
 *
 */
public class ConeBasePage {

	protected WebDriver driver;
	
	private static ConeBasePage instance;
	
	@FindBy(id = "headline")
	private WebElement headline;
	
	@FindBy(css = "#metaMenuSkipLinkAnchor a:nth-of-type(2)")
	private WebElement loginLink;
	
	@FindBy(css = "#mainMenuSkipLinkAnchor a:nth-of-type(2)")
	private WebElement searchLink;
	
	public ConeBasePage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
	}
	
	public static ConeBasePage getInstance(WebDriver driver) {
		if (instance == null)
			instance = new ConeBasePage(driver);
		return instance;
	}
	
	public String getHeadline() {
		return headline.getText();
	}
	
	public ConeHomePage login(String username, String password) {
		return goToLoginPage().loginCone(username, password);
	}
	
	private LoginPage goToLoginPage() {
		loginLink.click();
		
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public ConeBasePage logout() {
		loginLink.click();
		
		return PageFactory.initElements(driver, ConeBasePage.class);
	}
	
	public ConeHomePage goToHomePage(ConeHomePage coneHomePage) {
		return PageFactory.initElements(driver, ConeHomePage.class);
	}
	
	public ConeSearchPage goToSearch() {
		searchLink.click();
		
		return PageFactory.initElements(driver, ConeSearchPage.class);
	}
}
