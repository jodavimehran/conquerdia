package ca.concordia.encs.conquerdia.engine.map.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.map.Continent;
import ca.concordia.encs.conquerdia.engine.map.Country;
import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class MapReaderTest {

	private WorldMap worldMap;
	private IMapReader reader;
	private boolean isLoaded;

	@Before
	public void beforeTests() {
		worldMap = new WorldMap();
		reader = new MapReader(worldMap);
		isLoaded = reader.readMap("uk.map");
	}

	@Test
	public void testReadMap() {
		assertTrue(isLoaded);
	}

	@Test
	public void testContinentCount() {
		// uk map has 6 continents,
		assertEquals(worldMap.getContinents().size(), 6);
	}

	@Test
	public void testCountryCount() {
		// uk map has 75 countries,
		assertEquals(worldMap.getCountries().size(), 75);
	}

	@Test
	public void testNeighbors() {
		// uk map's last border row is 75 73 74 71
		// i.e. country kent is adjacent to Surrey, Sussex, Essex
		Country kent = worldMap.getCountry("Kent");
		assertTrue(kent.isAdjacentTo("Surrey"));
		assertTrue(kent.isAdjacentTo("Sussex"));
		assertTrue(kent.isAdjacentTo("Essex"));
	}

	@Test
	public void testContinentDetail() {
		// Uk map has a continent row like: North-Scotland 2 blue
		Continent continent = worldMap.getContinent("North-Scotland");
		assertNotNull(continent);
		assertEquals(continent.getValue(), 2);
	}

	@Test
	public void testCountryDetail() {
		// Uk map has a country row like: 18 Ayrshire 2 423 136
		Country country = worldMap.getCountry("Ayrshire");
		assertNotNull(country);

		// Ayshire belongs to continent 2 South-Scotland
		assertEquals(country.getContinent().getName(), "South-Scotland");
	}
}
