package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class EditContinentCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "The \"editcontinent\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @return command type
     */
    @Override
    protected CommandType getCommandType() {
        return CommandType.EDIT_CONTINENT;
    }

    /**
     * @return command help message
     */
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
            String option = iterator.next();
            switch (option) {
                case ("-add"): {
                    String continentName = iterator.next();
                    String continentValue = iterator.next();
                    try {
                        WorldMap.getInstance().addContinent(continentName, Integer.valueOf(continentValue));
                        phaseLogList.add(String.format("Continent with name \"%s\" and value \"%s\" is added to map", continentName, continentValue));
                    } catch (ValidationException ex) {
                        errorList.addAll(ex.getValidationErrors());
                    } catch (NumberFormatException ex) {
                        errorList.add("Continent value must be an integer number.");
                    }
                    break;
                }
                case "-remove": {
                    try {
                        String continentName = iterator.next();
                        Continent continent = WorldMap.getInstance().removeContinent(continentName);
                        phaseLogList.add(String.format("Continent with name \"%s\" is removed.", continentName));
                    } catch (ValidationException ex) {
                        errorList.addAll(ex.getValidationErrors());
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
