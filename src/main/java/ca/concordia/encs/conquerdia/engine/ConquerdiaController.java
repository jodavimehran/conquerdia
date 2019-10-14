package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ConquerdiaController {

    private final ConquerdiaModel conquerdiaModel = new ConquerdiaModel();

    /**
     * @param scanner
     * @param output
     */
    public void start(Scanner scanner, PrintStream output) {
        output.println("Welcome to Conquerdia Game");
        while (true) {
            output.print("> ");
            String commandStr = scanner.nextLine();
            if ("exit".equals(commandStr)) {
                break;
            } else {
                executeCommand(commandStr, output);
            }
        }
    }

    /**
     * @param commandStr
     * @param output
     */
    public void executeCommand(String commandStr, PrintStream output) {
        List<Command> commands = CommandFactory.getCommands(conquerdiaModel, commandStr);
        for (Command command : commands) {
            output.println(command.execute());
        }
    }
}
