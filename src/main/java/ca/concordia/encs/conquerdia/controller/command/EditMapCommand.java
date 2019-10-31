package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.List;

public class EditMapCommand extends AbstractCommand {

    private static final String COMMAND_HELP_MSG = "Invalid \"editmap\" command. A valid \"editmap\" command is something like \"editmap filename\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.EDIT_MAP;
    }

    @Override
    protected String getCommandHelpMessage() {
        return COMMAND_HELP_MSG;
    }

    /**
     * @param inputCommandParts the command line parameters.
     */
    @Override
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        resultList.add(WorldMap.getInstance().editMap(inputCommandParts.get(1)));
    }
}
