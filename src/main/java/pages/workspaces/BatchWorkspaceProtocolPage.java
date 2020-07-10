package main.java.pages.workspaces;

import java.util.ArrayList;
import java.util.List;

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

}
