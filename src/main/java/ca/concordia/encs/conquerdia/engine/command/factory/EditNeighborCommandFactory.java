package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.*;

/**
 * Implementation of the <i></>editneighbor</i> command
 */
public class EditNeighborCommandFactory implements CommandFactory {
    public final static String ERR1 = "Invalid input! The \"editneighbor\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @param model
     * @param inputCommandParts
     * @return
     */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(() -> ERR1);
        List<Command> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                String option = iterator.next();
                String firstCountryName = iterator.next().toLowerCase();
                String secondCountryName = iterator.next().toLowerCase();
                switch (option) {
                    case ("-add"): {
                        commands.add(() -> model.getWorldMap().addNeighbour(firstCountryName, secondCountryName));
                        break;
                    }
                    case "-remove": {
                        commands.add(() -> model.getWorldMap().removeNeighbour(firstCountryName, secondCountryName));
                        break;
                    }
                    default: {
                        return Arrays.asList(() -> "Invalid input!");
                    }
                }
            }
            return commands;
        } catch (NoSuchElementException ex) {
            return Arrays.asList(() -> "Invalid input!");
        }
    }
}
