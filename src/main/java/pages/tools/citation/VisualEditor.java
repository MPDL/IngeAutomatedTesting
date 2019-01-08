package main.java.pages.tools.citation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VisualEditor {

	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(id = "styleMenuUl")
	private WebElement hiddenMenu;
	
	@FindBy(id = "styleMenu")
	private WebElement styleMenu;
	
	@FindBy(css = "#elementProperties input")
	private WebElement titleBox;
	
	public VisualEditor(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
		
		PageFactory.initElements(driver, this);
	}
	
	public void createNewStyle(String title) {
		styleMenu.click();
		hiddenMenu.findElement(By.id("menuNewStyle")).click();
		PageFactory.initElements(driver, VisualEditor.class);
		
		titleBox.clear();
		titleBox.sendKeys(title);
	}
	
	public void editStyle(String newTitle) {
		titleBox.clear();
		titleBox.sendKeys(newTitle);
	}
	
	public boolean canSaveNewStyle() {
		saveStyle();
		// TODO enable flash
//		WebElement saveButton = driver.findElement(By.xpath("//object[contains(@id, 'downloadify_')]"));
		
//		return saveButton.isDisplayed() && saveButton.isEnabled();
		return true;
	}
	
	public void saveModifiedStyle() {
		saveStyle();
		// TODO enable flash
//		driver.switchTo().alert().dismiss();
	}
	
	private void saveStyle() {
		styleMenu.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Save Style")));
		
		hiddenMenu.findElement(By.cssSelector("ul li:nth-of-type(3)")).click();
	}
}
