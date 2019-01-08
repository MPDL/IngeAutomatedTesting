package main.java.pages.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import main.java.pages.BasePage;
import main.java.pages.tools.citation.CitationStyleEditor;
import main.java.pages.tools.cone.ConeBasePage;
import main.java.pages.tools.rest.RestDescriptionPage;
import main.java.pages.tools.rest.RestExamplePage;

public class ToolsPage extends BasePage {

	@FindBy(xpath = "//a[contains(@id, 'lnkCoNE')]")
	private WebElement coneLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkRest')]")
	private WebElement restLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkCslEditor')]")
	private WebElement citationEditorLink;
	
	public ToolsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ConeBasePage goToCoNE() {
		return (ConeBasePage) openLinkNewWindow(coneLink, ConeBasePage.getInstance(driver));
		/*String firstHandle = driver.getWindowHandle();
		coneLink.click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		return PageFactory.initElements(driver, ConeBasePage.class);*/
	}
	
	public RestExamplePage goToRestInterface() {
		RestDescriptionPage restDescription = (RestDescriptionPage) openLinkNewWindow(restLink, RestDescriptionPage.getInstance(driver));
		return restDescription.goToRestExample();
		/*String firstHandle = driver.getWindowHandle();
		restLink.click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		driver.findElement(By.id("mainMenuSkipLinkAnchor")).findElement(By.tagName("a")).click();
		
		return PageFactory.initElements(driver, RestExamplePage.class);*/
	}
	
	public CitationStyleEditor goToCitationStyleEditor() {
		CitationStyleEditor citationStyleEditor = (CitationStyleEditor) openLinkNewWindow(citationEditorLink, CitationStyleEditor.getInstance(driver));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav_codeEditor")));
		
		return citationStyleEditor;
		
		/*String firstHandle = driver.getWindowHandle();
		citationEditorLink.click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		return PageFactory.initElements(driver, CitationStyleEditor.class);*/
	}
}
