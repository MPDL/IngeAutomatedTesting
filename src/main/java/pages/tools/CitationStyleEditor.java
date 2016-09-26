package main.java.pages.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CitationStyleEditor {

	@FindBy(id = "nav_searchByExample")
	private WebElement searchExampleLink;
	
	public CitationStyleEditor(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void searchByExample() {
		searchExampleLink.click();
	}
}
