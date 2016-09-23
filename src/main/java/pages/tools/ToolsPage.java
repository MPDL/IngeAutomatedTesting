package main.java.pages.tools;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.cone.ConeBasePage;

public class ToolsPage extends BasePage {

	@FindBy(id = "j_idt98:lnkCoNE")
	private WebElement coneLink;
	
	@FindBy(id = "j_idt98:lnkRest")
	private WebElement restLink;
	
	@FindBy(id = "j_idt98:lnkCslEditor")
	private WebElement citationEditorLink;
	
	public ToolsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ConeBasePage goToCoNE() {
		String firstHandle = driver.getWindowHandle();
		coneLink.click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		return PageFactory.initElements(driver, ConeBasePage.class);
	}
	
	public RestExamplePage goToRestInterface() {
		restLink.click();
		driver.findElement(By.id("mainMenuSkipLinkAnchor")).findElement(By.tagName("a")).click();
		
		return PageFactory.initElements(driver, RestExamplePage.class);
	}
	
	public CitationStyleEditor goToCitationStyleEditor() {
		citationEditorLink.click();
		
		return PageFactory.initElements(driver, CitationStyleEditor.class);
	}
}
