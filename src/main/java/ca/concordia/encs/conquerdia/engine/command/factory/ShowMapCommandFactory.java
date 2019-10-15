package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.Arrays;
import java.util.List;

/**
 *  Show map Command
 */
public class ShowMapCommandFactory implements CommandFactory {
        public final static String SHOW_MAP_COMMAND_ERR1 = "Map is Empty! \nPlease use the \"loadmap\" command to load the map before using the \"showmap\"...\n";

        /**
         * This class implements the "showmap" command for the world map.
         *
         * @param model             The model object of the game.
         * @param inputCommandParts the command line parameters.
         * @return List of Command Results
         */
        @Override
        public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
            if (!model.getWorldMap().toString().isEmpty()) {
                return Arrays.asList(model.getWorldMap()::toString);
            } else {
                return Arrays.asList(() -> SHOW_MAP_COMMAND_ERR1);
            }
        }

}
