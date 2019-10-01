package ca.concordia.encs.conquerdia.engine.map;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
/**
 *This class Tests the behavior of {@link MapValidation} class.
 * @author Sadegh Aalizadeh
 */
public class MapValidationTest {
	/**
	 * This TestCase is designed to check the result of 
	 * {@link MapValidation#isContinentAConnectedSubGraphOfWorldMap(Continent)} method in {@link MapValidation} class.
	 */
	@Test 
	public void isContinentAConnectedSubGraphOfWorldMapTestCase(){	
		//Build The World
		ConquerdiaModel model = new ConquerdiaModel();
		WorldMap  worldMap = model.getWorldMap();
		//Build Asia as a continent 
		Continent asia = new Continent.Builder("Asia")
                .setValue(1)
                .setWorldMap(model.getWorldMap())
                .build();
		//Build Africa as a continent 
		Continent africa = new Continent.Builder("Africa")
                .setValue(2)
                .setWorldMap(model.getWorldMap())
                .build();
		Set<Continent> continents= new HashSet<Continent>();
		continents.add(asia);
		continents.add(africa);
		worldMap.setContinents(continents);

		//Build  3 sample countries in Asia and determine their adjacent countries
		Country iran = new Country.Builder(1, "Iran").placedIn(asia).build();
		Country saudiArabia = new Country.Builder(2, "Saudi Arabia").placedIn(asia).build();
		Country armenia = new Country.Builder(3, "Armenia").placedIn(asia).build();

		Set<Country> iranAdjacentCountries = new HashSet<Country>();
		iranAdjacentCountries.add(saudiArabia);
		iranAdjacentCountries.add(armenia);
		iran.setAdjacentCountries(iranAdjacentCountries);
		
		Set<Country> saudiArabiaAdjacentCountries = new HashSet<Country>();
		saudiArabiaAdjacentCountries.add(iran);	
		saudiArabia.setAdjacentCountries(saudiArabiaAdjacentCountries);
		
		Set<Country> armeniaAdjacentCountries = new HashSet<Country>();
		armeniaAdjacentCountries.add(iran);
		armenia.setAdjacentCountries(armeniaAdjacentCountries);
		
		Set<Country> countries = new HashSet<Country>();
		countries.add(iran);
		countries.add(saudiArabia);
		countries.add(armenia);
		
		//Set the countries that are placed in Asia.
		asia.setCountries(countries);
		
		// Check if the continent is a valid Subgraph of the Map
		MapValidation mapValidation = new MapValidation(model.getWorldMap());
		assertTrue(mapValidation.isContinentAConnectedSubGraphOfWorldMap(asia));
	}
}
