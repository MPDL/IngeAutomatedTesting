package main.java.pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import main.java.pages.BasePage;

public class EditItemPage extends BasePage {

	@FindBy(id = "form1:inputTitleText")
	private WebElement titleBox;
	
	@FindBy(id = "iterCreatorOrganisationAuthors")
	private WebElement authors;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpcreator_persons_person_family_name_optional')]")
	private WebElement personFamilyNameBox;
	
	@FindBy(id = "form1:fileUploads:0:inpExtraFileDescription")
	private WebElement descriptionBox;
	
	@FindBy(id = "form1:lnkSave")
	private WebElement saveButton;
	
	@FindBy(id = "form1:lnkAccept")
	private WebElement acceptButton;
	
	@FindBy(id = "form1:lnkReleaseReleasedItem")
	private WebElement releaseButton;
	
	public EditItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage editItem() {
  	    try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		editDescription(" (revised by moderator)");
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public ViewItemPage editTitle(String newTitle) {
		titleBox.clear();
		titleBox.sendKeys(newTitle);
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public ViewItemPage editAuthor(String newAuthor) {
		personFamilyNameBox.clear();
		personFamilyNameBox.sendKeys(newAuthor);
		driver.findElement(By.cssSelector(".ac_results>li")).click();
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	/**
	 * Add another author at the end of the author list, by clicking the bottommost add-author-button. <br>
	 * Only the Family name is added to the next author.
	 * 
	 * @param nextAuthorFamilyName familyName of the author to add
	 * @return the ViewItemPage
	 */
	public ViewItemPage addAuthor(String nextAuthorFamilyName) {		
		int creatorCount = authors.findElements(By.xpath(".//select[contains(@id, 'selCreatorRoleString')]")).size();
		
		WebElement addNextAuthor = authors.findElement(By.xpath(".//input[@id='form1:j_idt512:" + (creatorCount-1) + ":btnAddCreator']"));
		wait.until(ExpectedConditions.elementToBeClickable(addNextAuthor));
		addNextAuthor.click();
		
		String nextFamilyNameBoxXPath = "//input[@id='form1:j_idt512:" + creatorCount + ":inpcreator_persons_person_family_name_optional']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextFamilyNameBoxXPath)));
		WebElement nextFamilyNameBox = authors.findElement(By.xpath("." + nextFamilyNameBoxXPath));
		nextFamilyNameBox.sendKeys(nextAuthorFamilyName);
		
		//If the first option of the suggestion-menu should be selected, use the following code:
		//driver.findElement(By.cssSelector(".ac_results>li")).click();
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
		
	private void editDescription(String descriptionEdit) {
		descriptionBox.sendKeys(descriptionEdit);
	}
	
	
}
