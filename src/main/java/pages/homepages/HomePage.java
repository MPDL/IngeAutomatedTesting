package main.java.pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.StartPage;
import main.java.pages.search.AdministrativeSearchPage;

public class HomePage extends BasePage {

	@FindBy(xpath = "//a[contains(@id, 'lnkAccountUserName')]")
	private WebElement loggedInUsername;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkAdminSearch')]")
	private WebElement administrativeSearchLink;
	
	public HomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	// PM-91, PM-92
	public StartPage logout() {
		return metaMenuComponent.logout();
	}
	
	public String getUsername() {
		return loggedInUsername.getText();
	}
	
	public AdministrativeSearchPage goToAdministrativeSearchPage() {
		administrativeSearchLink.click();
		return PageFactory.initElements(driver, AdministrativeSearchPage.class);
	}
	
	// homepages are set at the start page
	@Override
	public StartPage goToStartPage() {
		return PageFactory.initElements(driver, StartPage.class);
	}
}
