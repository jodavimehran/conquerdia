package ca.concordia.encs.conquerdia.engine.map;

import org.junit.Before;
import org.junit.Test;

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
        worldMap.addContinent("Asia", 1);
        worldMap.addContinent("Europe", 3);


        //Build sample countries in Asia and determine their adjacent countries
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
