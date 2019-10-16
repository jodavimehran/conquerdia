package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;

import java.util.*;

/**
 *
 */
public class EditContinentCommand implements Command {
    public final static String EDIT_CONTINENT_COMMAND_ERR1 = "Invalid input! The \"editcontinent\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(EDIT_CONTINENT_COMMAND_ERR1);
        List<String> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        iterator.next();
        try {
            while (iterator.hasNext()) {
                String option = iterator.next();
                switch (option) {
                    case ("-add"): {
                        String continentName = iterator.next();
                        String continentValue = iterator.next();
                        try {
                            commands.add(model.getWorldMap().addContinent(continentName, Integer.valueOf(continentValue)));
                        } catch (NumberFormatException ex) {
                            commands.add("Continent value must be an integer number.");
                        }
                        break;
                    }
                    case "-remove": {
                        String continentName = iterator.next();
                        commands.add(model.getWorldMap().removeContinent(continentName));
                        break;
                    }
                    default: {
                        return Arrays.asList("Invalid input!");
                    }
                }
            }
            return commands;
        } catch (NoSuchElementException ex) {
            return Arrays.asList("Invalid input!");
        }
    }
}
