package ca.concordia.encs.conquerdia.model.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	    // Serialization 
        try { 
  
            // Saving of object in a file 
            FileOutputStream file = new FileOutputStream ("D:\\state.txt"); 
            ObjectOutputStream out = new ObjectOutputStream (file); 
  
            // Method for serialization of object 
            out.writeObject(gameSaveState); 
  
            out.close(); 
            file.close(); 
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("target/state.json"), gameSaveState);
           
        } 
  
        catch (IOException ex) { 
            System.out.println("IOException is caught"); 
        } 
	}
	
	public List<String> LoadGame(String fileName) throws Exception {
		gameStateIO.setBuilder(new GameLoaderBuilder(fileName));
		gameStateIO.constructGameState();
		GameState gameLoadState = gameStateIO.getGameState();
		//Assign gameLoadState objects to Game Model elements
		return null;
	}
	
}
