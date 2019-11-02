package ca.concordia.encs.conquerdia.controller.command;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DefendCommandTest extends AttackPhaseCommandTest {

	@Test
	public void testCommandValidity() {
		DefendCommand defendCommand = new DefendCommand();
		List<String> list = new ArrayList<String>();
		list.add("defend");
		list.add("5");
		try {
			defendCommand.runCommand(list);
		} catch (Exception ex) {
		}
	}
}
