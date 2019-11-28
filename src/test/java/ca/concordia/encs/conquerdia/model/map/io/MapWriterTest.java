package ca.concordia.encs.conquerdia.model.map.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * The class tests the writeMap functionalities
 *
 * @author Mosabbir
 */

public class MapWriterTest {
	/**
	 * Worldmap to work on
	 */
	private static WorldMap worldMap;

	/**
	 * Checks if map writer is successful
	 */
	private static boolean isWriteSuccessful;

	/**
	 * Absolute path to the map file
	 */
	private static Path mapFullPath;

	/**
	 * Runs once before tests starts
	 * 
	 * @throws ValidationException
	 */
	@BeforeClass
	public static void setup() throws ValidationException {
		WorldMap.clear();
		worldMap = WorldMap.getInstance();

		// CONTINENTS
		worldMap.addContinent("Asia", 1);
		worldMap.addContinent("Europe", 3);

		// Build sample countries in Asia and determine their adjacent countries
		worldMap.addCountry("Iran", "Asia");
		worldMap.addCountry("Saudi-Arabia", "Asia");
		worldMap.addCountry("Armenia", "Asia");
		worldMap.addCountry("Turkey", "Asia");
		worldMap.addCountry("Greece", "Europe");

		worldMap.addNeighbour("Iran", "Saudi-Arabia");
		worldMap.addNeighbour("Iran", "Armenia");
		worldMap.addNeighbour("Iran", "Turkey");
		worldMap.addNeighbour("Saudi-Arabia", "Turkey");
		worldMap.addNeighbour("Greece", "Turkey");

		MapWriter writer = new MapWriter(worldMap);
		String map = "test.map";
		try {
			mapFullPath = Paths.get(MapIO.getMapFilePath(map));
			Files.deleteIfExists(mapFullPath);
			isWriteSuccessful = writer.writeMap(map);
			assumeTrue(FileHelper.exists(mapFullPath.toString()));
		} catch (IOException e) {
			e.printStackTrace();
			assumeNoException(e);
		}
	}

	/**
	 * Clear {@link WorldMap} object with instance. This method is run after all the
	 * tests are run
	 */
	@AfterClass
	public static void end() {
		WorldMap.clear();
		try {
			Files.deleteIfExists(mapFullPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the map is successfully written to the file
	 */
	@Test
	public void testWriteMap() {
		assertTrue(isWriteSuccessful);
	}

	/**
	 * Test if map contains continent properly
	 */
	@Test
	public void testContinent() {
		if (Files.isReadable(mapFullPath)) {
			try {
				List<String> lines = Files.readAllLines(mapFullPath);
				assertTrue(lines.contains("Asia 1 Black"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Test if country exists
	 */
	@Test
	public void testCountry() {
		if (Files.isReadable(mapFullPath)) {
			try {
				List<String> lines = Files.readAllLines(mapFullPath);
				assertTrue(lines.contains("1 Armenia 1 0 0"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Check if border exists
	 */
	@Test
	public void testBorder() {
		if (Files.isReadable(mapFullPath)) {
			try {
				List<String> lines = Files.readAllLines(mapFullPath);
				assertTrue(lines.contains("2 5 3 4"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
