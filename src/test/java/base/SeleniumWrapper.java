package test.java.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * 
 * Providing frequently used Selenium features. <br>
 * Adding/Wrapping additional functionality to the Selenium methods.
 * 
 * @author helk
 *
 */
public class SeleniumWrapper {
	
	/**
	 * Hides the webElement by setting the css display property to none.
	 * 
	 * @param driver The WebDriver
	 * @param webElement The WebElement to hide
	 */
	public static void noneDisplayWebElement(WebDriver driver, WebElement webElement) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].style.display = 'none';",  webElement);
	}

	/**
	 * Hides the webElement by setting the css visibility property to hidden.
	 * 
	 * @param driver The WebDriver
	 * @param webElement The WebElement to hide
	 */
	public static void hideWebElement(WebDriver driver, WebElement webElement) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].style.visibility = 'hidden';",  webElement);
	}
	
	/**
	 * Implementation of an own ExpectedCondition. <br>
	 * An expectation for checking that the number of windows has increased by one.
	 * 
	 * @param oldWindowCount the number of windows before opening a new window
	 * @return <code>true</code> when a new window is opened.
	 */
	public static ExpectedCondition<Boolean> newWindowOpened(final int oldWindowCount){
		return new ExpectedCondition<Boolean>() {
		
	      @Override
	      public Boolean apply(WebDriver webDriver) {
	    	  int currentWindowCount = webDriver.getWindowHandles().size();
	    	  
	    	  return currentWindowCount == oldWindowCount + 1;
	      }
	
	      @Override
	      public String toString() {
	    	  return "number of windows = " + oldWindowCount + " + 1";
	      }
	   };
	}
	
}
