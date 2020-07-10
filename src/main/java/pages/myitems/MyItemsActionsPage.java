package main.java.pages.myitems;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class MyItemsActionsPage extends BasePage {

	// TODO: Move MyItemsPage also in this package
	// TODO: inherit from father class MyItems!?

	@FindBy(xpath = "//a[contains(@id, ':lnkList_lblAddSelectionToBatch')]")
	private WebElement add_Selection_To_Batch_Processing_Link;

	public MyItemsActionsPage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

	/**
	 * Select the items by title and adds them to the batch processing.
	 * 
	 * Only selects elements on the first page!
	 * 
	 * @param itemTitles
	 */
	public MyItemsActionsPage selectAndAddToBatchProcessing(String... itemTitles) {
		for (String itemTitle : itemTitles) {
			WebElement itemListElement = driver
					.findElement(By.xpath("//a[contains(@id, 'lnkList_shortTitle') and @title='" + itemTitle
							+ "']/ancestor::li[contains(@class, 'listItem')]"));
			WebElement selectItemCheckbox = itemListElement
					.findElement(By.xpath(".//input[contains(@id, ':selItemSelect')]"));
			selectItemCheckbox.click();
		}

		this.add_Selection_To_Batch_Processing_Link.click();

		return new MyItemsActionsPage(driver);
	}

}
