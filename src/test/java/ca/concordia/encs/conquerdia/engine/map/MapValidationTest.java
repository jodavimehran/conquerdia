package ca.concordia.encs.conquerdia.engine.map;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
/**
 *This class Tests the behavior of {@link MapValidation} class.
 * @author Sadegh Aalizadeh
 */
public class MapValidationTest {

	ConquerdiaModel model = new ConquerdiaModel();
	//Build The World
	WorldMap  worldMap = model.getWorldMap();
	//Build Asia as a continent 
	Continent asia = new Continent.Builder("Asia")
            .setValue(1)
            .setWorldMap(model.getWorldMap())
            .build();
	//Build Europe as a continent 
	Continent europe = new Continent.Builder("Europe")
            .setValue(3)
            .setWorldMap(model.getWorldMap())
            .build();
	/**
     * All common activities are placed here
     */
    @Before
    public void setUp() {

		Set<Continent> continents= new HashSet<Continent>();
		continents.add(asia);
		continents.add(europe);
		worldMap.setContinents(continents);
		


		//Build sample countries in Asia and determine their adjacent countries
		Country iran = new Country.Builder(1, "Iran").placedIn(asia).build();
		Country saudiArabia = new Country.Builder(2, "Saudi Arabia").placedIn(asia).build();
		Country armenia = new Country.Builder(3, "Armenia").placedIn(asia).build();
		Country turkey = new Country.Builder(4, "Turkey").placedIn(asia).build();
		
		//Build a sample country in Europe and add its adjacent.
		Country greece = new Country.Builder(5, "Greece").placedIn(europe).build();
		
		Set<Country> iranAdjacentCountries = new HashSet<Country>();
		iranAdjacentCountries.add(saudiArabia);
		iranAdjacentCountries.add(armenia);
		iranAdjacentCountries.add(turkey);
		iran.setAdjacentCountries(iranAdjacentCountries);
		
		Set<Country> saudiArabiaAdjacentCountries = new HashSet<Country>();
		saudiArabiaAdjacentCountries.add(iran);
		saudiArabiaAdjacentCountries.add(turkey);
		saudiArabia.setAdjacentCountries(saudiArabiaAdjacentCountries);
		
		Set<Country> armeniaAdjacentCountries = new HashSet<Country>();
		armeniaAdjacentCountries.add(iran);
		armenia.setAdjacentCountries(armeniaAdjacentCountries);
		
		Set<Country> turkeyAdjacentCountries = new HashSet<Country>();
		turkeyAdjacentCountries.add(saudiArabia);
		turkeyAdjacentCountries.add(iran);
		turkeyAdjacentCountries.add(greece);
		turkey.setAdjacentCountries(turkeyAdjacentCountries);
		
		Set<Country> asiaCountries = new HashSet<Country>();
		asiaCountries.add(iran);
		asiaCountries.add(saudiArabia);
		asiaCountries.add(armenia);
		asiaCountries.add(turkey);
		
		
		//Set the countries that are placed in Asia.
		asia.setCountries(asiaCountries);
		

		Set<Country> greeceAdjacentCountries = new HashSet<Country>();
		greeceAdjacentCountries.add(turkey);	
		greece.setAdjacentCountries(greeceAdjacentCountries);
		
		Set<Country> europeCountries = new HashSet<Country>();
		europeCountries.add(greece);
		
		
		//Set the countries that are placed in Europe.
		europe.setCountries(europeCountries);

    }
	
	/**
	 * This TestCase is designed to check the result of 
	 * {@link MapValidation#isContinentAConnectedSubGraphOfWorldMap(Continent)} method in {@link MapValidation} class.
	 */
	@Test 
	public void isContinentAConnectedSubGraphOfWorldMapTestCase(){	
		MapValidation mapValidation = new MapValidation(model.getWorldMap());
		// Check if the continent is a valid Subgraph of the Map
		assertTrue(mapValidation.isContinentAConnectedSubGraphOfWorldMap(asia));
	}
	/**
	 * This TestCase is designed to check the result of {@link MapValidation#isMapAConnectedGraph(WorldMap)} in {@link MapValidation} calss
	 */
	@Test 
	public void isMapAConnectedGraphTestCase(){
				
		MapValidation mapValidation = new MapValidation(model.getWorldMap());
		assertTrue(mapValidation.isMapAConnectedGraph(worldMap));
	}
	
}
