package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;

import java.util.Arrays;
import java.util.List;

public class FortifyCommand implements Command {

    private static final String ERR1 = "Invalid \"fortify\" command. a valid one is something like \"fortify fromcountry tocountry num\" or \"fortify none\".";

    /**
     * @param model             The model object of the game.
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public List<String> execute(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 2)
            return Arrays.asList(ERR1);
        if ("none".equals(inputCommandParts.get(1))) {
            return Arrays.asList(model.fortify());
        }
        if (inputCommandParts.size() < 4)
            return Arrays.asList(ERR1);
        try {
            return Arrays.asList(model.fortify(inputCommandParts.get(1), inputCommandParts.get(2), Integer.valueOf(inputCommandParts.get(3))));
        } catch (NumberFormatException ex) {
            return Arrays.asList("Number of armies(latest parameter) must be an integer number.");
        }

    }
}
