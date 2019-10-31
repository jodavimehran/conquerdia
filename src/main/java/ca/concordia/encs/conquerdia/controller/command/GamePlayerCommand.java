package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<String> runCommand(List<String> inputCommandParts) {
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
                    } catch (ValidationException ex) {
                        commands.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                case "-remove": {
                    try {
                        GameModel.getInstance().removePlayer(playerName);
                    } catch (ValidationException ex) {
                        commands.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                default: {
                    return Arrays.asList("Invalid input! " + getCommandHelpMessage());
                }
            }
        }
        return commands;
    }
}
