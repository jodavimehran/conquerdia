package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.*;

public class EditCountryCommandFactory implements CommandFactory {
    public final static String EDIT_COUNTRY_COMMAND_ERR1 = "Invalid input! The \"editcountry\" command must has at least one option like \"-add\" or \"-remove\".";

    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(() -> EDIT_COUNTRY_COMMAND_ERR1);

        List<Command> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                switch (iterator.next()) {
                    case ("-add"): {
                        String countryName = iterator.next().toLowerCase();
                        String continentName = iterator.next().toLowerCase();
                        commands.add(() -> model.getWorldMap().addCountry(countryName, continentName));
                        break;
                    }
                    case "-remove": {
                        String countryName = iterator.next().toLowerCase();
                        commands.add(() -> model.getWorldMap().removeCountry(countryName));
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
