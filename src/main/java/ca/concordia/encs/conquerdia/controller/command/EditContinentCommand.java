package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class EditContinentCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "The \"editcontinent\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.EDIT_CONTINENT;
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
            String option = iterator.next();
            switch (option) {
                case ("-add"): {
                    String continentName = iterator.next();
                    String continentValue = iterator.next();
                    try {
                        commands.add(WorldMap.getInstance().addContinent(continentName, Integer.valueOf(continentValue)));
                    } catch (NumberFormatException ex) {
                        commands.add("Continent value must be an integer number.");
                    }
                    break;
                }
                case "-remove": {
                    String continentName = iterator.next();
                    commands.add(WorldMap.getInstance().removeContinent(continentName));
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
