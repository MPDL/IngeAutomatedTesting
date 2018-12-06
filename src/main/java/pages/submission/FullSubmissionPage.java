package main.java.pages.submission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.pages.BasePage;
import test.java.base.GenreGroup;
import test.java.base.SeleniumWrapper;
import test.java.base.TableHelper;

//TODO: Refactor this class: Split into Components; Handle the different 'fullSubmission' workflows in a clearer way; Avoid code replication.
public class FullSubmissionPage extends BasePage {

	/* basics form */
	@FindBy(id = "form1:cboGenre")
	private WebElement genreDropdown;
	
	@FindBy(id = "form1:inputTitleText")
	private WebElement titleBox;
	
	@FindBy(id = "form1:cboDegreeType")
	private WebElement degreeDropdown;
	
	/* locator form */
	@FindBy(id = "form1:locatorUploads:0:inpAddUrl")
	private WebElement locatorBox;
	
	@FindBy(id = "form1:locatorUploads:0:btnSaveUrl")
	private WebElement saveLocator;
	
	/* persons&organisations form */
	@FindBy(xpath = "//input[contains(@id, '0:inpcreator_persons_person_family_name_optional')]")
	private WebElement personFamilyNameBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inppersons_person_given_name_optional')]")
	private WebElement personGivenNameBox;
	
	@FindBy(id = "btnShowMultipleAuthors")
	private WebElement addMultipleAuthorsLink;
	
	@FindBy(xpath = "//input[contains(@id, '0:inppersons_person_ous_optional')]")
	private WebElement orgNrBox;
	
	@FindBy(xpath = "//textarea[contains(@id, '0:inporganizations_organization_address')]")
	private WebElement orgAddrBox;
	
	@FindBy(xpath = "//textarea[contains(@id, '0:inporganizations_organization_name')]")
	private WebElement orgNameBox;
	
	/* content form */
	@FindBy(id = "form1:inputFreeKeywords")
	private WebElement contentKeywordsBox;
	
	@FindBy(id = "form1:iterContentGroupDDCSubjectList:0:selSelectIdentifierType")
	private WebElement classificationDropdown;
	
	@FindBy(id = "form1:iterContentGroupDDCSubjectList:0:inpSubjectValue")
	private WebElement classificationValueBox;
	
	@FindBy(id = "form1:iterContentGroupAbstract:0:inputAbstractValue")
	private WebElement abstractBox;
	
	@FindBy(id = "form1:iterContentGroupAbstract:0:selcreatorOrgTypeString")
	private WebElement abstractLanguageBox;
	
	/* details form */
	@FindBy(id = "form1:txtDatePublishedInPrint")
	private WebElement datePrint;
	
	@FindBy(id = "form1:txtDatePublishedOnline")
	private WebElement dateOnline;
	
	@FindBy(id = "form1:txtDateAccepted")
	private WebElement dateAccepted;
	
	@FindBy(id = "form1:txtDateSubmitted")
	private WebElement dateSubmitted;
	
	@FindBy(id = "form1:txtDateModified")
	private WebElement dateModified;
	
	@FindBy(id = "form1:txtDateCreated")
	private WebElement dateCreated;
	
	@FindBy(id = "form1:lgTable:0:selectLanguageOfPublication")
	private WebElement publicationLanguageBox;
	
	@FindBy(id = "form1:txtTotalNoOfPages")
	private WebElement pageNumberBox;
	
	//TODO: Use/Set the Publisher (Details)
	@FindBy(id = "form1:txtaPublisher")
	private WebElement publisherBox;
	
	//TODO: Use/Set the Place (Details)
	@FindBy(id = "form1:txtPlace")
	private WebElement placeBox;
	
	//TODO: Use/Set the Table of content (Series Details)
	@FindBy(id = "form1:txtaTableOfContent")
	private WebElement tableOfContentsBox;
	
	@FindBy(id = "form1:iterDetailGroupIdentifier:0:selSelectIdentifierType")
	private WebElement identifierDropdown;
	
	@FindBy(id = "form1:iterDetailGroupIdentifier:0:inpIdentifierValue")
	private WebElement identifierValueBox;
	
	//TODO: Use/Set the Review type
	@FindBy(id = "form1:cboReviewType")
	private WebElement reviewDropdown;
	
	/* event form */
	@FindBy(id = "form1:inpEventTitle")
	private WebElement eventTitleBox;
	
	@FindBy(id = "form1:txtEventPlace")
	private WebElement eventPlaceBox;
	
	@FindBy(id = "form1:txtEventStartDate")
	private WebElement eventStartBox;
	
	@FindBy(id = "form1:txtEventEndDate")
	private WebElement eventEndBox;
	
	/* project information form */
	@FindBy(id = "form1:lgTable:0:selectLanguageOfPublication")
	private WebElement languageBox;
	
	@FindBy(xpath = "//textarea[contains(@id, 'inputProjectTitle')]")
	private WebElement projectNameBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inputProjectId')]")
	private WebElement grantIDBox;
	
	@FindBy(xpath = "//input[contains(@id, 'inpProjectInfoFundingProgram')]")
	private WebElement fundingProgramBox;
	
