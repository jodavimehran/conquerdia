package ca.concordia.encs.conquerdia.engine.command.factory;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.util.Arrays;
import java.util.List;

public class SaveMapCommandFactory implements CommandFactory {

    private static final String ERR1 = "Invalid \"savemap\" command. a valid \"savemap\" command is something like \"savemap filename\".";

    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
        if (inputCommandParts.size() < 2)
            return Arrays.asList(() -> ERR1);
        return Arrays.asList(model.getWorldMap()::saveMapFile);
    }
}