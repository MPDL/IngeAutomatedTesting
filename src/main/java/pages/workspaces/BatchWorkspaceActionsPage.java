package main.java.pages.workspaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BatchWorkspaceActionsPage extends BatchWorkspacePage {

	@FindBy(xpath = "//a[text()='Delete']")
	private WebElement deleteLink;

	@FindBy(xpath = "//a[text()='Submit']")
	private WebElement submitLink;

	@FindBy(xpath = "//a[text()='Add']")
	private WebElement addLocalTagLink;

	@FindBy(xpath = "//input[contains(@id, ':inputChangeLocalTags_add')]")
	private WebElement addLocalTagInput;

	@FindBy(xpath = "//a[text()='Change genre']")
	private WebElement changeGenreLink;

	@FindBy(xpath = "//select[contains(@id, ':selChangeGenreFrom')]")
	private WebElement oldGenreDropdown;

	@FindBy(xpath = "//select[contains(@id, ':selChangeGenreTo')]")
	private WebElement newGenreDropdown;

	public BatchWorkspaceActionsPage(WebDriver driver) {
		super(driver);

		PageFactory.initElements(driver, this);
	}

	public BatchWorkspaceProtocolPage changeGenre(String oldGenre, String newGenre) {
		Select selectOldGenre = new Select(oldGenreDropdown);
		selectOldGenre.selectByVisibleText(oldGenre);

		Select genreSelect = new Select(newGenreDropdown);
		genreSelect.selectByVisibleText(newGenre);

		changeGenreLink.click();

		return new BatchWorkspaceProtocolPage(driver);
	}

	public BatchWorkspaceProtocolPage addLocalTag(String localTag) {
		this.addLocalTagInput.sendKeys(localTag);

		this.addLocalTagLink.click();

		return new BatchWorkspaceProtocolPage(driver);
	}

	public BatchWorkspaceProtocolPage submit() {
		this.submitLink.click();

		return new BatchWorkspaceProtocolPage(driver);
	}

	public BatchWorkspaceProtocolPage delete() {
		this.deleteLink.click();

		return new BatchWorkspaceProtocolPage(driver);
	}

}
