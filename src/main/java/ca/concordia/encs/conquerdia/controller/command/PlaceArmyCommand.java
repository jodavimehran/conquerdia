package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.Arrays;
import java.util.List;

public class PlaceArmyCommand extends AbstractCommand {
    private static final String COMMAND_HELP_MSG = "A valid \"placearmy\" command is something like \"placearmy countryname\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.PLACE_ARMY;
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
        return Arrays.asList(GameModel.getInstance().placeArmy(inputCommandParts.get(1)));
    }
}