package main.java.pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.transition.AcceptItemPage;
import main.java.pages.submission.transition.FinaliseSubmissionPage;

public class EditItemPage extends BasePage {

	@FindBy(id = "form1:inputTitleText")
	private WebElement titleBox;
	
	@FindBy(id = "form1:j_idt497:0:inpcreator_persons_person_family_name_optional")
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
	
	public ViewItemPage addAuthor(String additionalAuthor) {
		int authorCount = driver.findElements(By.xpath("//input[contains(@id, 'j_idt497') and contains(@id, 'inpcreator_persons_person_family_name')]")).size();
		WebElement addSecondAuthor = driver.findElement(By.id("form1:j_idt497:" + (authorCount - 1) + ":btnAddCreator"));
		addSecondAuthor.click();
		WebElement secondFamilyNameBox = driver.findElement(By.id("form1:j_idt497:" + authorCount + ":inpcreator_persons_person_family_name_optional"));
		secondFamilyNameBox.sendKeys(additionalAuthor);
		driver.findElement(By.cssSelector(".ac_results>li")).click();
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public ViewItemPage modifyItem() {
		editDescription(" (modified by moderator)");
		acceptButton.click();
		
		return new AcceptItemPage(driver).acceptItem();
	}
	
	public ViewItemPage modifyTitle(String newTitle) {
		titleBox.clear();
		titleBox.sendKeys(newTitle);
		releaseButton.click();
		
		return new FinaliseSubmissionPage(driver).releaseSubmission();
	}
	
	public ViewItemPage modifyAuthor(String newAuthor) {
		personFamilyNameBox.clear();
		personFamilyNameBox.sendKeys(newAuthor);
		driver.findElement(By.cssSelector(".ac_results>li")).click();
		releaseButton.click();
		
		return new FinaliseSubmissionPage(driver).releaseSubmission();
	}
	
	private void editDescription(String descriptionEdit) {
		descriptionBox.sendKeys(descriptionEdit);
	}
	
	
}
