package main.java.pages.submission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import test.java.base.ItemStatus;
import main.java.pages.BasePage;
import main.java.pages.submission.transition.AcceptItemPage;
import main.java.pages.submission.transition.DiscardItemPage;
import main.java.pages.submission.transition.FinaliseSubmissionPage;
import main.java.pages.submission.transition.ReworkItemPage;
import main.java.pages.submission.transition.SubmitItemPage;

public class ViewItemPage extends BasePage {

	@FindBy(css = ".itemHeadline>b")
	private WebElement itemTitle;
	
	@FindBy(className = "statusIcon")
	private WebElement itemStatus;
	
	@FindBy(css = ".labelLine")
	private List<WebElement> labels;
	
	@FindBy(css = ".xTiny_marginLExcl")
	private List<WebElement> values;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkRelease')]")
	private WebElement releaseItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkSubmit')]")
	private WebElement submitItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkAccept')]")
	private WebElement acceptItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkEdit')]")
	private WebElement editItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkRevise')]")
	private WebElement reviseItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkModify')]")
	private WebElement modifyItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkDelete')]")
	private WebElement deleteItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkWithdraw')]")
	private WebElement discardItemLink;
	
	private Map<String, String> labelMap;
	
	public ViewItemPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		initLabelMap();
	}
	
	private void initLabelMap() {
		labelMap = new HashMap<>();
		int labelCount = labels.size();
		for (int i = 0; i < labelCount; i++) {
			String label = labels.get(i).getText().trim();
			String value = values.get(i).getText().trim();
			labelMap.put(label, value);
		}
	}
	
	public String getItemTitle() {
		return itemTitle.getText().trim();
	}
	
	public ItemStatus getItemStatus() {
		String classNames = itemStatus.getAttribute("class");
		if (classNames.contains("pendingItem"))
			return ItemStatus.PENDING;
		if (classNames.contains("submittedItem"))
			return ItemStatus.SUBMITTED;
		if (classNames.contains("releasedItem"))
			return ItemStatus.RELEASED;
		if (classNames.contains("inRevisionItem"))
			return ItemStatus.IN_REWORK;
		return ItemStatus.DISCARDED;
	}
	
	public ViewItemPage releaseItem() {
		releaseItemLink.click();
		
		return new FinaliseSubmissionPage(driver).releaseSubmission();
	}
	
	public ViewItemPage submitItem() {
		submitItemLink.click();
		
		return new SubmitItemPage(driver).submitItem();
	}
	
	public MyItemsPage deleteItem() {
		deleteItemLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	public ViewItemPage discardItem() {
		discardItemLink.click();
		
		return new DiscardItemPage(driver).discardItem();
	}
	
	public ViewItemPage acceptItem() {
		acceptItemLink.click();
		
		return new AcceptItemPage(driver).acceptItem();
	}
	
	public ViewItemPage editItem() {
		editItemLink.click();
		
		return new EditItemPage(driver).editItem();
	}
	
	public ViewItemPage editTitle(String newTitle) {
		editItemLink.click();
		
		return new EditItemPage(driver).editTitle(newTitle);
	}
	
	public ViewItemPage editAuthor(String newAuthor) {
		editItemLink.click();
		
		return new EditItemPage(driver).editAuthor(newAuthor);
	}
	
	public ViewItemPage addAuthor(String secondAuthor) {
		editItemLink.click();
		
		return new EditItemPage(driver).addAuthor(secondAuthor);
	}
	
	public ViewItemPage sendBackForRework() {
		reviseItemLink.click();
		
		return new ReworkItemPage(driver).sendBackForRework();
	}
	
	public ViewItemPage modifyItem() {
		modifyItemLink.click();
		
		return new EditItemPage(driver).modifyItem();
	}
	
	public ViewItemPage modifyTitle(String newTitle) {
		modifyItemLink.click();
		
		return new EditItemPage(driver).modifyTitle(newTitle);
	}
	
	public ViewItemPage modifyAuthor(String newAuthor) {
		modifyItemLink.click();
		
		return new EditItemPage(driver).modifyAuthor(newAuthor);
	}
	
	public ViewItemPage modifyAddAuthor(String newAuthor) {
		modifyItemLink.click();
		
		return new EditItemPage(driver).addAuthor(newAuthor);
	}
	
	/**
	 * assumes the label map has been initialised
	 */
	public String getLabel(String label) {
		/*int labelCount = labels.size();
		for (int i = 0; i < labelCount; i++) {
			WebElement labelElement = labels.get(i);
			if (labelElement.getText().trim().equals(label)) {
				return values.get(i).getText().trim();
			}
		}*/
		String value = labelMap.get(label);
		if (value == null) {
			throw new NoSuchElementException("No label present: '" + label + "'");
		}
		return value;
	}
	
}