	/* source form */
	@FindBy(xpath = "//select[contains(@id, '0:selChooseSourceGenre')]")
	private WebElement sourceGenreDropdown;
	
	@FindBy(xpath = "//textarea[contains(@id, '0:inpSourceTitle_Journal')]")
	private WebElement sourceTitleBox;
	
	@FindBy(xpath = "//select[contains(@id, '0:selSourceCreatorRoleString')]")
	private WebElement sourceCreatorRoleDropdown;
	
	@FindBy(css = "#iterSourceCreatorOrganisationAuthors .familyName")
	private WebElement sourceCreatorFamilyNameBox;
	
	@FindBy(css = "#iterSourceCreatorOrganisationAuthors .ouNumber")
	private WebElement sourceCreatorOrgNrBox;
	
	@FindBy(css = "#iterSourceCreatorOrganisation .organizationName")
	private WebElement sourceCreatorOrgNameBox;
	
	@FindBy(css = "#iterSourceCreatorOrganisation .organizationAddress")
	private WebElement sourceCreatorOrgAddrBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailVolume')]")
	private WebElement sourceVolumeBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceNumberOfPagesLabel')]")
	private WebElement sourcePageNrBox;
	
	@FindBy(xpath = "//textarea[contains(@id, '0:inpSourceDetailPublisher')]")
	private WebElement sourcePublisherBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailPublisherLabel')]")
	private WebElement sourcePublisherPlace;
	
	@FindBy(xpath = "//select[contains(@id, '0:iterSourceDetailsIdentifier:0:selSourceDetailIdentifier')]")
	private WebElement sourceIdentifierDropdown;
	
	@FindBy(xpath = "//input[contains(@id, '0:iterSourceDetailsIdentifier:0:inpSourceDetailIdentifier')]")
	private WebElement sourceIdentifierBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailInfoEdition')]")
	private WebElement sourceEditionBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailSourceIssue')]")
	private WebElement sourceIssueBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailStartPage')]")
	private WebElement sourceStartPageBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailEndPage')]")
	private WebElement sourceEndPageBox;
	
	@FindBy(xpath = "//input[contains(@id, '0:inpSourceDetailSeqNumber')]")
	private WebElement sequenceNrBox;
	
	/* actions form */
	@FindBy(id = "form1:lnkSave")
	private WebElement saveButton;
	
	@FindBy(id = "form1:lnkRelease")
	private WebElement releaseButton;
	
	private final String publisherVersionText = "Publisher version";
	
	public FullSubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Performe a FULL SUBMISSION filling in the minimal set of required fields. <br>
	 * Uses 'Other' as Genre and sets the OrgNr to '1'.
	 * 
	 * @param title The titel of this submission
	 * @param author The author of this submssion
	 * @param organization The organization of this submission
	 * @return
	 */
	public ViewItemPage fullSubmissionMinimal(String title, String author, String organization) {
		fillInBasics("Other", title);
		fillInPersonAndOrganization(author, "", "1", organization, "");
		return save();
	}
	
	// PM-22
	// note: only the 'select by visible text' method is used in order to be able to validate afterwards
	public ViewItemPage fullSubmission(String genre, String title, String author, String[] filepaths) {
		fillInBasics(genre, title);
		fillInGenericData(genre);
		uploadFiles(filepaths);
		fillInPersonAndOrganization(author, "", "1", "MPI for Social Anthropology, Max Planck Society", "");
		return save();
	}
	
	public ViewItemPage fullSubmissionEventDepSimple(TableHelper table) {
		fillInCommon(GenreGroup.EVENT_DEPENDENT, table);
		fillInSourceEventDependent(table);
		
		return save();
	}
	
	public ViewItemPage fullSubmissionNoSrc(TableHelper table) {
		fillInCommon(GenreGroup.NO_SOURCE, table);
		
		return save();
	}
	
	// PM-22
	public ViewItemPage fullSubmissionSrcIndep(TableHelper table) {
		fillInCommon(GenreGroup.SOURCE_INDEP, table);
		fillInSourceIndependent(table);
		
		return save();
	}
	
	public ViewItemPage fullSubmissionSrcDep(TableHelper table) {
		fillInCommon(GenreGroup.SOURCE_DEP, table);
		fillInSourceDependent(table);
		
		return save();
	}
	
	public ViewItemPage fullSubmissionSrcDepBook(TableHelper table) {
		fillInCommon(GenreGroup.SOURCE_DEP_BOOK, table);
		fillInSourceDependentBook(table);
		
		return save();
	}
	
	public ViewItemPage fullSubmissionEvent(TableHelper table) {
		fillInCommon(GenreGroup.EVENT, table);
		fillInSourceEvent(table);
		
		return save();
	}
	
	//TODO: Add full standard Submission for: GenreGroup.EVENT_DEPENDENT, GenreGroup.LEGAL
	
	public ViewItemPage fullSubmissionDegree(TableHelper table) {
		fillInCommon(GenreGroup.DEGREE, table);
		String degreeType = table.getRandomRowEntry("[degree type]");
		Select degreeSelect = new Select(degreeDropdown);
		degreeSelect.selectByVisibleText(degreeType);
		fillInSourceDegree(table);
		
		return save();
	}
	
