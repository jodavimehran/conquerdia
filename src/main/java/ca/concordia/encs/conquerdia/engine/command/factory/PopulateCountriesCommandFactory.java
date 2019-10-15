package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements the Populate Country Command ("populatecountries")
 */
public class PopulateCountriesCommandFactory implements CommandFactory {

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        return Arrays.asList(() -> model.populateCountries());
    }
}