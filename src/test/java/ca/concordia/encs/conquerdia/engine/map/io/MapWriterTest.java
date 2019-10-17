package ca.concordia.encs.conquerdia.engine.map.io;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class MapWriterTest {
	private WorldMap worldMap;
	private IMapWriter writer;
	private boolean isWriteSuccessful;

	@Before
	public void beforeTests() {
		worldMap = new WorldMap();
		worldMap.editMap("test");

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

		writer = new MapWriter(worldMap);
		isWriteSuccessful = writer.writeMap("test");
	}

	@Test
	public void testWriteMap() {
		//assertTrue(isWriteSuccessful);
	}
}