	private void fillInCommon(GenreGroup genreGroup, TableHelper table) {
		//TODO: Refactor this method. Use Enum for Genre.
		
		String genre = table.getRandomRowEntry(genreGroup.toString());
		fillInBasics(genre, table);
		uploadFile(table);
		fillInLocator(table);
		fillInAuthors(table);
		fillInContent(table);
		
		if(genre.equals("Journal")) {
			fillInDetailsForJournal(table);
		}else if (genre.equals("Series")) {
			fillInDetailsForSeries(table);
		}else {
			fillInDetails(table);
		}			
		
		if (genre.equals("Conference Paper") || genre.equals("Meeting Abstract"))
			fillInEvent(table);
		fillInProject(table);
	}
	
	// PM-32, PM-61
	private void fillInBasics(String genre, TableHelper table) {		
		String title = table.getRandomRowEntry("[title]");
		fillInBasics(genre, title);
	}
	
	// PM-32, PM-108, PM-61
	private void fillInBasics(String genre, String title) {
		Select genreSelect = new Select(genreDropdown);
		genreSelect.selectByVisibleText(genre.toString());
		titleBox.sendKeys(title);
		PageFactory.initElements(driver, this);
	}
	
	// PM-33, PM-109, PM-110, PM-62, PM-63
	private void uploadFile(TableHelper table) {
		String filepath = table.getRandomRowEntry("[upload file]");
		String contentCategory = table.getRandomRowEntry("[content category all]");
		String visibility = table.getRandomRowEntry("[Visibility]");
		
		// When the visibility is 'Restricted' an IP Range has to be selected 
		// -> Bug in Firefox: The drop-down menu options of the IP Range can NOT be selected
		// For now: When the visibility is 'Restricted', set it to another visibility instead
		if(visibility.equalsIgnoreCase("Restricted")) {
			//FIXME: As soon as the Bug is fixed or there is a workaround: Set the IP Range instead of setting another visibility
			table.setRowEntry("[Visibility]", "Public");
			visibility = "Public";
		}
		
		String description = table.getRandomRowEntry("[description file]");
		String statement = table.getRandomRowEntry("[Copyright statement]");
		String date = table.getRandomRowEntry("[Copyright Date]");
		String licenseURL = table.getRandomRowEntry("[license URL]");
		String fileURL = table.getRandomRowEntry("[file URL]");
		uploadFile(filepath, contentCategory, visibility, description, statement, date, licenseURL, fileURL);
	}
	
	// PM-33, PM-109, PM-34, PM-110, PM-62
	private void uploadFiles(String[] filepaths) {
		int n = filepaths.length;
		for (int i = 0; i < n; i++) {
			uploadFile(filepaths[i], i);
		}
	}
	
	// PM-33, PM-109, PM-110, PM-62, PM-63
	private void uploadFile(String filepath, String contentCategory, String visibility, String description, String statement, String date, String licenseURL, String fileURL) {
		String addFileInputID = "form1:inpFile_input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + addFileInputID + "').style.display = 'block';");
		driver.findElement(By.id(addFileInputID)).sendKeys(getFilepath(filepath));
		
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:fileUploads:0:selFileContentCategory"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(contentCategory);
		
		WebElement visibilityDropdown = driver.findElement(By.id("form1:fileUploads:0:selFileVisibility"));
		Select visibilitySelect = new Select(visibilityDropdown);
		visibilitySelect.selectByVisibleText(visibility);
		//TODO: Set the 'IP Range' drop-down menu if the visibility='Restricted' (As soon as Firefox-Bug (#4) is fixed)
		
		WebElement descriptionBox = driver.findElement(By.id("form1:fileUploads:0:inpExtraFileDescription"));
		descriptionBox.sendKeys(description);
		
		WebElement copyrightStatement = driver.findElement(By.id("form1:fileUploads:0:inpFileDescription"));
		copyrightStatement.sendKeys(statement);
		
		WebElement copyrightDate = driver.findElement(By.id("form1:fileUploads:0:fileLicenseDate"));
		copyrightDate.sendKeys(date);
		
		WebElement licenseBox = driver.findElement(By.id("form1:fileUploads:0:inpLicenseUrl"));
		licenseBox.sendKeys(licenseURL);
		
		WebElement fileURLBox = driver.findElement(By.id("form1:inpAddFileFromUrl"));
		fileURLBox.sendKeys(fileURL);
		WebElement uploadFileURL = driver.findElement(By.id("form1:btnUploadFileFromUrl"));
		uploadFileURL.click();
		
		contentCategoryDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form1:fileUploads:1:selFileContentCategory")));
		contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(contentCategory);
	}
	
	// PM-33, PM-109, PM-34, PM-110, PM-62
	private void uploadFile(String filepath, int i) {
		String addFileButtonID = "form1:inpFile_input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + addFileButtonID + "').style.display = 'block';");
		driver.findElement(By.id(addFileButtonID)).sendKeys(filepath);
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:fileUploads:" + i + ":selFileContentCategory"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(publisherVersionText);
		WebElement descriptionBox = driver.findElement(By.id("form1:fileUploads:" + i + ":inpExtraFileDescription"));
		descriptionBox.sendKeys("A sample file for testing.");
		fillInRights(i);
	}
	
