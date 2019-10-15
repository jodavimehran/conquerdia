package ca.concordia.encs.conquerdia.engine.api;

import java.io.PrintStream;

public interface IGameEngine {

	IWorldMap getWorldMap();
	void executeCommand(String commandStr, PrintStream output);
}
