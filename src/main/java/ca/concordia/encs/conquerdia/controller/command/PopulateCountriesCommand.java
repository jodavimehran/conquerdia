package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements the Populate Country Command ("populatecountries")
 */
public class PopulateCountriesCommand implements Command {

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> execute(GameModel model, List<String> inputCommandParts) {
        return Arrays.asList(model.populateCountries());
    }
}