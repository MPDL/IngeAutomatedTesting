package pages.submission;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

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
	
	// TODO: initial find version of method, fix null return values
	public WebElement findImport(String importName) {
		for (WebElement singleImport : imports) {
			if (singleImport.getText().equals(importName))
				return singleImport;
		}
		return null;
	}
	
	public void waitForUploadToFinish() {
		// TODO make sure file reaches 100% and has label "FINISHED"
	}
	
	public ImportWorkspacePage releaseImport(String importName) {
		findImport(importName).findElement(By.linkText("Release")).click();
		// TODO make sure file reaches 100% and has label "FINISHED"
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
}
