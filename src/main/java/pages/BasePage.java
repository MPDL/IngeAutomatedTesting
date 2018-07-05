package main.java.pages;

import java.io.File;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import main.java.components.MainMenuComponent;
import main.java.components.MetaMenuComponent;
import main.java.components.SearchComponent;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.HomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.ViewItemPage;
import main.java.pages.tools.ToolsPage;

/**
 * Components and actions common to every QA-PubMan page.
 * @author apetrova
 *
 */
public abstract class BasePage {
  
    protected final Logger log4j = Logger.getLogger(BasePage.class);

	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(css = "#contentSkipLinkAnchor>h1")
	private WebElement headline;
	
	protected MetaMenuComponent metaMenuComponent;
	protected MainMenuComponent mainMenuComponent;
	protected SearchComponent searchComponent;
	
	private final int EXPLICIT_TIMEOUT = 200;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
		
		metaMenuComponent = new MetaMenuComponent(driver);
		mainMenuComponent = new MainMenuComponent(driver);
		searchComponent = new SearchComponent(driver);
	}
	
	public String getHeadline() {
		return headline.getText();
	}
	
	public StartPage goToStartPage() {
		return mainMenuComponent.goToStartPage();
	}
	
	public LoginPage goToLoginPage() {
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public SearchResultsPage quickSearch(String searchQuery) {
		return searchComponent.quickSearch(searchQuery);
	}
	
	public ToolsPage goToToolsPage() {
		return mainMenuComponent.goToToolsPage();
	}
	
	public AdvancedSearchPage goToAdvancedSearchPage() {
		return searchComponent.goToAdvancedSearchPage();
	}
	
	/**
	 * Opens the ViewItemPage of the item. <p>
	 * 
	 * Use the Search-Component to search for an exact match of the item title. <br>
	 * Only released items can be opened this way. <p>
	 * 
	 * Opens the first search result item.
	 * If more than one result is returned, the viewItemPage of the first result is opened and an warn message is printed.
	 * 
	 * @param itemTitle The title to search for.
	 * @return The viewItemPage of the item.
	 */
	public ViewItemPage openReleasedItemByTitle(String itemTitle) {
		//Use quotes to search for an exact match
		itemTitle = "\"" + itemTitle + "\"";
		
		SearchResultsPage searchResultsPage = searchComponent.quickSearch(itemTitle);
		try {
	      Thread.sleep(250);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
		
		if(searchResultsPage.getResultCount() > 1) {
			log4j.warn("More than one item with " + itemTitle + " found.");
		}
		
		ViewItemPage viewItemPage = searchResultsPage.openFirstResult();
		return viewItemPage;
	}
	
	public HomePage goToHomePage(HomePage homePage) {
		goToStartPage();
		if (homePage instanceof DepositorHomePage)
			return new DepositorHomePage(driver);
		if (homePage instanceof ModeratorHomePage)
			return new ModeratorHomePage(driver);
		return new CombinedHomePage(driver);
	}
	
	public Object openLinkNewWindow(WebElement link, Object PageType) {
		String firstHandle = driver.getWindowHandle();
		link.click();
		try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		
		return PageFactory.initElements(driver, PageType.getClass());
	}
	
	public boolean errorMessageDisplayed() {
		try {
			driver.findElement(By.className("messageError"));
			return true;
		}
		catch (NoSuchElementException exc) {
			return false;
		}
	}
	
	public String getErrorMessage() {
		if (errorMessageDisplayed()) {
			return driver.findElement(By.className("messageError")).getText();
		}
		return "";
	}
	
	public final String getFilepath(String fileName) {
	  fileName = "/" + fileName;
      String filepath = getClass().getResource(fileName).getPath();
      String os = System.getProperty("os.name");
      System.out.println("OS " + os);
      if (driver instanceof FirefoxDriver) {
        if (os.startsWith("Windows")) {
          filepath = filepath.substring(1, filepath.length()).replace('/', File.separatorChar);
        }
      }
      if (driver instanceof ChromeDriver) {
        if (os.startsWith("Windows")) {
          filepath = filepath.substring(1, filepath.length()).replace('/', File.separatorChar);
        }
      }
        
      System.out.println("filepath: " + filepath);
      return filepath;
	}
	
}
