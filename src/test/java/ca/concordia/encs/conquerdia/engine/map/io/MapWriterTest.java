package ca.concordia.encs.conquerdia.engine.map.io;

import org.junit.Before;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class MapWriterTest {
	private WorldMap worldMap;

	@Before
	public void beforeTests() {
		worldMap = new WorldMap();

		// CONTINENTS
		worldMap.addContinent("Asia", 1);
		worldMap.addContinent("Europe", 3);

		// Build sample countries in Asia and determine their adjacent countries
		worldMap.addCountry("Iran", "Asia");
		worldMap.addCountry("Saudi Arabia", "Asia");
		worldMap.addCountry("Armenia", "Asia");
		worldMap.addCountry("Turkey", "Asia");
		worldMap.addCountry("Greece", "Europe");

		worldMap.addNeighbour("Iran", "Saudi Arabia");
		worldMap.addNeighbour("Iran", "Armenia");
		worldMap.addNeighbour("Iran", "Turkey");
		worldMap.addNeighbour("Saudi Arabia", "Turkey");
		worldMap.addNeighbour("Greece", "Turkey");
	}
}
