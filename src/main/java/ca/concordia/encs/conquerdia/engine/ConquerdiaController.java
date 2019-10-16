package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.api.IGameEngine;
import ca.concordia.encs.conquerdia.engine.api.IWorldMap;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandType;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ConquerdiaController implements IGameEngine {

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
    @Override
    public void executeCommand(String commandStr, PrintStream output) {
        String[] inputCommandParts = commandStr.trim().split(" ");
        if (inputCommandParts.length <= 0)
            output.println("Invalid Command! A valid command must have at least one part.");
        CommandType commandType = Command.createCommand(inputCommandParts);
        if (commandType == null)
            output.println("Command not found.");
        List<String> commands = commandType.getCommand().getCommands(conquerdiaModel, Arrays.asList(inputCommandParts));
        for (String result : commands) {
            output.println(result);
        }
    }

    @Override
    public IWorldMap getWorldMap() {
        return conquerdiaModel.getWorldMap();
    }
}
