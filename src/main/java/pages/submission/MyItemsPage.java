package main.java.pages.submission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import main.java.pages.BasePage;

public class MyItemsPage extends BasePage {

	@FindBy(id = "j_idt103:itemList:iterCurrentPartList:0:selItemSelect")
	private WebElement firstItemCheckBox;
	
	@FindBy(id = "j_idt103:lnkChangeSubmenuToExport")
	private WebElement exportLink;
	
	@FindBy(id = "j_idt103:selExportFormatName")
	private WebElement formatDropdown;
	
	@FindBy(id = "j_idt103:btnExportDownload")
	private WebElement downloadLink;
	
	public MyItemsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
	
	public ViewItemPage openItemByTitle(String itemTitle) {
		WebElement itemLink = driver.findElement(By.cssSelector("a[title='" + itemTitle + "']"));
		itemLink.click();
			
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public MyItemsPage deleteItemByTitle(String itemTitle) {
		ViewItemPage viewItemPage = openItemByTitle(itemTitle);
		return viewItemPage.deleteItem();
	}
	
	public ViewItemPage discardItemByTitle(String itemTitle) {
		ViewItemPage viewItemPage = openItemByTitle(itemTitle);
		return viewItemPage.discardItem();
	}
	
	public boolean exportItem(String itemTitle, String value) {
		try {
			exportLink.click();
			PageFactory.initElements(driver, this);
		}
		catch (NoSuchElementException exc) {
			// export submenu already open: do nothing
			// TODO temporary solution, design a fix
		}
			
		Select formatSelect = new Select(formatDropdown);
		formatSelect.selectByValue(value);
		
		if (!firstItemCheckBox.isSelected())
			firstItemCheckBox.click();
		
		return downloadLink.isDisplayed() && downloadLink.isEnabled();
	}
	
	public boolean hasItems() {
		List<WebElement> items = driver.findElements(By.className("listItem"));
		return items.size() > 0;
	}
}