	// PM-34, PM-110, PM-63
	private void fillInRights(int i) {
		WebElement copyrightInformation = driver.findElement(By.id("form1:fileUploads:" + i + ":inpFileDescription"));
		copyrightInformation.sendKeys("See license below.");
		WebElement copyrightDate = driver.findElement(By.id("form1:fileUploads:" + i + ":fileLicenseDate"));
		copyrightDate.sendKeys("2018-01-01");
		WebElement licenseURL = driver.findElement(By.id("form1:fileUploads:" + i + ":inpLicenseUrl"));
		licenseURL.sendKeys("https://creativecommons.org/share-your-work/public-domain/cc0/");
	}
	
	private void fillInGenericData(String genre) {
		fillInLocator();
		fillInContent();
		fillInDetails();
		if (genre.equals("Conference Paper"))
			fillInEvent();
		fillInLanguage();
		fillInProjectInfo();
		fillInSource();
	}
	
	// PM-35, PM-111, PM-64
	private void fillInLocator(TableHelper table) {
		String locator = table.getRandomRowEntry("[locator]");
		String contentCategoryLocator = table.getRandomRowEntry("[content category all]");
		fillInLocator(locator, contentCategoryLocator, "");
	}
	
	// PM-35, PM-111, PM-64
	private void fillInLocator() {
		locatorBox.sendKeys("https://subversion.mpdl.mpg.de/repos/smc/tags/public/PubMan/Wegweiser_durch_PubMan/Wegweiser_durch_PubMan.pdf");
		saveLocator.click();
		
		WebElement locatorDropdown = driver.findElement(By.id("form1:locatorUploads:0:selLocatorContentCategory"));
		Select locatorSelect = new Select(locatorDropdown);
		locatorSelect.selectByVisibleText(publisherVersionText);
		
		WebElement locatorDescription = driver.findElement(By.id("form1:locatorUploads:0:inpLocatorDescription"));
		locatorDescription.sendKeys("A sample locator.");
	}
	
	// PM-35, PM-64
	private void fillInLocator(String locator, String contentCategoryLocator, String locatorDescription) {
		WebElement locatorBox = driver.findElement(By.id("form1:locatorUploads:0:inpAddUrl"));
		locatorBox.sendKeys(locator);
		WebElement saveLink = driver.findElement(By.id("form1:locatorUploads:0:btnSaveUrl"));
		saveLink.click();
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:locatorUploads:0:selLocatorContentCategory"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(contentCategoryLocator);
		WebElement descriptionBox = driver.findElement(By.id("form1:locatorUploads:0:inpLocatorDescription"));
		descriptionBox.sendKeys(locatorDescription);
	}
	
	// PM-36, PM-112, PM-65
	private void fillInAuthors(TableHelper table) {
		String roleAll = table.getRandomRowEntry("[role all]");
		String familyName = table.getRandomRowEntry("[Person]");
		String multipleAuthors = table.getRandomRowEntry("[add multiple]");
		String organisation = table.getRandomRowEntry("[organization]");
		String orgAddress = table.getRandomRowEntry("[adress organization]");
		fillInAuthors(roleAll, familyName, multipleAuthors, organisation, orgAddress);
	}
	
