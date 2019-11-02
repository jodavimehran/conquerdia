package ca.concordia.encs.conquerdia.controller.command;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Abstraction for attack phase commands tests
 */
public abstract class AttackPhaseCommandTest {

	protected static WorldMap map;

	@BeforeClass
	public static void onStart() throws ValidationException {
		map = createMapForTest();
	}

	public static WorldMap createMapForTest() throws ValidationException {

		WorldMap map = WorldMap.getInstance();

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

		return map;
	}

	@AfterClass
	public static void onFinish() {
		if (map != null) {
			map.clear();
		}
	}
}
