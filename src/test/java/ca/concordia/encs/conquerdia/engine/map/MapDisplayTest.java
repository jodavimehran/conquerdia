package ca.concordia.encs.conquerdia.engine.map;

import static org.junit.Assert.assertTrue;

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
		testContinent = new Continent.Builder("South-West-England").build();
		country1 = new Country.Builder("Ross-shire", testContinent).build();
		country2 = new Country.Builder("Ross-shire-and-Chromartyshire", testContinent).build();
		country3 = new Country.Builder("Kincardine-shire", testContinent).build();
		country = new Country.Builder("Northamptonshire_Northamptonshire", testContinent).build();

		country1.addNeighbour(country2);
		country1.addNeighbour(country3);
		country1.addNeighbour(country);

		countries.put(country1.getName(), country1);
		countries.put(country2.getName(), country2);
		countries.put(country3.getName(), country3);
		countries.put(country.getName(), country);
	}

	@Test
	public void testShowMapForEditing() {
		MapFormattor formattor = new MapFormattor(countries);
		String str = formattor.format();
		assertTrue(str.contains("Kincardine-shire,Ross-shire-and-Chromartyshire,Northamptonshire_Northamptonshire"));
		//System.out.println(str);
	}

	@Test
	public void testShowMapInGame() {
	
	}
}
