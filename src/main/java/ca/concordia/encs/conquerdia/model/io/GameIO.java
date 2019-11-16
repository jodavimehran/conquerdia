package ca.concordia.encs.conquerdia.model.io;

public class GameIO {
	private GameStateIO gameStateIO;
	public GameIO() {
		this.gameStateIO = new GameStateIO();
	}
	public void SaveGame() {
		gameStateIO.setBuilder(new GameSaverBuilder());
		gameStateIO.constructGameState();
		GameState gameSaveState = gameStateIO.getGameState();
		//Save gameSaveState objects in required files
	}
	
	public void LoadGame() {
		gameStateIO.setBuilder(new GameLoaderBuilder("C:\\GameStateFilePath\\GameState.state"));
		gameStateIO.constructGameState();
		GameState gameLoadState = gameStateIO.getGameState();
		//Assign gameLoadState objects to Game Model elements
	}
	
}
