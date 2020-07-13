package main.java.pages.workspaces;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BatchWorkspaceProtocolPage extends BatchWorkspacePage {

	@FindBy(xpath = "//span[contains(@id, ':logStatus')]")
	private List<WebElement> logStateElements;

	public BatchWorkspaceProtocolPage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

	public List<String> getLogStates() {
		List<String> logStates = new ArrayList<>();
		logStateElements.forEach(logStateElement -> logStates.add(logStateElement.getText()));

		return logStates;
	}

	public String getLogState(String itemTitle) {
		WebElement itemTableRowElement = driver.findElement(By.xpath("//a[contains(@id, ':lnkItems') and text() = '" + itemTitle
				+ "']/ancestor::tr[contains(@class, 'listItem')]"));
		WebElement itemLogStatusElement = itemTableRowElement.findElement(By.xpath(".//span[contains(@id, ':logStatus')]"));

		return itemLogStatusElement.getText();
	}

}
