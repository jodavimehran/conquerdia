package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;

import java.util.*;

/**
 * Implementation of the <i></>editneighbor</i> command
 */
public class EditNeighborCommand implements Command {
    public final static String ERR1 = "Invalid input! The \"editneighbor\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(ERR1);
        List<String> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                String option = iterator.next();
                String firstCountryName = iterator.next().toLowerCase();
                String secondCountryName = iterator.next().toLowerCase();
                switch (option) {
                    case ("-add"): {
                        commands.add(model.getWorldMap().addNeighbour(firstCountryName, secondCountryName));
                        break;
                    }
                    case "-remove": {
                        commands.add(model.getWorldMap().removeNeighbour(firstCountryName, secondCountryName));
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
