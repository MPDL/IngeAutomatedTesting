package main.java.pages.search;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;
import main.java.pages.tools.rest.RestExamplePage;

public class SearchResultsPage extends BasePage {

	@FindBy(xpath = "//a[contains(@id, 'lnkRestServiceExamplePage')]")
	private WebElement queryInRestLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lblExportOptions')]")
	private WebElement exportLink;
	
	@FindBy(xpath = "//select[contains(@id, 'selEXPORTFORMAT_OPTIONS')]")
	private WebElement exportFormatDropdown;
	
	@FindBy(className = "allCheckBox")
	private WebElement allCheckBox;
	
	@FindBy(xpath = "//a[contains(@id, 'btnExportDownload')]")
	private WebElement downloadLink;
	
	private List<WebElement> searchResults;
	
	public SearchResultsPage(WebDriver driver) {
		super(driver);
		
		searchResults = driver.findElements(By.className("listItem"));
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage openFirstResult() {
		if (getResultCount() == 0)
			throw new NoSuchElementException("There are no search results.");
		
		WebElement titleLink = searchResults.get(0).findElement(By.xpath("//a[contains(@id, 'shortTitle')]"));
		titleLink.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public List<WebElement> getResults() {
		return searchResults;
	}
	
	public int getResultCount() {
		return searchResults.size();
	}
	
	public boolean allResultsReleased() {
		int serarchResultsCount = searchResults.size();
		for (int i = 0; i < serarchResultsCount; i++) {
			WebElement result = searchResults.get(i);
			String uploadInfo = result.findElement(By.xpath("//label[contains(@id, 'itemList:iterCurrentPartList:" + i + ":lblItemState')]")).getText();
			if (!uploadInfo.contains("Released") && !uploadInfo.contains("Freigegeben"))
				return false;
		}
		return true;
	}
	
	public RestExamplePage insertQueryREST() {
		String firstHandle = driver.getWindowHandle();
		queryInRestLink.click();

		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		return PageFactory.initElements(driver, RestExamplePage.class);
	}
	
	public SearchResultsPage goToExport() {
		exportLink.click();
		
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	public boolean resultExportPossible(String format, String fileFormat) {
		Select exportFormat = new Select(exportFormatDropdown);
		exportFormat.selectByValue(format);
		
		if (fileFormat != null) {
			WebElement fileFormatDropdown = driver.findElement(By.xpath("//select[contains(@id, 'selFILEFORMAT_OPTIONS')]"));
			Select fileFormatSelect = new Select(fileFormatDropdown);
			fileFormatSelect.selectByValue(fileFormat);
		}
		
		selectAllItems();
		
		return downloadLink.isDisplayed() && downloadLink.isEnabled();
	}
	
	private void selectAllItems() {
		allCheckBox.click();
	}
}
