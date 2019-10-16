package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.Arrays;
import java.util.List;

public class PlaceArmyCommandFactory implements CommandFactory {
    private static final String PLACE_ARMY_ERR1 = "Invalid loadmap command. A valid \"placearmy\" command is something like \"placearmy countryname\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 2)
            return Arrays.asList(() -> PLACE_ARMY_ERR1);
        return Arrays.asList(() -> model.placeArmy(inputCommandParts.get(1)));
    }
}