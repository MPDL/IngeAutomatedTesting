package main.java.pages.submission;

import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.pages.BasePage;
import test.java.base.GenreGroup;
import test.java.base.TableHelper;

/** 
 *  Page with a dynamic three-step completion process
 */
//TODO: Refactor EasySubmissionPage: Declare the WebElements as instance variables; Split in to three single page classes
public class EasySubmissionPage extends BasePage {
	
	public EasySubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Deterministic submission method
	 */
	public ViewItemPage easySubmission(String genre, String title, String filepath) {
		fillInBasic(genre, title);
		uploadFile(filepath);
		addLocator();
		goToStepTwo();
		
		fillInAuthors("Author", "Terstermann", "", "MPDL", "");
		fillInContent("", "");
		goToStepThree();

		fillInDates("2018-01-01", "", "", "", "", "");
		fillInSource("Book", "Test Book", "", "", "", "");
		return submit();
	}
	
	/**
	 * Reads submission data from a table by randomly choosing a cell in the corresponding row.  
	 * @param an Excel file converted into an XSSFWorkbook object
	 */
	public ViewItemPage easySubmissionPatent(TableHelper table) {
		fillInCommon(table, GenreGroup.PATENT);
		fillInDetailsPatent(table);
		return submit();
	}
	
	/**
	 * Reads submission data from a table by randomly choosing a cell in the corresponding row.  
	 * @param an Excel file converted into an XSSFWorkbook object
	 */
	public ViewItemPage easySubmissionLegal(TableHelper table) {
		fillInCommon(table, GenreGroup.LEGAL);
		fillInDetailsLegal(table);
		return submit();
	}
	
	/**
	 * Reads submission data from a table by randomly choosing a cell in the corresponding row.  
	 * @param an Excel file converted into an XSSFWorkbook object
	 */
	public ViewItemPage easySubmissionEventDependent(TableHelper table) {
		fillInCommon(table, GenreGroup.EVENT_DEPENDENT);
		fillInDetailsEventDependent(table);
		return submit();
	}
	
	private void fillInCommon(TableHelper table, GenreGroup genreGroup) {
		fillInBasic(table, genreGroup);
		uploadFile(table);
		addLocator(table);
		goToStepTwo();
		
		fillInAuthors(table);
		fillInContent(table);
		goToStepThree();
		
	}
	
	private void goToStepTwo() {
	    try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		WebElement nextButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:lnkNext"));
		nextButton.click();
	}
	
	private void goToStepThree() {
  	  try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		WebElement nextButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:lnkNext"));
		nextButton.click();
	}
	
