package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.exception.ValidationException;

public class AttackCommandTest {
	/**
	 * Setup the data for attack validations
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}
	/**
	 * test Attack command	validation rules
	 */
	@Test
	public void testAttackCOmmand() {
		AttackCommand attackCommand = new AttackCommand();
		List<String> list = new ArrayList<String>();
		list.add("attack");
		list.add("Greece");
		list.add("Iran");
		list.add("-5");
		String message = null;
		try {
			attackCommand.runCommand(list);
		} catch (ValidationException ex) {
			message = ex.getMessage();
		}
		assertTrue(message.contains("must be a positive integer"));
	}
	/**
	 * Test validateNoAttack method of AttackCOmmand Class
	 */
	@Test
	public void testValidateNoAttackCommand() {
		
	}
}
