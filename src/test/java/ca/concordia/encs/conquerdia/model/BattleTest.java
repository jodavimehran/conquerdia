package ca.concordia.encs.conquerdia.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;

/**
 * Test for the {@link Battle} class
 * 
 * @author Mosabbir
 *
 */
public class BattleTest {

	/**
	 * Attacking country
	 */
	private static Country attackingCountry;

	/**
	 * Defending country
	 */
	private static Country defendingCountry;

	private static WorldMap worldMap;
	private static PlayersModel playersController;

	private static Player p1, p2;

	/**
	 * All common activities are placed here
	 * 
	 * @throws ValidationException
	 */
	@BeforeClass
	public static void setUp() throws ValidationException {
		createMap();
		addPlayers();
		p1.addCountry(worldMap.getCountry("Iran"));
		p1.addCountry(worldMap.getCountry("Armenia"));
		p2.addCountry(worldMap.getCountry("Greece"));
	}

	/**
	 * Runs before each tests
	 */
	@Before
	public void beforeTests() {
		Player player = playersController.getCurrentPlayer();
		player.setBattle(null);
		attackingCountry = worldMap.getCountry("Iran");
		defendingCountry = worldMap.getCountry("Greece");
		worldMap.getCountry("Armenia").setNumberOfArmies(1);
		attackingCountry.setNumberOfArmies(2);
		defendingCountry.setNumberOfArmies(1);

	}

	/**
	 * Runs after each tests
	 */
	@After
	public void AfterTests() {
	}

	/**
	 * Test dice count generation for attacker
	 */
	@Test
	public void testGetMaxDiceCountForAttacker() {

		Battle battle = new Battle(attackingCountry, defendingCountry);
		attackingCountry.setNumberOfArmies(1);
		assertEquals(0, battle.getNumberOfAttackerDices());
	}

	/**
	 * Test if attacker can attack
	 */
	@Test
	public void testAttackerValidation() {

		// Check if attacker can attack with one army
		attackingCountry.setNumberOfArmies(1);
		boolean canAttack = p1.canPerformAttack();
		assertFalse(canAttack);

		// Check if attacker can attack with 2 armies
		attackingCountry.setNumberOfArmies(2);
		canAttack = p1.canPerformAttack();

		assertTrue(canAttack);
	}

	/**
	 * Test if defender can defend
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void testDefenderValidation() throws ValidationException {

		Player player = playersController.getCurrentPlayer();
		boolean canDefend = player.canPerformDefend();
		assertFalse(canDefend);
		player.attack("Iran", "Greece", 1, false, false);
		canDefend = player.canPerformDefend();
		assertTrue(canDefend);
	}

	/**
	 * Test if player can move valid no of armies after conquer
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void testValidMoveAfterConquer() throws ValidationException {

		Player player = playersController.getCurrentPlayer();
		boolean isConquerd = false;

		for (int i = 0; i < 100; i++) {
			attackingCountry.setNumberOfArmies(Integer.MAX_VALUE);
			player.attack("Iran", "Greece", 1, true, false);

			isConquerd = player.getBattle().isConquered();
			if (isConquerd) {
				assertTrue(isConquerd);
				break;
			}
		}

		if (isConquerd) {
			// Check valid move
			int lastNumDice = player.getBattle().getNumberOfAttackerDices();
			attackingCountry.setNumberOfArmies(lastNumDice + 1);

			try {
				player.attackMove(lastNumDice - 1);
			} catch (Exception ex) {
				// Cannot move less army than last dices count rolled by the attacker
				assertNotNull(ex);
			}

			player.attackMove(lastNumDice);
		}
	}

	/**
	 * Test if player can move valid no of armies after conquer
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void testEndOfGame() throws ValidationException {
		/*
		 * Player player = playersController.getCurrentPlayer(); boolean isConquerd =
		 * false;
		 * 
		 * // Do 100 times to increase the chances of winning for (int i = 0; i < 100;
		 * i++) { attackingCountry.setNumberOfArmies(Integer.MAX_VALUE);
		 * player.attack("Iran", "Greece", 1, true, false);
		 * 
		 * isConquerd = player.getBattle().isConquered(); if (isConquerd) {
		 * assertTrue(isConquerd); break; } }
		 * 
		 * if (isConquerd) { // Check valid move int lastNumDice =
		 * player.getBattle().getNumberOfAttackerDices();
		 * attackingCountry.setNumberOfArmies(lastNumDice + 1);
		 * 
		 * try { player.attackMove(lastNumDice - 1); } catch (Exception ex) { // Cannot
		 * move less army than last dices count rolled by the attacker
		 * assertNotNull(ex); }
		 * 
		 * player.attackMove(lastNumDice); }
		 */
	}

	/**
	 * Add players
	 */
	private static void addPlayers() throws ValidationException {
		PlayersModel.clear();
		playersController = PlayersModel.getInstance();
		playersController.addPlayer("p1", "human");
		playersController.addPlayer("p2", "human");
		Object[] players = playersController.getPlayers().toArray();
		p1 = (Player) players[0];
		p2 = (Player) players[1];
	}

	/**
	 * Create a map for testing
	 * 
	 * @throws ValidationException
	 */
	private static void createMap() throws ValidationException {

		WorldMap.clear();
		worldMap = WorldMap.getInstance();

		// CONTINENTS
		worldMap.addContinent("Asia", 1);
		worldMap.addContinent("Europe", 3);

		// Build sample countries for Asia
		worldMap.addCountry("Iran", "Asia");
		worldMap.addCountry("Armenia", "Asia");

		// Country in europe
		worldMap.addCountry("Greece", "Europe");

		// Iran = Armenia
		// Iran = Greece
		worldMap.addNeighbour("Iran", "Armenia");
		worldMap.addNeighbour("Iran", "Greece");
	}

	/**
	 * Clear {@link WorldMap} object with instance. This method is run after all the
	 * tests are run
	 */
	@AfterClass
	public static void end() {
		PlayersModel.clear();
		WorldMap.clear();
	}
}
