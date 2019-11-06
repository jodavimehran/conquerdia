import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.concordia.encs.conquerdia.controller.command.CommandTestSuite;
import ca.concordia.encs.conquerdia.model.PlayerTestSuite;
import ca.concordia.encs.conquerdia.model.map.MapTestSuite;
import ca.concordia.encs.conquerdia.model.map.io.FileTestSuite;

@RunWith(Suite.class)
@SuiteClasses({CommandTestSuite.class,
	PlayerTestSuite.class,
	MapTestSuite.class,
	FileTestSuite.class})
public class AllTests {

}
