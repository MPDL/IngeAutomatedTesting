package main.java.pages.workspaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BatchWorkspaceViewPage extends BatchWorkspacePage {

	public BatchWorkspaceViewPage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

}
