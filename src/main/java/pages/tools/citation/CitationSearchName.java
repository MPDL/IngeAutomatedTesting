package main.java.pages.tools.citation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CitationSearchName {

	private WebDriver driver;
	
	@FindBy(id = "styleNameQuery")
	private WebElement styleQuery;
	
	@FindBy(id = "styleNameInput")
	private WebElement styleNameInput;
	
	@FindBy(id = "searchResults")
	private WebElement searchResults;
	
	public CitationSearchName(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public CitationSearchName searchStyle(String name) {
		styleQuery.sendKeys(name);
		styleNameInput.findElement(By.tagName("button")).click();
		
		return PageFactory.initElements(driver, CitationSearchName.class);
	}
	
	public WebElement getFirstResult() {
		List<WebElement> results = searchResults.findElements(By.tagName("table"));
		return results.get(0);
	}
	
	public StyleInfoPage openStylePage(WebElement style) {
		WebElement stylePageLink = style.findElement(By.className("style-title"));
		stylePageLink.click();
		
		return PageFactory.initElements(driver, StyleInfoPage.class);
	}
}
