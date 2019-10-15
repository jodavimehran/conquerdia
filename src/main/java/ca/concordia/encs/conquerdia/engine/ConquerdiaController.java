package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.api.IGameEngine;
import ca.concordia.encs.conquerdia.engine.api.IWorldMap;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;

import java.io.PrintStream;
import java.util.HashMap;
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
		List<Command> commands = CommandFactory.getCommands(conquerdiaModel, commandStr);
		for (Command command : commands) {
			output.println(command.execute());
		}
	}

	@Override
	public IWorldMap getWorldMap() {
		return conquerdiaModel.getWorldMap();
	}
}
