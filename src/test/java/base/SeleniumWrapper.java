package test.java.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	
}
