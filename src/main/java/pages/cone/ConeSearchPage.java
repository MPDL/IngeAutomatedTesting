package main.java.pages.cone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import main.java.pages.cone.journal.ViewJournalPage;
import main.java.pages.cone.person.ViewPersonPage;

public class ConeSearchPage extends ConeBasePage {

	@FindBy(name = "model")
	private WebElement typeDropdown;
	
	@FindBy(name = "searchterm")
	private WebElement searchBox;
	
	@FindBy(className = "activeButton")
	private WebElement submitButton;
	
	public ConeSearchPage(WebDriver driver) {
		super(driver);
	}
	
	public ConeBasePage searchFirstResult(String searchQuery, EntityType type) {
		Select typeSelect = new Select(typeDropdown);
		searchBox.sendKeys(searchQuery);
		switch(type) {
			case PERSON:
				typeSelect.selectByValue("persons");
				submitButton.click();
				driver.findElement(By.cssSelector("large_marginLInclclear a")).click();
				return PageFactory.initElements(driver, ViewPersonPage.class);
			case JOURNAL:
				typeSelect.selectByValue("journals");
				submitButton.click();
				driver.findElement(By.cssSelector("large_marginLInclclear a")).click();
				return PageFactory.initElements(driver, ViewJournalPage.class);
			default:
				return PageFactory.initElements(driver, ConeSearchPage.class);
		}
	}
	
}
