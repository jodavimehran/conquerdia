package ca.concordia.encs.conquerdia.engine.map;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.map.MapFormattor;

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
		testContinent = new Continent.Builder("South-West-England").setValue(5).build();
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

		country1.placeArmy(5);
		country2.placeArmy(1);
		country3.placeArmy(4);
		country.placeArmy(15);

		Player p1 = new Player.Builder("John").build();
		country1.setOwner(p1);
		country.setOwner(p1);
		country3.setOwner(new Player.Builder("Robert").build());
		country2.setOwner(new Player.Builder("Doe").build());
	}

	@Test
	public void testDefaultMapFormattor() {
		MapFormattor formattor = new MapFormattor(countries);
		String res = formattor.format();
		assertTrue(res.contains("Northamptonshire_Northamptonshire"));
	}

	@Test
	public void testDetailMapFormattor() {
		MapFormattor formattor = new MapFormattor(countries);
		String res = formattor.format(MapFormattor.FormatType.Detail);
		assertTrue(res.contains("Northamptonshire_Northamptonshire| 15    | John"));
	}
}
