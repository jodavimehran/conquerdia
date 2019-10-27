package ca.concordia.encs.conquerdia.controller;

import ca.concordia.encs.conquerdia.model.GameModel;
import ca.concordia.encs.conquerdia.controller.command.Command;
import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.model.CommandResultModel;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class CommandController {

    private final GameModel gameModel = new GameModel();

    /**
     * @param commandStr user command
     */
    public void executeCommand(String commandStr) {
        String[] inputCommandParts = commandStr.trim().split(" ");
        if (inputCommandParts.length <= 0) {
            CommandResultModel.getInstance().setResult("Invalid Command! A valid command must have at least one part.");
            return;
        }
        CommandType commandType = Command.createCommand(inputCommandParts);
        if (commandType == null) {
            CommandResultModel.getInstance().setResult("Command not found.");
            return;
        }
        List<String> commands = commandType.getCommand().execute(gameModel, Arrays.asList(inputCommandParts));
        for (String result : commands) {
            CommandResultModel.getInstance().setResult(result);
        }
    }

}
