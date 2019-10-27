package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandType;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.view.CommandResultView;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class CommandController {

    private final ConquerdiaModel conquerdiaModel = new ConquerdiaModel();

    /**
     * @param scanner the scanner
     * @param output  the output stream
     */
    void start(Scanner scanner, PrintStream output) {
        output.println("Welcome to Conquerdia Game");
        CommandResultModel.getInstance().addObserver(new CommandResultView(output));
        while (true) {
            output.print("> ");
            String commandStr = scanner.nextLine();
            if ("exit".equals(commandStr)) {
                break;
            } else {
                executeCommand(commandStr);
            }
        }
    }

    /**
     * @param commandStr user command
     */
    public void executeCommand(String commandStr) {
        String[] inputCommandParts = commandStr.trim().split(" ");
        if (inputCommandParts.length <= 0) {
            CommandResultModel.getInstance().setResult("Invalid Command! A valid command must have at least one part.");
            return;
        }
        CommandType commandType = Command.createCommand(inputCommandParts);
        if (commandType == null) {
            CommandResultModel.getInstance().setResult("Command not found.");
            return;
        }
        List<String> commands = commandType.getCommand().execute(conquerdiaModel, Arrays.asList(inputCommandParts));
        for (String result : commands) {
            CommandResultModel.getInstance().setResult(result);
        }
    }

}
