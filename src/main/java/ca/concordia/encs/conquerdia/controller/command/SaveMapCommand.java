package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

public class SaveMapCommand extends AbstractCommand {

    private static final String COMMAND_HELP_MSG = "Invalid \"savemap\" command. a valid \"savemap\" command is something like \"savemap filename\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.SAVE_MAP;
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
    public void runCommand(List<String> inputCommandParts) {
        phaseLogList.add(WorldMap.getInstance().saveMap(inputCommandParts.get(1)));
    }
}