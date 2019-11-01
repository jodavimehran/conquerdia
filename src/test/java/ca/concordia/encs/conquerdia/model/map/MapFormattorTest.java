package ca.concordia.encs.conquerdia.model.map;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Player;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MapFormattorTest {

    private static WorldMap map;

    /**
     * All common activities are placed here
     */
    @BeforeClass
    public static void setUpOnce() throws ValidationException {
        map = WorldMap.getInstance();

        map.addContinent("South-West-England", 5);
        map.addCountry("Ross-shire", "South-West-England");
        map.addCountry("Ross-shire-and-Chromartyshire", "South-West-England");
        map.addCountry("Kincardine-shire", "South-West-England");
        map.addCountry("Northamptonshire_Northamptonshire", "South-West-England");

        map.addNeighbour("Ross-shire", "Ross-shire-and-Chromartyshire");
        map.addNeighbour("Ross-shire", "Kincardine-shire");
        map.addNeighbour("Ross-shire", "Northamptonshire_Northamptonshire");

        map.getCountry("Ross-shire").placeArmy(5);
        map.getCountry("Ross-shire-and-Chromartyshire").placeArmy(1);
        map.getCountry("Kincardine-shire").placeArmy(4);
        map.getCountry("Northamptonshire_Northamptonshire").placeArmy(15);

        Player p1 = new Player.Builder("John").build();
        map.getCountry("Ross-shire").setOwner(p1);
        map.getCountry("Northamptonshire_Northamptonshire").setOwner(p1);

        map.getCountry("Kincardine-shire").setOwner(new Player.Builder("Robert").build());
        map.getCountry("Ross-shire-and-Chromartyshire").setOwner(new Player.Builder("Doe").build());

    }

    @AfterClass
    public static void cleanup() {
        WorldMap.getInstance().clear();
    }

    @Test
    public void testDefaultMapFormattor() {
        MapFormattor formattor = new MapFormattor(map);
        String res = formattor.format();
        assertTrue(res.contains("Northamptonshire_Northamptonshire"));
    }

    @Test
    public void testDetailMapFormattor() {
        MapFormattor formattor = new MapFormattor(map);
        String res = formattor.format(MapFormattor.FormatType.Detail);
        assertTrue(res.contains("Northamptonshire_Northamptonshire| 15    | John"));
    }

    @Test
    public void testPresenseOfEmptyContinents() throws ValidationException {
        map.addContinent("Antarctica", 8);
        MapFormattor formattor = new MapFormattor(map);
        String res = formattor.format(MapFormattor.FormatType.Detail);
        assertTrue(res.contains("Antarctica"));
    }
}

