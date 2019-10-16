package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;

import java.util.Arrays;
import java.util.List;

public class ReinforceCommand implements Command {
    public final static String REINFORCE_COMMAND_ERR1 = "Invalid input! The \"reinforce\" command must has at least two parameter, first one is countryname and second one is number of armies.";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 3)
            return Arrays.asList(REINFORCE_COMMAND_ERR1);
        try {
            return Arrays.asList(model.reinforce(inputCommandParts.get(1), Integer.valueOf(inputCommandParts.get(2))));
        } catch (NumberFormatException ex) {
            return Arrays.asList("Number of armies(latest parameter) must be an integer number.");
        }

    }
}
