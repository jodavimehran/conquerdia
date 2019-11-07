package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.List;

public class FortifyCommand extends AbstractCommand {

    private static final String COMMAND_HELP_MSG = "a valid \"fortify\" command is something like \"fortify fromcountry tocountry num\" or \"fortify none\".";
    /**
     * {@inheritDoc'}
     */
    @Override
    protected CommandType getCommandType() {
        return CommandType.FORTIFY;
    }
    /**
     * {@inheritDoc}
     */
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
        if ("none".equals(inputCommandParts.get(1))) {
            PhaseModel.getInstance().getCurrentPlayer().fortify();
            return;
        }
        if (inputCommandParts.size() < 4)
            throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        try {
            phaseLogList.add(PhaseModel.getInstance().getCurrentPlayer().fortify(inputCommandParts.get(1), inputCommandParts.get(2), Integer.valueOf(inputCommandParts.get(3))));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Number of armies(latest parameter) must be an integer number.");
        }

    }
}
