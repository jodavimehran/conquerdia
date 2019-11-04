package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the <i>editneighbor</i> command
 */
public class EditNeighborCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "Invalid input! The \"editneighbor\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.EDIT_NEIGHBOR;
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
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        Iterator<String> iterator = inputCommandParts.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            String option = iterator.next();
            String firstCountryName = iterator.next();
            String secondCountryName = iterator.next();
            switch (option) {
                case ("-add"): {
                    try {
                        WorldMap.getInstance().addNeighbour(firstCountryName, secondCountryName);
                        phaseLogList.add(String.format("a country adjacency between \"%s\" and \"%s\" is added.", firstCountryName, secondCountryName));
                    } catch (ValidationException ex) {
                        resultList.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                case "-remove": {
                    try {
                        WorldMap.getInstance().removeNeighbour(firstCountryName, secondCountryName);
                        phaseLogList.add(String.format("a country adjacency between \"%s\" and \"%s\" is removed.", firstCountryName, secondCountryName));
                    } catch (ValidationException ex) {
                        resultList.addAll(ex.getValidationErrors());
                    }
                    break;
                }
                default: {
                    throw new ValidationException(COMMAND_HELP_MSG);
                }
            }
        }
    }
}
