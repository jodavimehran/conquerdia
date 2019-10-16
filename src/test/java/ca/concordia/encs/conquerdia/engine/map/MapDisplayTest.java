package ca.concordia.encs.conquerdia.engine.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.util.MapFormattor;

public class MapDisplayTest {

	private Country country;
	private Continent testContinent;
	private Country country1;
	private Country country2;
	private Country country3;
	Map<String, Country> countries;

	/**
	 * All common activities are placed here
	 */
	@Before
	public void setUp() {
		countries = new HashMap<>();
		testContinent = new Continent.Builder("testContinent").build();
		country1 = new Country.Builder("one", testContinent).build();
		country2 = new Country.Builder("two", testContinent).build();
		country3 = new Country.Builder("tree", testContinent).build();
		country = new Country.Builder("test", testContinent).build();
		countries.put(country1.getName(), country2);
		countries.put(country2.getName(), country2);
		countries.put(country3.getName(), country3);
	}

	@Test
	public void testShowMapForEditing() {
		WorldMap worldMap = new WorldMap();
		worldMap.loadMap("uk.map");

		System.out.println(worldMap.toString());
	}

	@Test
	public void testShowMapInGame() {
		MapFormattor formattor = new MapFormattor(countries);
		String str = formattor.format();
		System.out.println(str);
	}
}
