package ca.concordia.encs.conquerdia.controller;

import ca.concordia.encs.conquerdia.controller.command.Command;
import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

import java.util.Arrays;

/**
 *
 */
public class CommandController {

    /**
     * @param commandStr user command
     */
    public void executeCommand(String commandStr) {
        CommandResultModel.getInstance().clear();
        Player currentPlayer = PhaseModel.getInstance().getCurrentPlayer();
        if (currentPlayer != null) {
            CardExchangeModel.getInstance().addCards(currentPlayer.getCards());
        }
        String[] inputCommandParts = commandStr.trim().split(" ");
        if (inputCommandParts.length <= 0) {
            CommandResultModel.getInstance().addResult("Invalid Command! A valid command must have at least one part.");
            return;
        }
        CommandType commandType = Command.createCommand(inputCommandParts);
        if (commandType == null) {
            CommandResultModel.getInstance().addResult("Command not found.");
            return;
        }
        commandType.getCommand().execute(Arrays.asList(inputCommandParts));

    }

}
