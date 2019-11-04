package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

import java.util.List;

public class ReinforceCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "The \"reinforce\" command must has at least two parameter, first one is countryname and second one is number of armies.";

    @Override
    protected CommandType getCommandType() {
        return CommandType.REINFORCE;
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
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        try {
            String countryName = inputCommandParts.get(1);
            int numberOfArmy = Integer.valueOf(inputCommandParts.get(2));
            Player currentPlayer = PhaseModel.getInstance().getCurrentPlayer();
            currentPlayer.reinforce(countryName, numberOfArmy);
            phaseLogList.add(String.format("%s placed %d army/armies to %s.", currentPlayer.getName(), numberOfArmy, countryName));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Number of armies(latest parameter) must be an integer number.");
        }

    }
}
