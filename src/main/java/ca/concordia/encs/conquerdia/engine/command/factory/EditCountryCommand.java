package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;

import java.util.*;

public class EditCountryCommand implements Command {
    public final static String EDIT_COUNTRY_COMMAND_ERR1 = "Invalid input! The \"editcountry\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> execute(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(EDIT_COUNTRY_COMMAND_ERR1);

        List<String> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        iterator.next();
        try {
            while (iterator.hasNext()) {
                switch (iterator.next()) {
                    case ("-add"): {
                        String countryName = iterator.next();
                        String continentName = iterator.next();
                        commands.add(model.getWorldMap().addCountry(countryName, continentName));
                        break;
                    }
                    case "-remove": {
                        String countryName = iterator.next();
                        commands.add(model.getWorldMap().removeCountry(countryName));
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
