package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This class is temporarily disabled due to file path issues which are still
 * need to be solved for Travis CI. The test works with local machine.
 * <p>
 * The class tests the writeMap functionalatieis
 *
 * @author Mosabbir
 */
@Ignore
public class MapWriterTest {
    private WorldMap worldMap;
    private IMapWriter writer;
    private boolean isWriteSuccessful;

    /**
     * Runs before every tests
     */
    @Before
    public void beforeTests() throws ValidationException {
        worldMap = WorldMap.getInstance();

        // CONTINENTS
        worldMap.addContinent("Asia", 1);
        worldMap.addContinent("Europe", 3);

        // Build sample countries in Asia and determine their adjacent countries
        worldMap.addCountry("Iran", "Asia");
        worldMap.addCountry("Saudi-Arabia", "Asia");
        worldMap.addCountry("Armenia", "Asia");
        worldMap.addCountry("Turkey", "Asia");
        worldMap.addCountry("Greece", "Europe");

        worldMap.addNeighbour("Iran", "Saudi-Arabia");
        worldMap.addNeighbour("Iran", "Armenia");
        worldMap.addNeighbour("Iran", "Turkey");
        worldMap.addNeighbour("Saudi-Arabia", "Turkey");
        worldMap.addNeighbour("Greece", "Turkey");

        writer = new MapWriter(worldMap);
        isWriteSuccessful = writer.writeMap("test");
    }

    /**
     * Check if the map is successfully written to the file
     */
    @Test
    public void testWriteMap() {
        assertTrue(isWriteSuccessful);
    }
}
