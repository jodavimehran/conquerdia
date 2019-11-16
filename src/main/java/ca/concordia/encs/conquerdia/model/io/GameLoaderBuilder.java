package ca.concordia.encs.conquerdia.model.io;

public class GameLoaderBuilder extends GameStateBuilder {
	private String gameStateFilePath;
	public GameLoaderBuilder(String newGameStateFilePath) {
		this.gameStateFilePath = newGameStateFilePath;
	}
	@Override
	void buildMap() {	
	}

	@Override
	void buildPlayers() {	
	}

	@Override
	void buildPhase() {	
	}

	@Override
	void buildCards() {	
	}
}
