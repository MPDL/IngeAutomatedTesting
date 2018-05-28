package main.java.pages.tools.citation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CitationSearchExample {

	private WebDriver driver;
	
	@FindBy(id = "styleFormatInputControls")
	private WebElement styleFormat;
	
	@FindBy(id = "searchButton")
	private WebElement searchButton;
	
	private List<WebElement> searchResults;
	
	public CitationSearchExample(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getSearchResults() {
		WebElement searchButton = styleFormat.findElement(By.id("searchButton"));
		// sleep needed to ensure correct script execution
		try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
		WebElement allResults = driver.findElement(By.id("searchResults"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
		searchResults = allResults.findElements(By.tagName("table"));
		
		return searchResults;
	}
	
	public VisualEditor editFirstResult() {
		WebElement firstResult = searchResults.get(0);
		firstResult.findElement(By.className("editStyle")).click();
		
		return PageFactory.initElements(driver, VisualEditor.class);
	}
}
