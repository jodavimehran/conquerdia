package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

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
     */
    @Override
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        Iterator<String> iterator = inputCommandParts.iterator();
        iterator.next();

        while (iterator.hasNext()) {
            switch (iterator.next()) {
                case ("-add"): {
                    String countryName = iterator.next();
                    String continentName = iterator.next();
                    resultList.add(WorldMap.getInstance().addCountry(countryName, continentName));
                    break;
                }
                case "-remove": {
                    String countryName = iterator.next();
                    resultList.add(WorldMap.getInstance().removeCountry(countryName));
                    break;
                }
                default: {
                    throw new ValidationException(COMMAND_HELP_MSG);
                }
            }
        }
    }
}
