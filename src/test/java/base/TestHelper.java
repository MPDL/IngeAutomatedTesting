package test.java.base;

import org.testng.Assert;

/**
 * Helper class for the test classes.
 * 
 * @author helk
 *
 */
public class TestHelper {

	public static final String CONE_URL = TestSuiteInitialisation.PURE_URL + "/" + "cone";

	/**
	 * Compares (Assert.assertEquals) the actualIdentifierValue to the
	 * expectedIdentifierValue.
	 * 
	 * @param actualIdentifierValue
	 * @param expectedIdentifierType
	 * @param expectedIdentifierValue
	 */
	public static void compareIdentifiers(String actualIdentifierValue, String expectedIdentifierType,
			String expectedIdentifierValue) {
		if (TableHelper.CONE_IDENTIFIER_TYPE.equalsIgnoreCase(expectedIdentifierType)) {
			expectedIdentifierValue = CONE_URL + expectedIdentifierValue;
		}

		Assert.assertEquals(actualIdentifierValue, expectedIdentifierType + ": " + expectedIdentifierValue);
	}

	// TODO: Add more helper methods to compare the data in the data driven tests.

}
