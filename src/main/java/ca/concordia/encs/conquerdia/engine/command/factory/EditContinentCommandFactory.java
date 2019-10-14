package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.*;

/**
 *
 */
public class EditContinentCommandFactory implements CommandFactory {
    public final static String EDIT_CONTINENT_COMMAND_ERR1 = "Invalid input! The \"editcontinent\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(() -> EDIT_CONTINENT_COMMAND_ERR1);
        List<Command> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                switch (iterator.next()) {
                    case ("-add"): {
                        String continentName = iterator.next();
                        String continentValue = iterator.next();
                        commands.add(() -> model.getWorldMap().addContinent(continentName, Integer.valueOf(continentValue)));
                        break;
                    }
                    case "-remove": {
                        String continentName = iterator.next();
                        commands.add(() -> model.getWorldMap().removeContinent(continentName));
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
