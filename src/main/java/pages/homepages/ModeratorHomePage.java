package main.java.pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.submission.QAWorkspacePage;

public class ModeratorHomePage extends HomePage {

	public ModeratorHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public QAWorkspacePage goToQAWorkspacePage() {
		return mainMenuComponent.goToQAWorkspacePage();
	}
}
