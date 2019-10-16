package ca.concordia.encs.conquerdia.engine.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This class Tests the behavior of {@link MapValidation} class.
 *
 * @author Sadegh Aalizadeh
 */
public class MapValidationTest {

    //Build The World
    private MapValidation mapValidation;
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
     * This TestCase is designed to check the result of {@link MapValidation#checkAllMapValidationRules()}
     * in {@link MapValidation} class.
     */
    @Test
    public void checkAllMapValidationRulesTestCase() {
        assertTrue(mapValidation.checkAllMapValidationRules());
    }
}
