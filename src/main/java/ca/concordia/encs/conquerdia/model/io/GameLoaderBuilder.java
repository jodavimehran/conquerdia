package ca.concordia.encs.conquerdia.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player.CardType;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameLoaderBuilder extends GameStateBuilder {
	private String gameStateFilePath;
	public GameLoaderBuilder(String newGameStateFilePath) throws ClassNotFoundException {
		this.gameStateFilePath = newGameStateFilePath;
		// Deserialization 
        try { 
  
            // Reading the object from a file 
            FileInputStream file = new FileInputStream (gameStateFilePath); 
            ObjectInputStream in = new ObjectInputStream (file); 
  
            // Method for deserialization of object 
            stateProduct = (GameState) in.readObject();
            in.close(); 
            file.close(); 

        } 
  
        catch (IOException ex) { 
            System.out.println("IOException is caught"); 
        } 
        catch (ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException" + " is caught"); 
        } 
        
		ObjectMapper mapper = new ObjectMapper();

		try {

			// Convert JSON string from file to Object
			stateProduct = mapper.readValue(new File("target/state.json"), GameState.class);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
	}
	@Override
	void buildMap() {
		//Use Read from Map Files implemented with adapter
	}

	@Override
	void buildPlayers() {	
		PlayersModel.getInstance().getPlayers().addAll(stateProduct.getPlayers());
	}

	@Override
	void buildPhase() {	
	}
	@Override
	void buildPhaseModel() {
		PhaseModel.getInstance().setCurrentPhase(stateProduct.getCurrentPhase());
	}
	@Override
	void buildCards() {	
		//Load cards interface in build#1 , build #2
	}

}
