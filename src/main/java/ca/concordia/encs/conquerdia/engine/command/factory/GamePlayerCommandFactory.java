package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.*;

/**
 * Implementation of the <i></>gameplayer</i> command
 */
public class GamePlayerCommandFactory implements CommandFactory {
    public final static String GAME_PLAYER_COMMAND_ERR1 = "Invalid input! The \"gameplayer\" command must has at least one option like \"-add\" or \"-remove\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(() -> GAME_PLAYER_COMMAND_ERR1);
        List<Command> commands = new ArrayList<>();
        Iterator<String> iterator = inputCommandParts.iterator();
        try {
            while (iterator.hasNext()) {
                String option = iterator.next();
                String playerName = iterator.next();
                switch (option) {
                    case ("-add"): {
                        commands.add(() -> model.addPlayer(playerName));
                        break;
                    }
                    case "-remove": {
                        commands.add(() -> model.removePlayer(playerName));
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
