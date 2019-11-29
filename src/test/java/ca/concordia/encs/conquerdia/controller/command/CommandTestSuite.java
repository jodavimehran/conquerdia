package ca.concordia.encs.conquerdia.controller.command;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	DefendCommandTest.class,
	CommandTypeTest.class,
	AttackPhaseCommandTest.class,
	AttackMoveCommandTest.class,
	AttackCommandTest.class,
	SaveGameCommandTest.class,
	LoadGameCommandTest.class
})
/**
 * 
 * @author Sadegh Aalizadeh
 *
 */
public class CommandTestSuite {

}
