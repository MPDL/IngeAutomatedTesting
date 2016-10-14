package main.java.pages.submission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import main.java.pages.BasePage;

public class ImportWorkspacePage extends BasePage {

	private List<WebElement> imports;
	
	public ImportWorkspacePage(WebDriver driver) {
		super(driver);
		
		imports = driver.findElements(By.className("listItem"));
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean isImportPresent(String importName) {
		return findImport(importName) != null;
	}
	
	public WebElement findImport(String importName) {
		imports = driver.findElements(By.className("listItem"));
		for (WebElement singleImport : imports) {
			WebElement titleLink = singleImport.findElement(By.tagName("a"));
			if (titleLink.getText().equals(importName))
				return singleImport;
		}
		return null;
	}
	
	/**
	 * Continuously checks if the import/release/delete has reached completion.
	 */
	public void waitForActionToFinish(final String importName) {
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement currentImport = null;
				try {
					currentImport = findImport(importName);
					currentImport.findElement(By.className("FINISHED"));
				}
				catch (StaleElementReferenceException | NoSuchElementException | NullPointerException exc) {
					PageFactory.initElements(driver, this);
					return false;
				}
				return true;
			}
		});
	}
	
	// Currently language-specific
	public ImportWorkspacePage releaseImport(String importName) {
		findImport(importName).findElement(By.linkText("Release")).click();
		waitForActionToFinish(importName);
		
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
	
	// Currently language-specific
	public ImportWorkspacePage deleteImport(String importName) {
		findImport(importName).findElement(By.linkText("Delete")).click();
		waitForActionToFinish(importName);
		
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
}
