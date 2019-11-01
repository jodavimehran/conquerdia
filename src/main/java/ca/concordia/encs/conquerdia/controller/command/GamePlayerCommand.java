package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the <i>gameplayer</i> command
 */
public class GamePlayerCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "The \"gameplayer\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.GAME_PLAYER;
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
        List<String> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            String option = iterator.next();
            String playerName = iterator.next();
            switch (option) {
                case ("-add"): {
                    try {
                        GameModel.getInstance().addPlayer(playerName);
                        phaseLogList.add(String.format("Player with name \"%s\" was added", playerName));
                    } catch (ValidationException ex) {
                        errorList.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                case "-remove": {
                    try {
                        GameModel.getInstance().removePlayer(playerName);
                        phaseLogList.add(String.format("Player with name \"%s\" was removed.", playerName));
                    } catch (ValidationException ex) {
                        errorList.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                default: {
                    throw new ValidationException(COMMAND_HELP_MSG);
                }
            }
        }
    }
}
