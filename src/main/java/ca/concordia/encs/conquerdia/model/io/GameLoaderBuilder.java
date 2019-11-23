package ca.concordia.encs.conquerdia.model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.Player.CardType;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameLoaderBuilder extends GameStateBuilder {
	private String gameStateFilePath;
	private FileInputStream file ;
	private Queue<Player> players = new LinkedList<>();
	private Player firstPlayer;
	private int numPlayers;
	public GameLoaderBuilder(String newGameStateFilePath) throws ValidationException {
	    this.gameStateFilePath = newGameStateFilePath; 
        parseGameStateFile();            
        stateProduct.getPlayersModel().setFirstPlayer(firstPlayer);
        stateProduct.getPlayersModel().setPlayers(players);
        stateProduct.getPlayersModel().setNumberOfPlayers(numPlayers);               
	}
	private void parseGameStateFile() throws ValidationException {
		BufferedReader reader;
        try {
        	reader = new BufferedReader(new FileReader(gameStateFilePath));
        	String line = reader.readLine();
        	while(line != null) {
        		if(line.startsWith("$$PlayerModel")) {
            		line = reader.readLine();
            		if(line.startsWith("$$Players")) {
                		line = reader.readLine();
                		if(line.startsWith("[")) {
                			line = reader.readLine();
                		}
                		if(line != null && !line.startsWith("$$PhaseModel")) {
                			
                			String[] csvPlayer = line.split(",");
                		}
                		if(line.startsWith("PhaseModel")) {
                			
                		}
            		}
        		}       		
        	}
        	reader.close();
        } catch(IOException ex) {
        	throw new ValidationException(gameStateFilePath + ex.getMessage());
        }
	}
	@Override
	void buildPlayersModel() {	
		PlayersModel.getInstance().setFirstPlayer(stateProduct.getPlayersModel().getFirstPlayer());
		PlayersModel.getInstance().setPlayers(stateProduct.getPlayersModel().getPlayers());
		PlayersModel.getInstance().setNumberOfPlayers(stateProduct.getPlayersModel().getNumberOfPlayers());
	}

	@Override
	void buildPhaseModel() {
		PhaseModel.getInstance();
	}
	@Override
	void buildCards() {	
		//Load cards interface in build#1 , build #2
	}

}
