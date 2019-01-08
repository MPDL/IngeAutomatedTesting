package test.java.base;

import org.testng.annotations.AfterClass;

import main.java.pages.StartPage;
import main.java.pages.homepages.HomePage;

/**
 * Superclass for all test classes containing tests with logged in users.
 * 
 * @author helk
 *
 */
public abstract class BaseLoggedInUserTest extends BaseTest{

	//TODO: Add @BeforeClass login()-Method
		
	/**
	 * Log out the user after navigating to the HomnePage.
	 * Independent of the user type.
	 * 
	 * @return the StartPage
	 */
	// PM-91
	@AfterClass
	public StartPage logout() {
		HomePage homePage = navigateToHomePage();
		return homePage.logout();
	}
	
}