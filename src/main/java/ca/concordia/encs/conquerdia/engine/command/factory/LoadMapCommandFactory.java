package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of loadmap command
 */
public class LoadMapCommandFactory implements CommandFactory {

    private static final String EDIT_MAP_ERR1 = "Invalid loadmap command. a valid loadmap command is something like \"loadmap filename\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 2)
            return Arrays.asList(() -> EDIT_MAP_ERR1);
        return Arrays.asList(() -> model.loadMap(inputCommandParts.get(1)));
    }
}