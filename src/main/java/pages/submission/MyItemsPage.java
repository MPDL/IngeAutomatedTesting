package main.java.pages.submission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import main.java.pages.BasePage;
import main.java.pages.myitems.MyItemsActionsPage;

public class MyItemsPage extends BasePage {

	@FindBy(xpath = "//input[contains(@id, 'itemList:iterCurrentPartList:0:selItemSelect')]")
	private WebElement firstItemCheckBox;

	@FindBy(xpath = "//a[contains(@id, 'lnkChangeSubmenuToExport')]")
	private WebElement exportLink;

	@FindBy(xpath = "//select[contains(@id, 'selEXPORTFORMAT')]")
	private WebElement formatDropdown;

	@FindBy(id = "j_idt103:itemList:extSelectTop")
	private WebElement itemCountDropdown;

	@FindBy(xpath = "//a[contains(@id, 'btnExportDownload')]")
	private WebElement downloadLink;

	@FindBy(xpath = "//a[contains(@id, ':lnkList_lblSelected')]")
	private WebElement actionsLink;

	public MyItemsPage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

	public ViewItemPage openItemByTitle(String itemTitle) {
		WebElement itemLink = driver.findElement(By.cssSelector("a[title='" + itemTitle + "']"));
		itemLink.click();
		wait.until(ExpectedConditions.stalenessOf(itemLink));

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
		List<WebElement> exportLinks = driver.findElements(By.xpath("//a[contains(@id, 'lnkChangeSubmenuToExport')]"));
		if (exportLinks.size() > 0) {
			WebElement exportLink = exportLinks.get(0);
			exportLink.click();
			wait.until(ExpectedConditions.stalenessOf(exportLink));
			PageFactory.initElements(driver, this);
		}
		// else: export submenu already open -> do nothing

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

	public MyItemsPage show50Entries() {
		itemCountDropdown.click();
		Select itemCountSelect = new Select(itemCountDropdown);
		itemCountSelect.selectByValue("50");

		return PageFactory.initElements(driver, MyItemsPage.class);
	}

	public MyItemsActionsPage openActionsView() {
		this.actionsLink.click();

		return new MyItemsActionsPage(driver);
	}

}
