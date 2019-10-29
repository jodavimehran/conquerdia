package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements the Populate Country Command ("populatecountries")
 */
public class PopulateCountriesCommand extends AbstractCommand {
    private static final String COMMAND_HELP_MSG = "";

    @Override
    protected CommandType getCommandType() {
        return CommandType.POPULATE_COUNTRIES;
    }

    @Override
    protected String getCommandHelpMessage() {
        return COMMAND_HELP_MSG;
    }

    /**
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> runCommand(List<String> inputCommandParts) {
        return Arrays.asList(GameModel.getInstance().populateCountries());
    }
}