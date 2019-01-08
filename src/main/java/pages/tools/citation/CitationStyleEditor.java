package main.java.pages.tools.citation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CitationStyleEditor {

	private WebDriver driver;
	private WebDriverWait wait;
	
	private static CitationStyleEditor instance;
	
	@FindBy(css = "div .lead")
	private WebElement csHeadline;
	
	@FindBy(id = "nav_searchByName")
	private WebElement searchByNameLink;
	
	@FindBy(id = "nav_searchByExample")
	private WebElement searchExampleLink;
	
	public CitationStyleEditor(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
		
		PageFactory.initElements(driver, this);
	}
	
	public static CitationStyleEditor getInstance(WebDriver driver) {
		if (instance == null)
			instance = new CitationStyleEditor(driver);
		return instance;
	}
	
	public CitationSearchExample searchByExample() {
		searchExampleLink.click();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//title[text()='CSL Search by Example']")));
		
		return PageFactory.initElements(driver, CitationSearchExample.class);
	}
	
	public CitationSearchName searchStyle(String styleName) {
		return searchByName().searchStyle(styleName);
	}
	
	private CitationSearchName searchByName() {
		searchByNameLink.click();
		
		return PageFactory.initElements(driver, CitationSearchName.class);
	}
	
	public String getHeadline() {
		return csHeadline.getText();
	}
}
