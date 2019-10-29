package ca.concordia.encs.conquerdia.controller;

import ca.concordia.encs.conquerdia.controller.command.Command;
import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.model.CommandResultModel;

import java.util.Arrays;

/**
 *
 */
public class CommandController {

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
        commandType.getCommand().execute(Arrays.asList(inputCommandParts));

    }

}
