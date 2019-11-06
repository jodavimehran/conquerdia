package ca.concordia.encs.conquerdia.controller.command;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Abstraction for attack phase commands tests
 */
@Ignore
public abstract class AttackPhaseCommandTest {

	protected static WorldMap map;
	protected static PhaseModel phaseModel;

	@BeforeClass
	public static void onStart() throws ValidationException {
		map = createMapForTest();
		phaseModel = PhaseModel.getInstance();
	}

	public static WorldMap createMapForTest() throws ValidationException {

		WorldMap map = WorldMap.getInstance();
		map.clear();
		map.addContinent("Africa", 5);
		map.addCountry("Greece", "Africa");
		map.addCountry("Germany", "Africa");
		map.addCountry("Italy", "Africa");
		map.addCountry("France", "Africa");

		map.addNeighbour("Greece", "Germany");
		map.addNeighbour("Greece", "Italy");
		map.addNeighbour("Greece", "France");

		map.getCountry("Greece").placeArmy(5);
		map.getCountry("Germany").placeArmy(1);
		map.getCountry("Italy").placeArmy(4);
		map.getCountry("France").placeArmy(15);

		Player p1 = new Player.Builder("John").build();
		map.getCountry("Greece").setOwner(p1);
		map.getCountry("France").setOwner(p1);

		map.getCountry("Italy").setOwner(new Player.Builder("Robert").build());
		map.getCountry("Germany").setOwner(new Player.Builder("Doe").build());
		
		return map;
	}

	@AfterClass
	public static void onFinish() {
		if (map != null) {
			map.clear();
		}
	}
}
