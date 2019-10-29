package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.Arrays;
import java.util.List;

public class FortifyCommand extends AbstractCommand {

    private static final String COMMAND_HELP_MSG = "a valid \"fortify\" command is something like \"fortify fromcountry tocountry num\" or \"fortify none\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.FORTIFY;
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
        if ("none".equals(inputCommandParts.get(1))) {
            return null;
        }
        if (inputCommandParts.size() < 4)
            return Arrays.asList("Invalid input! " + getCommandHelpMessage());
        try {
            return Arrays.asList(PhaseModel.getInstance().getCurrentPlayer().fortify(inputCommandParts.get(1), inputCommandParts.get(2), Integer.valueOf(inputCommandParts.get(3))));
        } catch (NumberFormatException ex) {
            return Arrays.asList("Number of armies(latest parameter) must be an integer number.");
        }

    }
}
