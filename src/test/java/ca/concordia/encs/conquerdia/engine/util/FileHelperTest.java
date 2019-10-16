package ca.concordia.encs.conquerdia.engine.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class to test the functionalities of {@link FileHelper} class
 */
public class FileHelperTest {

	/**
	 * Checks the extraction of filename method
	 */
	@Test
	public void testFileNameWithoutExtension() {
		assertEquals(FileHelper.getFileNameWithoutExtension("uk.mp"), "uk");
	}
}