	// PM-36, PM-65
	private void fillInAuthors(String roleAll, String familyName, String multipleAuthors, String organisation, String orgAddress) {
		personFamilyNameBox.sendKeys(familyName);
		hideAllSuggestions();
		orgNameBox.sendKeys(organisation);
		hideAllSuggestions();
		orgAddrBox.sendKeys(orgAddress);
		orgNrBox.sendKeys("1");
		
		addMultipleAuthorsLink.click();
		WebElement parseAuthorsBox = driver.findElement(By.id("form1:inpcreatorParseString"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(parseAuthorsBox));
		parseAuthorsBox.sendKeys(multipleAuthors);
		WebElement addAuthorsLink = driver.findElement(By.id("form1:btnAddAuthors"));
		addAuthorsLink.click();
		
		//Wait until the page is loaded after the click, by checking weather any 'old' element is now stale on the new page
		wait.until(ExpectedConditions.stalenessOf(parseAuthorsBox));
		//TODO: Handle wait for the Throbber/Pageload in a generic way.
		
		PageFactory.initElements(driver, this);
		
		// set all authors to given role. iterating over a WebElement list and
		// changing each element per iteration leads to a StaleElementReferenceException
		List<WebElement> roleDropdowns = driver.findElements(By.xpath("//select[contains(@id, ':selCreatorRoleString')]"));
		int dropdownCount = roleDropdowns.size();
		for (int i = 0; i < dropdownCount; i++) {
			for (int attempts = 0; attempts < 20; attempts++) {
				try {
					WebElement roleDropdown = driver.findElement(By.xpath("//select[contains(@id, '" + i + ":selCreatorRoleString')]"));
					
					//Width of the original Select-Element is not correct, as a result Selenium clicks at the wrong position
					//Change the width of the Select-Element to make it selectable
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					WebElement roleSelectContainer = roleDropdown.findElement(By.xpath(".."));
					int roleSelectContainerWidth = roleSelectContainer.getSize().getWidth();
				    jse.executeScript("arguments[0].style.width = '" + roleSelectContainerWidth + "px';",  roleDropdown);
					
					Select roleSelect = new Select(roleDropdown);
					roleSelect.selectByVisibleText(roleAll);
					break;
				}
				catch (StaleElementReferenceException exc) {}
			}
		}
	}

	// PM-36, PM-112, (PM-65)
	private void fillInPersonAndOrganization(String familyName, String givenName, String orgNr, String organization, String organizationAdress) {
		personFamilyNameBox.sendKeys(familyName);
		hideAllSuggestions();
		personGivenNameBox.sendKeys(givenName);
		orgNrBox.sendKeys(orgNr);
		orgNameBox.sendKeys(organization);
		hideAllSuggestions();
		orgAddrBox.sendKeys(organizationAdress);
	}
	
	// PM-38, PM-114, PM-67
	private void fillInContent(TableHelper table) {
		String keywords = table.getRandomRowEntry("[free keywords]");
		String abstractText = table.getRandomRowEntry("[abstract]");
		String classificationType = table.getRandomRowEntry("[Classification type]");
		String classificationValue = getClassificationValue(table, classificationType);
		String abstractLanguage = table.getRandomRowEntry("[Language of abstract]");
		fillInContent(keywords, classificationType, classificationValue, abstractText, abstractLanguage);
	}
	
	private String getClassificationValue(TableHelper table, String classificationType) {
		switch(classificationType) {
			case "DDC":
				return table.getRandomRowEntry("[Classification value DDC]");
			case "MPIPKS":
				return table.getRandomRowEntry("[Classification value MPIPKS]");
			default:
				return table.getRandomRowEntry("[Classification value ISO 639-3]");
		}
	}
	
	// PM-38, PM-114, PM-67
	private void fillInContent(String keywords, String classificationType, String classificationValue, String abstractText, String abstractLanguage) {
		contentKeywordsBox.sendKeys(keywords);
		Select classificationSelect = new Select(classificationDropdown);
		classificationSelect.selectByVisibleText(classificationType);
		classificationValueBox.sendKeys(classificationValue);
		hideAllSuggestions();
		abstractBox.sendKeys(abstractText);
		
		//FIXME: Sometimes selectByVisibleText() for the language-Select-Box leads in all tests to an Exception: "Cannot locate element with text: eng - English". Cause: unknown.
//		Select abstractLanguageSelect = new Select(abstractLanguageBox);
//		abstractLanguageBox.click();
//		abstractLanguageSelect.selectByVisibleText(abstractLanguage);
	}
	
	// PM-38, PM-114, PM-67
	private void fillInContent() {
		fillInContent("test, QA, INGe", "DDC", "", "some abstract", "eng - English");
	}
	
	// PM-40, PM-41, PM-42, PM-44, PM-45, PM-46, PM-48
	// PM-115, PM-116, PM-117, PM-118, PM-119, PM-120, PM-121, PM-122, PM-124
	// PM-68, PM-69, PM-70, PM-71, PM-72, PM-73, PM-74, PM-77
	private void fillInDetails(TableHelper table) {
		String datePrint = table.getRandomRowEntry("[Date published in print valid]");
		String dateOnline = table.getRandomRowEntry("[Date published online valid]");
		String dateAccepted = table.getRandomRowEntry("[Date accepted valid]");
		String dateSubmitted = table.getRandomRowEntry("[Date submitted valid]");
		String dateMofified = table.getRandomRowEntry("[Date modified valid]");
		String dateCreated = table.getRandomRowEntry("[Date created valid]");
		fillInDates(datePrint, dateOnline, dateAccepted, dateSubmitted, dateMofified, dateCreated);
		
		String publicationLanguage = table.getRandomRowEntry("[Language of publication]");
		String pageNumber = table.getRandomRowEntry("[total no of pages]");
		String identifierType = table.getRandomRowEntry("[identifier create item]");
		String identifierValue = table.getRandomRowEntry("[identifier value]");
		fillInPublication(publicationLanguage, pageNumber, identifierType, identifierValue);
		
	}
	
	// PM-40, PM-46, PM-48,
	private void fillInDetailsForJournal(TableHelper table) {
		String publicationLanguage = table.getRandomRowEntry("[Language of publication]");		
		String datePrint = table.getRandomRowEntry("[Date published in print valid]");
		String pageNumber = table.getRandomRowEntry("[total no of pages]");		
		String identifierType = table.getRandomRowEntry("[identifier create item]");
		String identifierValue = table.getRandomRowEntry("[identifier value]");
		
		this.publicationLanguageBox.sendKeys(publicationLanguage);
		hideAllSuggestions();
		this.datePrint.sendKeys(datePrint);
		this.pageNumberBox.sendKeys(pageNumber);
		//Publisher
		//Place
		//Review type
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByVisibleText(identifierType);
		identifierValueBox.sendKeys(identifierValue);
	}
	
	// PM-40, PM-41, PM-46,
	private void fillInDetailsForSeries(TableHelper table) {
		String publicationLanguage = table.getRandomRowEntry("[Language of publication]");		
		String datePrint = table.getRandomRowEntry("[Date published in print valid]");
		String dateOnline = table.getRandomRowEntry("[Date published online valid]");
		String pageNumber = table.getRandomRowEntry("[total no of pages]");
		
		this.publicationLanguageBox.sendKeys(publicationLanguage);
		hideAllSuggestions();
		this.datePrint.sendKeys(datePrint);
		this.dateOnline.sendKeys(dateOnline);
		this.pageNumberBox.sendKeys(pageNumber);
		//Publisher
		//Place
		//Table of content
		//Review type
	}
	
	// PM-40, PM-41, PM-42, PM-44, PM-45
	// PM-69, PM-70, PM-71, PM-72, PM-73, PM-74
	private void fillInDates(String print, String online, String accepted, String submitted, String modified, String created) {
		datePrint.sendKeys(print);
		dateOnline.sendKeys(online);
		dateAccepted.sendKeys(accepted);
		dateSubmitted.sendKeys(submitted);
		dateModified.sendKeys(modified);
		dateCreated.sendKeys(created);
	}
	// PM-46, PM-48
	// PM-68, PM-77
	private void fillInPublication(String publicationLanguage, String pageNumber, String identifierType, String identifierValue) {
		publicationLanguageBox.sendKeys(publicationLanguage);
		hideAllSuggestions();
		pageNumberBox.sendKeys(pageNumber);
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByVisibleText(identifierType);
		identifierValueBox.sendKeys(identifierValue);
	}
	
	// PM-40, PM-41, PM-42, PM-44, PM-45, PM-46, PM-48
	// PM-116, PM-117, PM-118, PM-119, PM-120, PM-121, PM-122, PM-124
	// PM-69, PM-70, PM-71, PM-72, PM-73, PM-74, PM-77
	private void fillInDetails() {
		datePrint.sendKeys("2016-06-06");
		dateOnline.sendKeys("2016-05-05");
		dateAccepted.sendKeys("2016-04-04");
		dateSubmitted.sendKeys("2016-03-03");
		dateModified.sendKeys("2016-02-02");
		dateCreated.sendKeys("2016-01-01");
		pageNumberBox.sendKeys("2");
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByVisibleText("arXiv");
		identifierValueBox.sendKeys("1601.00001");
	}
	
	// PM-49
	private void fillInEvent(TableHelper table) {
		String eventTitle = table.getRandomRowEntry("[Title of event]");
		String eventPlace = table.getRandomRowEntry("[Place of event]");
		String eventStart = table.getRandomRowEntry("[Start date of event]");
		String eventEnd = table.getRandomRowEntry("[Start date of event]");
		fillInEvent(eventTitle, eventPlace, eventStart, eventEnd);
	}
	
	// PM-49
	private void fillInEvent(String eventTitle, String eventPlace, String eventStart, String eventEnd) {
		eventTitleBox.sendKeys(eventTitle);
		eventPlaceBox.sendKeys(eventPlace);
		eventStartBox.sendKeys(eventStart);
		eventEndBox.sendKeys(eventEnd);
	}
	
	// PM-49
	private void fillInEvent() {
		fillInEvent("Test Conference", "Munich", "2016-01-01", "2016-01-02");
	}
	
	// PM-50, PM-125, PM-78
	private void fillInProject(TableHelper table) {
		String projectName = table.getRandomRowEntry("[Project name]");
		String grantID = table.getRandomRowEntry("[Grant ID]");
		String fundingProgram = table.getRandomRowEntry("[Funding program]");
		fillInProject(projectName, grantID, fundingProgram);
	}
	
	// PM-50, PM-125, PM-78
	private void fillInProject(String projectName, String grantID, String fundingProgram) {
		projectNameBox.sendKeys(projectName);
		grantIDBox.sendKeys(grantID);
		fundingProgramBox.sendKeys(fundingProgram);
		hideAllSuggestions();
	}
	
	// PM-50, PM-125, PM-78
	private void fillInProjectInfo() {
		fillInProject("Test Project", "1", "Funding Programme 7 (FP7) - European Commission (EC)");
	}
	
	//PM-115, PM-68
	private void fillInLanguage() {
		languageBox.sendKeys("eng");
	}
	
	// PM-79, PM-80
	private void fillInSourceIndependent(TableHelper table) {
		String genreSource = table.getRandomRowEntry("[genre source]");
		String titleSource = table.getRandomRowEntry("[title source]");
		String roleSource = table.getRandomRowEntry("[role source]");
		String personSource = table.getRandomRowEntry("[Person source]");
		String orgSource = table.getRandomRowEntry("[organization source]");
		String orgAddrSource = table.getRandomRowEntry("[Address organization source]");
		
		fillInSourceBasics(genreSource, titleSource, roleSource, personSource, orgSource, orgAddrSource);
	}
	
	// (PM-53)
	private void fillInSourceDependent(TableHelper table) {
		String genreSource = table.getRandomRowEntry("[genre source]");
		String titleSource = table.getRandomRowEntry("[title source]");
		String roleSource = table.getRandomRowEntry("[role source]");
		String personSource = table.getRandomRowEntry("[Person source]");
		String orgSource = table.getRandomRowEntry("[organization source]");
		String orgAddrSource = table.getRandomRowEntry("[Address organization source]");
		fillInSourceBasics(genreSource, titleSource, roleSource, personSource, orgSource, orgAddrSource);
		
		String volumeSource = table.getRandomRowEntry("[Volume source]");
		String publisherSource = table.getRandomRowEntry("[Publisher source]");
		fillInSourceDetails(volumeSource, publisherSource);
		
		String identifierSource = table.getRandomRowEntry("[identifier source create item]");
		String identifierValue = table.getRandomRowEntry("[identifier source value]");
		String startPageSource = table.getRandomRowEntry("[Start page source]");
		fillInSourceIdentifier(identifierSource, identifierValue, startPageSource);
	}
	
	private void fillInSourceDependentBook(TableHelper table) {
		String genreSource = table.getRandomRowEntry("[genre source]");
		String titleSource = table.getRandomRowEntry("[title source]");
		String roleSource = table.getRandomRowEntry("[role source]");
		String personSource = table.getRandomRowEntry("[Person source]");
		String orgSource = table.getRandomRowEntry("[organization source]");
		String orgAddrSource = table.getRandomRowEntry("[Address organization source]");
		fillInSourceBasics(genreSource, titleSource, roleSource, personSource, orgSource, orgAddrSource);
		
		String volumeSource = table.getRandomRowEntry("[Volume source]");
		String publisherSource = table.getRandomRowEntry("[Publisher source]");
		String placeSource = table.getRandomRowEntry("[Place source]");
		fillInSourceDetails(volumeSource, publisherSource, placeSource);
		
		String identifierSource = table.getRandomRowEntry("[identifier source create item]");
		String identifierValue = table.getRandomRowEntry("[identifier source value]");
		String startPageSource = table.getRandomRowEntry("[Start page source]");
		String endPageSource = table.getRandomRowEntry("[Endpage source]");
		fillInSourceIdentifier(identifierSource, identifierValue, startPageSource, endPageSource);
	}
	
	private void fillInSourceEvent(TableHelper table) {
		String genreSource = table.getRandomRowEntry("[genre source]");
		String titleSource = table.getRandomRowEntry("[title source]");
		String roleSource = table.getRandomRowEntry("[role source]");
		String personSource = table.getRandomRowEntry("[Person source]");
		String orgSource = table.getRandomRowEntry("[organization source]");
		String orgAddrSource = table.getRandomRowEntry("[Address organization source]");
		fillInSourceBasics(genreSource, titleSource, roleSource, personSource, orgSource, orgAddrSource);
		
		String volumeSource = table.getRandomRowEntry("[Volume source]");
		String numPagesSource = table.getRandomRowEntry("[Total no of pages source]");
		String publisherSource = table.getRandomRowEntry("[Publisher source]");
		String placeSource = table.getRandomRowEntry("[Place source]");
		fillInSourceDetails(volumeSource, numPagesSource, publisherSource, placeSource);
		
		String identifierSource = table.getRandomRowEntry("[identifier source create item]");
		String identifierValue = table.getRandomRowEntry("[identifier source value]");
		String startPageSource = table.getRandomRowEntry("[Start page source]");
		String endPageSource = table.getRandomRowEntry("[Endpage source]");
		fillInSourceIdentifier(identifierSource, identifierValue, startPageSource, endPageSource);
	}
	
	private void fillInSourceDegree(TableHelper table) {
		fillInSourceEvent(table);
		String sourceEdition = table.getRandomRowEntry("[Edition]");
		sourceEditionBox.sendKeys(sourceEdition);
	}
	
	private void fillInSourceEventDependent(TableHelper table) {
		fillInSource(table);
	}
	
	private void fillInSource(TableHelper table) {
		String genreSource = table.getRandomRowEntry("[genre source]");
		String titleSource = table.getRandomRowEntry("[title source]");
		String roleSource = table.getRandomRowEntry("[role source]");
		String personSource = table.getRandomRowEntry("[Person source]");
		String orgSource = table.getRandomRowEntry("[organization source]");
		String orgAddrSource = table.getRandomRowEntry("[Address organization source]");
		fillInSourceBasics(genreSource, titleSource, roleSource, personSource, orgSource, orgAddrSource);
		
		String volumeSource = table.getRandomRowEntry("[Volume source]");
		String numPagesSource = table.getRandomRowEntry("[Total no of pages source]");
		String publisherSource = table.getRandomRowEntry("[Publisher source]");
		String placeSource = table.getRandomRowEntry("[Place source]");
		fillInSourceDetails(volumeSource, numPagesSource, publisherSource, placeSource);
		
		String identifierSource = table.getRandomRowEntry("[identifier source create item]");
		String identifierValue = table.getRandomRowEntry("[identifier source value]");
		String issueSource = table.getRandomRowEntry("[issue source]");
		String startPageSource = table.getRandomRowEntry("[Start page source]");
		String endPageSource = table.getRandomRowEntry("[Endpage source]");
		fillInSourceIdentifier(identifierSource, identifierValue, issueSource, startPageSource, endPageSource);
	}
	
	// PM-51, PM-52, PM-79, PM-80
	private void fillInSourceBasics(String genreSource, String titleSource, String roleSource, String personSource,
			String orgSource, String orgAddrSource) {
		Select genreSourceSelect = new Select(sourceGenreDropdown);
		genreSourceSelect.selectByVisibleText(genreSource);
		sourceTitleBox.sendKeys(titleSource);
		
		//Width of the original Select-Element is not correct, as a result Selenium clicks at the wrong position
		//Change the width of the Select-Element to make it selectable
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement sourceCreatorRoleSelectContainer = sourceCreatorRoleDropdown.findElement(By.xpath(".."));
		int sourceCreatorRoleSelectContainerWidth = sourceCreatorRoleSelectContainer.getSize().getWidth();
	    jse.executeScript("arguments[0].style.width = '" + sourceCreatorRoleSelectContainerWidth + "px';",  sourceCreatorRoleDropdown);
	    
		Select creatorRoleSelect = new Select(sourceCreatorRoleDropdown);
		creatorRoleSelect.selectByVisibleText(roleSource);
		sourceCreatorFamilyNameBox.sendKeys(personSource);
		hideAllSuggestions();
		sourceCreatorOrgNameBox.sendKeys(orgSource);
		hideAllSuggestions();
		sourceCreatorOrgAddrBox.sendKeys(orgAddrSource);
	}
	
	// PM-53, PM-81
	private void fillInSourceDetails(String volumeSource, String numPagesSource, String publisherSource, String placeSource) {
		sourceVolumeBox.sendKeys(volumeSource);
		sourcePageNrBox.sendKeys(numPagesSource);
		sourcePublisherBox.sendKeys(publisherSource);
		sourcePublisherPlace.sendKeys(placeSource);
	}
	
	private void fillInSourceDetails(String volumeSource, String publisherSource, String placeSource) {
		sourceVolumeBox.sendKeys(volumeSource);
		sourcePublisherBox.sendKeys(publisherSource);
		sourcePublisherPlace.sendKeys(placeSource);
	}
	
	// (PM-53)
	private void fillInSourceDetails(String volumeSource, String publisherSource) {
		sourceVolumeBox.sendKeys(volumeSource);
		sourcePublisherBox.sendKeys(publisherSource);
	}
	
	private void fillInSourceIdentifier(String identifierSource, String identifierValue, String issueSource,
			String startPageSource, String endPageSource) {
		Select identifierSelect = new Select(sourceIdentifierDropdown);
		identifierSelect.selectByVisibleText(identifierSource);
		sourceIdentifierBox.sendKeys(identifierValue);
		sourceIssueBox.sendKeys(issueSource);
		sourceStartPageBox.sendKeys(startPageSource);
		sourceEndPageBox.sendKeys(endPageSource);
	}
	
	private void fillInSourceIdentifier(String identifierSource, String identifierValue, String startPageSource, String endPageSource) {
		Select identifierSelect = new Select(sourceIdentifierDropdown);
		identifierSelect.selectByVisibleText(identifierSource);
		sourceIdentifierBox.sendKeys(identifierValue);
		sourceStartPageBox.sendKeys(startPageSource);
		sourceEndPageBox.sendKeys(endPageSource);
	}
	
	// (PM-53)
	private void fillInSourceIdentifier(String identifierSource, String identifierValue, String startPageSource) {
		Select identifierSelect = new Select(sourceIdentifierDropdown);
		identifierSelect.selectByVisibleText(identifierSource);
		sourceIdentifierBox.sendKeys(identifierValue);
		sourceStartPageBox.sendKeys(startPageSource);
	}
	
	// PM-51, PM-52, PM-53, PM-79, PM-80, PM-81
	private void fillInSource() {
		fillInSourceBasics("Book", "Test Book", "Author", "Testermann, Testo (MPI for Social Anthropology, Max Planck Society)",
				"MPI for Social Anthropology, Max Planck Society", "");
		fillInSourceDetails("2", "800", "QA", "Munich");
	}
	
	private ViewItemPage save() {
		saveButton.click();
		
		//TODO: Handle this wait in a more generic way
		//Use the headline 'Item' to check that the ViewItem page is loaded
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\\h1[contains(text(),'Item')]")));
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	private void hideAllSuggestions() {
		//Note: Suggestions can NOT be identified clearly (no id).
		//Workaround: Hide all suggestions currently in the DOM.
		//Problem: 1) ALL suggestions must be hidden.
		//Problem: 2) Suggestion can NOT be used/clicked until their visibility is set to visible.
		//Problem: 3) Wait for the visibility of the suggestion can not be used, because it would lead to a race condition.
		
		List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@class='ac_results']"));
		for (WebElement suggestion : suggestions) {
			SeleniumWrapper.hideWebElement(driver, suggestion);			
		}
	}
	
}
