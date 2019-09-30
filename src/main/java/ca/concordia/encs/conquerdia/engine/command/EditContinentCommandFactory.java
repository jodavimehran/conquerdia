package ca.concordia.encs.conquerdia.engine.command;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.map.Continent;
import ca.concordia.encs.conquerdia.engine.util.MessageUtil;

import java.util.*;

/**
 *
 */
public class EditContinentCommandFactory implements CommandFactory {

    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(() -> MessageUtil.EDIT_CONTINENT_COMMAND_ERR1);
        List<Command> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                switch (iterator.next()) {
                    case ("-add"): {
                        String continentName = iterator.next();
                        String continentValue = iterator.next();
                        Continent continent = new Continent.Builder(continentName)
                                .setValue(Integer.valueOf(continentValue))
                                .setWorldMap(model.getWorldMap())
                                .build();
                        commands.add(continent::addContinentToWorldMap);
                        break;
                    }
                    case "-remove": {
                        String continentName = iterator.next();
                        Continent continent = new Continent.Builder(continentName)
                                .setWorldMap(model.getWorldMap())
                                .build();
                        commands.add(continent::removeContinentFromWorldMap);
                        break;
                    }
                }
            }
            return commands;
        } catch (NoSuchElementException ex) {
            return Arrays.asList(() -> "Invalid input!");
        }
    }
}
