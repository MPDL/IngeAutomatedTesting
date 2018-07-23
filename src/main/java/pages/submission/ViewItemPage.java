package main.java.pages.submission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.transition.DiscardItemPage;
import main.java.pages.submission.transition.FinaliseSubmissionPage;
import main.java.pages.submission.transition.ReworkItemPage;
import main.java.pages.submission.transition.SubmitItemPage;
import test.java.base.ItemStatus;

public class ViewItemPage extends BasePage {

	@FindBy(css = ".itemHeadline>b")
	private WebElement itemTitle;
	
	@FindBy(css = ".labelLine")
	private List<WebElement> labels;
	
	@FindBy(css = ".xTiny_marginLExcl")
	private List<WebElement> values;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkRelease')]")
	private WebElement releaseItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkSubmit')]")
	private WebElement submitItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkEdit')]")
	private WebElement editItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkRevise')]")
	private WebElement reviseItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkDelete')]")
	private WebElement deleteItemLink;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkWithdraw')]")
	private WebElement discardItemLink;
	
	private Map<String, String> labelMap;
	
	public ViewItemPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	private void initLabelMap() {
		labelMap = new HashMap<>();
		int labelCount = labels.size();
		for (int i = 0; i < labelCount; i++) {
			String label = labels.get(i).getText().trim();
			String value = values.get(i).getText().trim();
			if ( !labelMap.containsKey(label))
			{
				//TODO: Handle equal labels (e.g. Details and Source Pages) appropriate
			  labelMap.put(label, value);
			}
		}
	}
	
	public String getItemTitle() {
		return itemTitle.getText().trim();
	}
	
	public ItemStatus getItemStatus() {
  	    try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        WebElement itemStatus = driver.findElement(By.className("statusIcon"));
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
	
	// PM-60, PM-134, PM-135
	public ViewItemPage releaseItem() {
		releaseItemLink.click();
		
		return new FinaliseSubmissionPage(driver).releaseSubmission();
	}
	
	// PM-130, PM-133, PM-144
	public ViewItemPage submitItem() {
		submitItemLink.click();
		
		return new SubmitItemPage(driver).submitItem();
	}
	
	public MyItemsPage deleteItem() {
		deleteItemLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	// PM-137
	public ViewItemPage discardItem() {
		discardItemLink.click();
		
		return new DiscardItemPage(driver).discardItem();
	}
	
	// PM-131, PM-135
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
	
	// PM-132
	public ViewItemPage sendBackForRework() {
		reviseItemLink.click();
		
		return new ReworkItemPage(driver).sendBackForRework();
	}
	
	/**
	 * Get the value of the field with the given label. <br>
	 * Internally creates and searches a label map if the getLabel method is called for the first time.
	 * 
	 * @param label The label of the field to get the value from
	 * @return The value of the associated field
	 */
	public String getValue(String label) {
		if(labelMap == null) {
			initLabelMap();
		}
		
		String value = labelMap.get(label);
		if (value == null) {
			throw new NoSuchElementException("No label present: '" + label + "'");
		}
		return value;
	}
	
}
