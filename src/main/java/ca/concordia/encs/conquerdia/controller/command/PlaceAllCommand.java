package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.GameModel;

import java.util.List;

public class PlaceAllCommand extends AbstractCommand {

    @Override
    protected CommandType getCommandType() {
        return CommandType.PLACE_ALL;
    }

    @Override
    protected String getCommandHelpMessage() {
        return "";
    }

    /**
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public void runCommand(List<String> inputCommandParts) {
        resultList.add(GameModel.getInstance().placeAll());
    }

}
