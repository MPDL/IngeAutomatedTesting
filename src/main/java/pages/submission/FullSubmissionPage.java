package main.java.pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import test.java.base.Genre;
import main.java.pages.BasePage;

public class FullSubmissionPage extends BasePage {

	@FindBy(id = "form1:cboGenre")
	private WebElement genreDropdown;
	
	@FindBy(id = "form1:fileUploads:0:selFileContentCategory")
	private WebElement contentCategoryDropdown;
	
	@FindBy(id = "form1:inputTitleText")
	private WebElement titleBox;
	
	@FindBy(id = "form1:fileUploads:0:inpExtraFileDescription")
	private WebElement descriptionBox;
	
	@FindBy(id = "form1:j_idt497:0:inpcreator_persons_person_family_name_optional")
	private WebElement personFamilyNameBox;
	
	@FindBy(id = "form1:j_idt497:0:inppersons_person_ous_optional")
	private WebElement orgNrBox;
	
	@FindBy(id = "form1:j_idt545:0:inporganizations_organization_name")
	private WebElement orgNameBox;
	
	@FindBy(id = "form1:inputFreeKeywords")
	private WebElement contentKeywords;
	
	@FindBy(id = "form1:txtDatePublishedInPrint")
	private WebElement datePublishedInPrintBox;
	
	@FindBy(id = "form1:txtTotalNoOfPages")
	private WebElement pageNumberBox;
	
	@FindBy(id = "form1:iterDetailGroupIdentifier:0:selSelectIdentifierType")
	private WebElement identifierDropdown;
	
	@FindBy(id = "form1:iterDetailGroupIdentifier:0:inpIdentifierValue")
	private WebElement identifierValue;
	
	@FindBy(id = "form1:inpEventTitle")
	private WebElement eventTitle;
	
	@FindBy(id = "form1:txtEventPlace")
	private WebElement eventPlace;
	
	@FindBy(id = "form1:txtEventStartDate")
	private WebElement eventStartDate;
	
	@FindBy(id = "form1:txtEventEndDate")
	private WebElement eventEndDate;
	
	@FindBy(id = "form1:inputProjectTitle")
	private WebElement projectName;
	
	@FindBy(id = "form1:inpProjectInfoFundingProgram")
	private WebElement projectFundingProgram;
	
	@FindBy(id = "form1:j_idt827:0:selChooseSourceGenre")
	private WebElement sourceGenreDropdown;
	
	@FindBy(id = "form1:j_idt827:0:inpSourceTitle_Journal")
	private WebElement sourceTitleBox;
	
	@FindBy(id = "form1:j_idt827:0:j_idt899:0:selCreatorRoleString")
	private WebElement creatorRoleDropdown;
	
	@FindBy(id = "form1:j_idt827:0:j_idt899:0:inpcreator_persons_person_family_name_optional")
	private WebElement creatorFamilyNameBox;
	
	@FindBy(id = "form1:j_idt827:0:j_idt899:0:inppersons_person_ous_optional")
	private WebElement creatorOrgNrBox;
	
	@FindBy(id = "form1:j_idt827:0:j_idt954:0:inporganizations_organization_name")
	private WebElement creatorOrgNameBox;
	
	@FindBy(id = "form1:j_idt827:0:inpSourceDetailVolume")
	private WebElement sourceVolumeBox;
	
	@FindBy(id = "form1:j_idt827:0:inpSourceNumberOfPagesLabel")
	private WebElement sourcePageNrBox;
	
	@FindBy(id = "form1:j_idt827:0:inpSourceDetailPublisher")
	private WebElement sourcePublisherBox;
	
	@FindBy(id = "form1:j_idt827:0:inpSourceDetailPublisherLabel")
	private WebElement sourcePublisherPlace;
	
	@FindBy(id = "form1:j_idt827:0:iterSourceDetailsIdentifier:0:selSourceDetailIdentifier")
	private WebElement sourceIdentifierDropdown;
	
	@FindBy(id = "form1:j_idt827:0:iterSourceDetailsIdentifier:0:inpSourceDetailIdentifier")
	private WebElement sourceIdentifierBox;
	
	@FindBy(id = "form1:lnkSave")
	private WebElement saveButton;
	
	@FindBy(id = "form1:lnkRelease")
	private WebElement releaseButton;
	
	private final String publisherVersionValue = "http://purl.org/escidoc/metadata/ves/content-categories/publisher-version";
	
	public FullSubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage fullSubmission(Genre genre, String title, String author, String filepath) {
		fillInData(genre, title, author);
		fillInGenericData(genre);
		uploadFile(filepath);
		return save();
	}
	
	private void fillInData(Genre genre, String title, String author) {
		Select genreSelect = new Select(genreDropdown);
		genreSelect.selectByValue(genre.toString());
		titleBox.sendKeys(title);
		
		fillInPersonInfo(author);
	}
	
	private void uploadFile(String filepath) {
		String addFileButtonID = "form1:inpFile_input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + addFileButtonID + "').style.display = 'block';");
		driver.findElement(By.id(addFileButtonID)).sendKeys(filepath);
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByValue(publisherVersionValue);
		descriptionBox.sendKeys("test");
	}
	
	private void fillInGenericData(Genre genre) {
		/* TODO add locator */
		fillInContentInfo();
		fillInDetailsInfo();
		if (genre.equals(Genre.CONFERENCE_PAPER))
			fillInEventInfo();
		fillInProjectInfo();
		fillInSourceInfo();
	}
	
	private void fillInPersonInfo(String author) {
		personFamilyNameBox.sendKeys(author);
		orgNrBox.sendKeys("1");
		orgNameBox.sendKeys("MPI for Social Anthropology, Max Planck Society");
	}
	
	private void fillInContentInfo() {
		contentKeywords.sendKeys("test", "QA", "PubMan");
	}
	
	private void fillInDetailsInfo() {
		datePublishedInPrintBox.sendKeys("2016-01-01");
		pageNumberBox.sendKeys("2");
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByValue("ARXIV");
		identifierValue.sendKeys("1601.00001");
	}
	
	private void fillInEventInfo() {
		eventTitle.sendKeys("Test Conference");
		eventPlace.sendKeys("Munich");
		eventStartDate.sendKeys("2016-01-01");
		eventEndDate.sendKeys("2016-01-02");
	}
	
	private void fillInProjectInfo() {
		projectName.sendKeys("Test Project");
		projectFundingProgram.sendKeys("Funding Programme 7 (FP7) - European Commission (EC)");
	}
	
	private void fillInSourceInfo() {
		Select genreSourceSelect = new Select(sourceGenreDropdown);
		genreSourceSelect.selectByValue(Genre.BOOK.toString());
		sourceTitleBox.sendKeys("Test Book");
		Select creatorRoleSelect = new Select(creatorRoleDropdown);
		creatorRoleSelect.selectByValue("AUTHOR");
		creatorFamilyNameBox.sendKeys("Testermann, Testo (MPI for Social Anthropology, Max Planck Society)");
		creatorOrgNameBox.sendKeys("MPI for Social Anthropology, Max Planck Society");
		sourceVolumeBox.sendKeys("2");
		sourcePageNrBox.sendKeys("800");
		sourcePublisherBox.sendKeys("QA");
		sourcePublisherPlace.sendKeys("Munich");
		Select sourceIdentifierSelect = new Select(sourceIdentifierDropdown);
		sourceIdentifierSelect.selectByValue("ARXIV");
		sourceIdentifierBox.sendKeys("1601.00002");
	}
	
	private ViewItemPage save() {
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
