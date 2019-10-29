package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EditCountryCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "Invalid input! The \"editcountry\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.EDIT_COUNTRY;
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
            switch (iterator.next()) {
                case ("-add"): {
                    String countryName = iterator.next();
                    String continentName = iterator.next();
                    commands.add(WorldMap.getInstance().addCountry(countryName, continentName));
                    break;
                }
                case "-remove": {
                    String countryName = iterator.next();
                    commands.add(WorldMap.getInstance().removeCountry(countryName));
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
