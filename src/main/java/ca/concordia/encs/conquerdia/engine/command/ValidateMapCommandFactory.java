package ca.concordia.encs.conquerdia.engine.command;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;

import java.util.Arrays;
import java.util.List;

public class ValidateMapCommandFactory implements CommandFactory {
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        return Arrays.asList(model.getWorldMap()::validateMap);
    }

}