	private ViewItemPage submit() {
  	    try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		// switch to full submission mode
		WebElement fullSubmissionButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:lnkAddDetails"));
		fullSubmissionButton.click();
		//TODO: Open a new Edit	Item Page with PageFactory and save the input there
		
		//Use the headline 'Edit item' to check that the Edit item page is loaded, then click save
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Edit item')]")));
		
		WebElement saveButton = driver.findElement(By.id("form1:lnkSave"));
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	private void fillInBasic(TableHelper table, GenreGroup genreGroup) {
  	    try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
	    log4j.debug("Table: " + table);
	    for(Entry<String, String> entry : table.getMap().entrySet()) {
          log4j.debug("Table-Key: " + entry.getKey() + " - Table-Value: " + entry.getValue());
        }
		String genre = table.getRandomRowEntry(genreGroup.toString());
		String title = table.getRandomRowEntry("[title]");
		fillInBasic(genre, title);
	}
	
	private void fillInBasic(String genre, String title) {
		WebElement genreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:selGenre"));
		Select genreSelect = new Select(genreDropdown);
		genreSelect.selectByVisibleText(genre);
		
		WebElement genreTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:inpItemMetadataTitle"));
		genreTitleBox.sendKeys(title);
	}
	
	private void uploadFile(TableHelper table) {
		String filepath = table.getRandomRowEntry("[upload file]");
		String contentCategory = table.getRandomRowEntry("[content category all]");
		String visibility = table.getRandomRowEntry("[Visibility]");
		String description = table.getRandomRowEntry("[description file]");
		String statement = table.getRandomRowEntry("[Copyright statement]");
		String date = table.getRandomRowEntry("[Copyright Date]");
		String licenseURL = table.getRandomRowEntry("[license URL]");
		String fileURL = table.getRandomRowEntry("[file URL]");
		uploadFile(filepath, contentCategory, visibility, description, statement, date, licenseURL, fileURL);
	}
	
	private void uploadFile(String filepath) {
		uploadFile(filepath, "Abstract", "Public", "", "", "", "", "");
	}
	
	private void uploadFile(String filepath, String contentCategory, String visibility, String description, String statement, String date, String licenseURL, String fileURL) {
		String addFileInputID = "form1:easySubmission:easySubmissionStep1Manual:inpFile_input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + addFileInputID + "').style.display = 'block';");
		driver.findElement(By.id(addFileInputID)).sendKeys(getFilepath(filepath));
		
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:selContentCategory"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(contentCategory);
		
		WebElement visibilityDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:selFileVisibility"));
		Select visibilitySelect = new Select(visibilityDropdown);
		visibilitySelect.selectByVisibleText(visibility);
		
		WebElement descriptionBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:inpComponentDescription"));
		descriptionBox.sendKeys(description);
		
		WebElement copyrightStatement = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:inpComponentFileDefaultMetadataRights"));
		copyrightStatement.sendKeys(statement);
		
		WebElement copyrightDate = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:fileLicenseDate"));
		copyrightDate.sendKeys(date);
		
		WebElement licenseBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:inpComponentFileDefaultMetadataLicense"));
		licenseBox.sendKeys(licenseURL);
		
		if (fileURL != null && !"".equals(fileURL)) {
		  WebElement fileURLBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:inpLocatorUpload"));
	      fileURLBox.sendKeys(fileURL);
	      WebElement uploadFileURL = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:btnEditItemUpload"));
	      uploadFileURL.click();
	        
	      contentCategoryDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:1:selContentCategory"));
	      contentCategorySelect = new Select(contentCategoryDropdown);
	      contentCategorySelect.selectByVisibleText(contentCategory);
		}
		
	}
	
	private void addLocator(TableHelper table) {
		String locator = table.getRandomRowEntry("[locator]");
		String contentCategoryLocator = table.getRandomRowEntry("[content category all]");
		addLocator(locator, contentCategoryLocator, "");
	}

	private void addLocator() {
		WebElement locatorBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:inpLocatorLocator1"));
		locatorBox.sendKeys("https://subversion.mpdl.mpg.de/repos/smc/tags/public/PubMan/Wegweiser_durch_PubMan/Wegweiser_durch_PubMan.pdf");
		WebElement saveLink = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:lblEasySubmissionSaveLocator"));
		saveLink.click();
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:selContentCategoryLoc"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText("Table of contents");
		WebElement descriptionBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:inpLocatorFileDescription"));
		descriptionBox.sendKeys("A sample locator");
	}
	
	private void addLocator(String locator, String contentCategoryLocator, String locatorDescription) {
		WebElement locatorBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:inpLocatorLocator1"));
		locatorBox.sendKeys(locator);
		WebElement saveLink = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:lblEasySubmissionSaveLocator"));
		saveLink.click();
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:selContentCategoryLoc"));
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByVisibleText(contentCategoryLocator);
		WebElement descriptionBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:locatorUploads:0:inpLocatorFileDescription"));
		descriptionBox.sendKeys(locatorDescription);
	}
	
	private void fillInAuthors(TableHelper table) {
		String roleAll = table.getRandomRowEntry("[role all]");
		String familyName = table.getRandomRowEntry("[Person]");
		String multipleAuthors = table.getRandomRowEntry("[add multiple]");
		String organisation = table.getRandomRowEntry("[organization]");
		String orgAddress = table.getRandomRowEntry("[adress organization]");
		fillInAuthors(roleAll, familyName, multipleAuthors, organisation, orgAddress);
	}
	
	private void fillInAuthors(String roleAll, String familyName, String multipleAuthors, String organisation, String orgAddress) {
		WebElement authorFamilyName = driver.findElement(By.xpath("//input[contains(@id, '0:inpcreator_persons_person_family_name_optional')]"));
		authorFamilyName.sendKeys(familyName);
		authorFamilyName.sendKeys(Keys.ESCAPE);
//		WebElement roleDropdown = driver.findElement(By.xpath("//select[contains(@id, '0:selCreatorRoleString')]"));
//		Select roleSelect = new Select(roleDropdown);
//		roleSelect.selectByVisibleText(roleAll);
		
		WebElement addMultipleAuthorsLink = driver.findElement(By.id("btnShowMultipleAuthors"));
		addMultipleAuthorsLink.click();
		WebElement parseAuthorsBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:inpcreatorParseString"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(parseAuthorsBox));
		parseAuthorsBox.sendKeys(multipleAuthors);
		WebElement addAuthorsLink = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:btnAddAuthors"));
		addAuthorsLink.click();
		
		WebElement organisationNameBox = driver.findElement(By.xpath("//textarea[contains(@id, 'inporganizations_organization_name')]"));
		organisationNameBox.sendKeys(organisation);
		organisationNameBox.sendKeys(Keys.ESCAPE);
		WebElement orgAddressBox = driver.findElement(By.xpath("//textarea[contains(@id, 'inporganizations_organization_address')]"));
		orgAddressBox.sendKeys(orgAddress);
		WebElement personOrgNrBox = driver.findElement(By.xpath("//input[contains(@id, 'inppersons_person_ous_optional')]"));
		personOrgNrBox.sendKeys("1");
		
		// set all authors to given role
//		List<WebElement> roleDropdowns = driver.findElements(By.xpath("//select[contains(@id, ':selCreatorRoleString')]"));
//		for (WebElement dropdown : roleDropdowns) {
//			roleSelect = new Select(dropdown);
//			roleSelect.selectByVisibleText(roleAll);
//		}
	}
	
	private void fillInContent(TableHelper table) {
		String keywords = table.getRandomRowEntry("[free keywords]");
		String abstractText = table.getRandomRowEntry("[abstract]");
		fillInContent(keywords, abstractText);
	}
	
	private void fillInContent(String keywords, String abstractText) {
		WebElement keywordsBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:inpFreeKeywords"));
		keywordsBox.sendKeys(keywords);
		WebElement abstractBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:inpAbstract"));
		abstractBox.sendKeys(abstractText);
	}
	
	private void fillInDates(TableHelper table) {
		String print = table.getRandomRowEntry("[Date published in print valid]");
		String online = table.getRandomRowEntry("[Date published online valid]");
		String accepted = table.getRandomRowEntry("[Date accepted valid]");
		String submitted = table.getRandomRowEntry("[Date submitted valid]");
		String modified = table.getRandomRowEntry("[Date modified valid]");
		String created = table.getRandomRowEntry("[Date created valid]");
		fillInDates(print, online, accepted, submitted, modified, created);
	}
	
	private void fillInDates(String print, String online, String accepted, String submitted, String modified, String created) {
		WebElement datePrint = driver.findElement(By.xpath("//input[contains(@id, 'txtDatePublishedInPrint')]"));
		datePrint.sendKeys(print);
		WebElement dateOnline = driver.findElement(By.xpath("//input[contains(@id, 'txtDatePublishedOnline')]"));
		dateOnline.sendKeys(online);
		WebElement dateAccepted = driver.findElement(By.xpath("//input[contains(@id, 'txtDateAccepted')]"));
		dateAccepted.sendKeys(accepted);
		WebElement dateSubmitted = driver.findElement(By.xpath("//input[contains(@id, 'txtDateSubmitted')]"));
		dateSubmitted.sendKeys(submitted);
		WebElement dateModified = driver.findElement(By.xpath("//input[contains(@id, 'txtDateModified')]"));
		dateModified.sendKeys(modified);
		WebElement dateCreated = driver.findElement(By.xpath("//input[contains(@id, 'txtDateCreated')]"));
		dateCreated.sendKeys(created);
	}
	
	private void fillInSource(String sourceGenre, String sourceTitle, String volume, String issue, String startPage, String endPage) {
		WebElement sourceGenreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:selSourceGenre"));
		Select sourceGenreSelect = new Select(sourceGenreDropdown);
		sourceGenreSelect.selectByVisibleText(sourceGenre);
		WebElement sourceTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceTitle"));
		sourceTitleBox.sendKeys(sourceGenre);
		
		if (sourceGenre.equals("")) {
			WebElement volumeBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsVolume"));
			volumeBox.sendKeys(volume);
		}
		WebElement issueBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsIssue"));
		issueBox.sendKeys(issue);
		WebElement startPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsStartPage"));
		startPageBox.sendKeys(startPage);
		WebElement endPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsEndPage"));
		endPageBox.sendKeys(endPage);
	}
	
	private void fillInDetailsPatent(String publicationDate, String applicationDate, String authorityCode, String authorityPlace, String identifierType, String identifierValue) {
		WebElement publicationDateBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtDatePublishedOnline"));
		publicationDateBox.sendKeys(publicationDate);
		WebElement applicationDateBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtDateSubmitted"));
		applicationDateBox.sendKeys(applicationDate);
		
		WebElement authorityCodeBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtaPublisher"));
		authorityCodeBox.sendKeys(authorityCode);
		WebElement authorityPlaceBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtPlace"));
		authorityPlaceBox.sendKeys(authorityPlace);
		
		WebElement identifierDropdown = driver.findElement(By.xpath("//select[contains(@id, ':selTypeString')]"));
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByVisibleText(identifierType);
		
		WebElement identifierValueBox = driver.findElement(By.cssSelector(".itemLine:nth-of-type(3)>.xHuge_area0>.xDouble_txtInput"));
		identifierValueBox.sendKeys(identifierValue);
	}
	
	private void fillInDetailsPatent(TableHelper table) {
		String publicationDate = table.getRandomRowEntry("[Publication Date]");
		String applicationDate = table.getRandomRowEntry("[Application Date]");
		String authorityCode = table.getRandomRowEntry("[Authority Code]");
		String authorityPlace = table.getRandomRowEntry("[Authority Place]");
		String identifierType = table.getRandomRowEntry("[identifier create item]");
		String identifierValue = table.getRandomRowEntry("[identifier value]");
		
		fillInDetailsPatent(publicationDate, applicationDate, authorityCode, authorityPlace, identifierType, identifierValue);
	}
	
	private void fillInLegalCase(String court, String caseNum, String caseTitle, String caseDate) {
		WebElement courtBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inplegal_case_legal_case_court_nameOptional"));
		courtBox.sendKeys(court);
		WebElement caseNumBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inplegal_case_legal_case_identifier"));
		caseNumBox.sendKeys(caseNum);
		WebElement caseTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inputLegalCaseTitleText"));
		caseTitleBox.sendKeys(caseTitle);
		WebElement caseDateBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inplegal_case_legal_case_date-published"));
		caseDateBox.sendKeys(caseDate);
	}
	
	private void fillInLegalSource(String sourceGenre, String sourceTitle, String startPage, String endPage, String identifierType, String identifierValue) {
		WebElement sourceGenreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:selSourceGenre"));
		Select sourceGenreSelect = new Select(sourceGenreDropdown);
		sourceGenreSelect.selectByVisibleText(sourceGenre);
		WebElement sourceTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceTitle"));
		sourceTitleBox.sendKeys(sourceTitle);
		
		WebElement startPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsStartPage"));
		startPageBox.sendKeys(startPage);
		WebElement endPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsEndPage"));
		endPageBox.sendKeys(endPage);
		
		WebElement identifierDropdown = driver.findElement(By.xpath("//select[contains(@id, ':selTypeString')]"));
		Select identifierSelect = new Select(identifierDropdown);
		identifierSelect.selectByVisibleText(identifierType);
		
		WebElement identifierValueBox = driver.findElement(By.cssSelector("#fullItem > div > div.full_area0.itemBlock.journalSuggestAnchor > div > div:nth-child(6) > span > input"));
		identifierValueBox.sendKeys(identifierValue);
	}
	
	private void fillInDetailsLegal(TableHelper table) {
		String print = table.getRandomRowEntry("[Date published in print valid]");
		String online = table.getRandomRowEntry("[Date published online valid]");
		String accepted = table.getRandomRowEntry("[Date accepted valid]");
		String submitted = table.getRandomRowEntry("[Date submitted valid]");
		String modified = table.getRandomRowEntry("[Date modified valid]");
		String created = table.getRandomRowEntry("[Date created valid]");
		fillInDates(print, online, accepted, submitted, modified, created);
		
		String court = table.getRandomRowEntry("[Court]");
		String caseNum = table.getRandomRowEntry("[Case Reference Number]");
		String caseTitle = table.getRandomRowEntry("[Title Legal Case]");
		String caseDate = table.getRandomRowEntry("[Date Legal Case]");
		fillInLegalCase(court, caseNum, caseTitle, caseDate);
		
		String sourceGenre = table.getRandomRowEntry("[genre source]");
		String sourceTitle = table.getRandomRowEntry("[title source]");
		String startPage = table.getRandomRowEntry("[title source]");
		String endPage = table.getRandomRowEntry("[Endpage source]");
		String identifierType = table.getRandomRowEntry("[identifier source create item]");
		String identifierValue = table.getRandomRowEntry("[identifier source value]");
		fillInLegalSource(sourceGenre, sourceTitle, startPage, endPage, identifierType, identifierValue);
		
		String publicationLang = table.getRandomRowEntry("[Language of publication]");
		String numPages = table.getRandomRowEntry("[total no of pages]");
		
		WebElement publicationLangBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpLanguageOfPublication"));
		publicationLangBox.sendKeys(publicationLang);
		WebElement numPagesBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtTotalNoOfPages"));
		numPagesBox.sendKeys(numPages);
	}
	
	private void fillInEvent(String eventTitle, String eventPlace, String eventStart, String eventEnd) {
		WebElement eventTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpEventTitle"));
		eventTitleBox.sendKeys(eventTitle);
		WebElement eventPlaceBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtEventPlace"));
		eventPlaceBox.sendKeys(eventPlace);
		WebElement eventStartBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtEventStartDate"));
		eventStartBox.sendKeys(eventStart);
		WebElement eventEndBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtEventEndDate"));
		eventEndBox.sendKeys(eventEnd);
	}
	
	private void fillInEventSource(String sourceGenre, String sourceTitle, String volume, String startPage, String endPage) {
		WebElement sourceGenreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:selSourceGenre"));
		Select sourceGenreSelect = new Select(sourceGenreDropdown);
		sourceGenreSelect.selectByVisibleText(sourceGenre);
		WebElement sourceTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceTitle"));
		sourceTitleBox.sendKeys(sourceGenre);
		
		WebElement volumeBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsVolume"));
		volumeBox.sendKeys(volume);
		WebElement startPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsStartPage"));
		startPageBox.sendKeys(startPage);
		WebElement endPageBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceDetailsEndPage"));
		endPageBox.sendKeys(endPage);
	}
	
	private void fillInDetailsEventDependent(TableHelper table) {
		String print = table.getRandomRowEntry("[Date published in print valid]");
		String online = table.getRandomRowEntry("[Date published online valid]");
		String accepted = table.getRandomRowEntry("[Date accepted valid]");
		String submitted = table.getRandomRowEntry("[Date submitted valid]");
		String modified = table.getRandomRowEntry("[Date modified valid]");
		String created = table.getRandomRowEntry("[Date created valid]");
		fillInDates(print, online, accepted, submitted, modified, created);
		
		String eventTitle = table.getRandomRowEntry("[Title of event]");
		String eventPlace = table.getRandomRowEntry("[Place of event]");
		String eventStart = table.getRandomRowEntry("[Start date of event]");
		String eventEnd = table.getRandomRowEntry("[End date of event]");
		fillInEvent(eventTitle, eventPlace, eventStart, eventEnd);
		
		String sourceGenre = table.getRandomRowEntry("[genre source]");
		String sourceTitle = table.getRandomRowEntry("[title source]");
		String startPage = table.getRandomRowEntry("[title source]");
		String endPage = table.getRandomRowEntry("[Endpage source]");
		String volume = table.getRandomRowEntry("[Volume source]");
		fillInEventSource(sourceGenre, sourceTitle, volume, startPage, endPage);
	}
	
}
