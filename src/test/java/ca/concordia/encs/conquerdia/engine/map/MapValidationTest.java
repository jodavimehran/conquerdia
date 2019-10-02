package ca.concordia.encs.conquerdia.engine.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * This class Tests the behavior of {@link MapValidation} class.
 *
 * @author Sadegh Aalizadeh
 */
public class MapValidationTest {

    //Build The World
    private MapValidation mapValidation;
    private Continent asia;
    private WorldMap worldMap;

    /**
     * All common activities are placed here
     */
    @Before
    public void setUp() {
        worldMap = new WorldMap();
        //CONTINENTS
        //Build Asia as a continent
        asia = new Continent.Builder("Asia")
                .setValue(1)
                .setWorldMap(worldMap)
                .build();
        asia.addContinentToWorldMap();

        //Build Europe as a continent
        Continent europe = new Continent.Builder("Europe")
                .setValue(3)
                .setWorldMap(worldMap)
                .build();
        asia.addContinentToWorldMap();

        //Build sample countries in Asia and determine their adjacent countries
        Country iran = new Country.Builder("Iran", asia).build();
        iran.addCountry();

        Country saudiArabia = new Country.Builder("Saudi Arabia", asia).build();
        saudiArabia.addCountry();

        Country armenia = new Country.Builder("Armenia", asia).build();
        armenia.addCountry();

        Country turkey = new Country.Builder("Turkey", asia).build();
        turkey.addCountry();

        //Build a sample country in Europe and add its adjacent.
        Country greece = new Country.Builder("Greece", europe).build();
        greece.addCountry();

        Set<Country> iranAdjacentCountries = new HashSet<Country>();
        iranAdjacentCountries.add(saudiArabia);
        iranAdjacentCountries.add(armenia);
        iranAdjacentCountries.add(turkey);
        iran.getAdjacentCountries().addAll(iranAdjacentCountries);

        Set<Country> saudiArabiaAdjacentCountries = new HashSet<Country>();
        saudiArabiaAdjacentCountries.add(iran);
        saudiArabiaAdjacentCountries.add(turkey);
        saudiArabia.getAdjacentCountries().addAll(saudiArabiaAdjacentCountries);

        Set<Country> armeniaAdjacentCountries = new HashSet<Country>();
        armeniaAdjacentCountries.add(iran);
        armenia.getAdjacentCountries().addAll(armeniaAdjacentCountries);

        Set<Country> turkeyAdjacentCountries = new HashSet<Country>();
        turkeyAdjacentCountries.add(saudiArabia);
        turkeyAdjacentCountries.add(iran);
        turkeyAdjacentCountries.add(greece);
        turkey.getAdjacentCountries().addAll(turkeyAdjacentCountries);

        Set<Country> greeceAdjacentCountries = new HashSet<Country>();
        greeceAdjacentCountries.add(turkey);
        greece.getAdjacentCountries().addAll(greeceAdjacentCountries);

        mapValidation = new MapValidation(worldMap);
    }

    /**
     * This TestCase is designed to check the result of
     * {@link MapValidation#isContinentAConnectedSubGraphOfWorldMap(Continent)} method in {@link MapValidation} class.
     */
    @Test
    public void isContinentAConnectedSubGraphOfWorldMapTestCase() {
        // Check if the continent is a valid Subgraph of the Map
        assertTrue(mapValidation.isContinentAConnectedSubGraphOfWorldMap(asia));
    }

    /**
     * This TestCase is designed to check the result of {@link MapValidation#isMapAConnectedGraph()}
     * method in {@link MapValidation} class
     */
    @Test
    public void isMapAConnectedGraphTestCase() {
        assertTrue(mapValidation.isMapAConnectedGraph());
    }

    /**
     * This TestCase is designed to check the result of {@link MapValidation#isEeachCountryBelongingToOnlyOneContinent()}
     * in {@link MapValidation} class.
     */
    @Test
    public void isEeachCountryBelongingToOnlyOneContinentTestCase() {
        assertTrue(mapValidation.isEeachCountryBelongingToOnlyOneContinent());
    }

    /**
     * This TestCase is designed to check the result of {@link MapValidation#checkAllMapValidationRules()}
     * in {@link MapValidation} class.
     */
    @Test
    public void checkAllMapValidationRulesTestCase() {
        assertTrue(mapValidation.checkAllMapValidationRules());
    }

    /**
     * This TestCase is designed to check the result of {@link MapValidation#isAllContinentsAConnectedSubgraphofWorldMap(Set)}
     * in {@link MapValidation} class.
     */
    @Test
    public void isAllContinentsAConnectedSubgraphofWorldMap() {
        assertTrue(mapValidation.isAllContinentsAConnectedSubgraphofWorldMap(worldMap.getContinents()));
    }

}
