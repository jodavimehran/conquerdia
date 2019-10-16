package ca.concordia.encs.conquerdia.engine.command;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public interface CommandFactory {

    /**
     * @param model
     * @param userInput
     * @return
     */
    static List<Command> getCommands(ConquerdiaModel model, String userInput) {
        String[] inputCommandParts = userInput.trim().split(" ");
        if (inputCommandParts.length <= 0)
            return Arrays.asList(() -> "Invalid Command! A valid command must have at least one part.");
        CommandType commandType = CommandType.findCommandTypeByName(inputCommandParts[0]);
        if (commandType == null)
            return Arrays.asList(() -> "Command not found.");
        return commandType.getFactory().getCommands(model, Arrays.asList(inputCommandParts));
    }


    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts);

}
