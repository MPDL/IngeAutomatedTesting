package main.java.pages.workspaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public abstract class BatchWorkspacePage extends BasePage {

	@FindBy(xpath = "//a[contains(@id, ':lnkList_lblActions')]")
	private WebElement actionsLink;

	public BatchWorkspacePage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

	public BatchWorkspaceActionsPage openActionsView() {
		this.actionsLink.click();

		return new BatchWorkspaceActionsPage(driver);
	}

}
