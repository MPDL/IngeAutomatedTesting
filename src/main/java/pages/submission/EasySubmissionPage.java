package main.java.pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.java.base.Genre;
import main.java.pages.BasePage;

/** 
 *  Page with a dynamic three-step completion process
 */
public class EasySubmissionPage extends BasePage {
	
	public EasySubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage easySubmission(Genre genre, String title, String filepath) {
		return stepOne(genre, title, filepath);
	}
	
	private ViewItemPage stepOne(Genre genre, String title, String filepath) {
		WebElement genreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:selGenre"));
		Select genreSelect = new Select(genreDropdown);
		genreSelect.selectByValue(genre.toString());
		
		WebElement genreTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:inpItemMetadataTitle"));
		genreTitleBox.sendKeys(title);
		
		uploadFile(filepath);
		
		WebElement nextButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:lnkNext"));
		nextButton.click();
		
		return stepTwo();
	}
	
	private void uploadFile(String filepath) {
		String addFileInputID = "form1:easySubmission:easySubmissionStep1Manual:inpFile_input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + addFileInputID + "').style.display = 'block';");
		driver.findElement(By.id(addFileInputID)).sendKeys(filepath);
		
		WebElement contentCategoryDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep1Manual:fileUploads:0:selContentCategory"));
		String publisherVersionValue = "http://purl.org/escidoc/metadata/ves/content-categories/publisher-version";
		Select contentCategorySelect = new Select(contentCategoryDropdown);
		contentCategorySelect.selectByValue(publisherVersionValue);
	}
	
	private ViewItemPage stepTwo() {
		WebElement addMultipleAuthorsLink = driver.findElement(By.id("btnShowMultipleAuthors"));
		addMultipleAuthorsLink.click();
		WebElement parseAuthorsBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:inpcreatorParseString"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(parseAuthorsBox));
		parseAuthorsBox.sendKeys("Testo Testermann, test user");
		WebElement addAuthorsLink = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:btnAddAuthors"));
		addAuthorsLink.click();
		
		WebElement organisationNameBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:j_idt651:0:inporganizations_organization_name"));
		organisationNameBox.sendKeys("MPI for Social Anthropology, Max Planck Society");
		
		WebElement personOrgNrBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:j_idt595:0:inppersons_person_ous_optional"));
		personOrgNrBox.sendKeys("1");
		
		WebElement nextButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep2Manual:lnkNext"));
		nextButton.click();
		
		return stepThree();
	}
	
	private ViewItemPage stepThree() {
		WebElement datePublishedBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:txtDatePublishedInPrint"));
		datePublishedBox.sendKeys("2016-01-01");
		
		WebElement sourceGenreDropdown = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:selSourceGenre"));
		Select sourceGenreSelect = new Select(sourceGenreDropdown);
		sourceGenreSelect.selectByValue(Genre.BOOK.toString());
		
		WebElement sourceTitleBox = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:inpSourceTitle"));
		sourceTitleBox.sendKeys("Test Book");
		
		WebElement saveButton = driver.findElement(By.id("form1:easySubmission:easySubmissionStep3Manual:lnkSave"));
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}

}
