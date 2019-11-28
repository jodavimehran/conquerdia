package ca.concordia.encs.conquerdia.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;

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
	Country attackingCountry;

	/**
	 * Defending country
	 */
	Country defendingCountry;

	/**
	 * All common activities are placed here
	 */
	@Before
	public void setUp() {
		attackingCountry = new Country.Builder("Greece", new Continent.Builder("Europe").build()).build();
		defendingCountry = new Country.Builder("Iran", new Continent.Builder("Asia").build()).build();
	}

	/**
	 * Test dice count generation for attacker
	 */
	@Test
	public void testGetMaxDiceCountForAttacker() {

		Battle battle = new Battle(attackingCountry, defendingCountry);
		attackingCountry.placeArmy(1);
		assertEquals(0, battle.getNumberOfAttackerDices());
	}
}
