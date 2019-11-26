package ca.concordia.encs.conquerdia.model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.google.common.base.CharMatcher;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle.BattleState;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.model.player.Player;

public class GameLoaderBuilder extends GameStateBuilder {
	private String gameStateFileName;
	private FileInputStream file ;
	private Queue<Player> players = new LinkedList<>();
	Player currentPlayer ;
//	private Player firstPlayer;
	private int numPlayers;
	public GameLoaderBuilder(String fileName) throws ValidationException {
	    this.gameStateFileName = fileName; 
        parseGameStateFile();            
//        stateProduct.getPlayersModel().setFirstPlayer(firstPlayer);
//        stateProduct.getPlayersModel().setPlayers(players);
//        stateProduct.getPlayersModel().setNumberOfPlayers(numPlayers);
	}
	private void parseGameStateFile() throws ValidationException {
		BufferedReader reader;
        try {
        	reader = new BufferedReader(new FileReader(gameStateFileName + ".state"));
        	String line = reader.readLine();
        	while(!line.equals("$$End") ) {
        		if(line.equals("$$PlayersModel")) {
            		line = reader.readLine();
            		if(line.startsWith("$$Players")) {
                		line = reader.readLine();
                		if(line.startsWith("[")) {
                			line = reader.readLine();
                		}
                		String[] csvPlayer = {};
                		while (line != null && !line.startsWith("$$")) {		
                			 csvPlayer = line.split("\\|");
                			 String playerName = csvPlayer[0];
                			 String strategy = csvPlayer[1];
                			 Player player = Player.factory(playerName, strategy);
                			 
                			 boolean fortificatiionFinished = csvPlayer[6].equals("true") ? true: false;
                			 boolean attackFinished = csvPlayer[7].equals("true") ? true: false;
                			 boolean hasSuccessfulAttack = csvPlayer[8].equals("true") ? true: false;
                			 player.setFortificationFinished(fortificatiionFinished);
                			 player.setAttackFinished(attackFinished);
                			 player.setSuccessfulAttack(hasSuccessfulAttack);
                			 
                			 String[] continents = csvPlayer[9].replace("[","").replace("]", "").split(",");
                			 HashMap<String, Continent> playerContinents = new HashMap<>();
                			 if(!continents.toString().isEmpty()) {
                    			 for(String continentName : continents) {
                    				 continentName = CharMatcher.anyOf("\r\n\t \u00A0").trimFrom(continentName);
                    				 if(!continentName.trim().isEmpty()) {
                    					 Continent continent =  WorldMap.getInstance().getContinent(continentName);
                        				 playerContinents.put(continentName,continent); 
                    				 }        				 
                    			 } 
                			 }

                			 //player.setContinents(playerContinents);
                			 
                			 String[] countries = csvPlayer[10].replace("[","").replace("]", "").split(",");
                			 if(!countries.toString().isEmpty()) {
                    			 for(String countryName : countries) {
                    				 countryName = CharMatcher.anyOf("\r\n\t \u00A0").trimFrom(countryName);
                    				 if(!countryName.trim().isEmpty()) {
                    					 player.addCountry(WorldMap.getInstance().getCountry(countryName)); 
                    				 }        				 
                    			 }                			  
                			 }
                			 players.add(player);
                			 line = reader.readLine();
                		}
                		if(line.startsWith("$$CurrentPlayer")) {
                			line = reader.readLine();
                			while (line != null && !line.startsWith("$$")) {
                				line = reader.readLine();
                			}
                		}
                		if(line.startsWith("$$FirstPlayer")) {
                			line = reader.readLine();
                			while (line != null && !line.startsWith("$$")) {
                    			line = reader.readLine();
                			}
                		}
                		if(line.startsWith("$$Battle")) {
                    		line = reader.readLine();
                    		if(line.startsWith("[")) {
                    			line = reader.readLine();
                    		}
                    		String[] csvBattle = {};
                    		while (line != null && !line.startsWith("$$")) {		
                    			csvBattle = line.split("\\|");
                    			if(currentPlayer != null) {
                    				currentPlayer.getBattle().setToCountry(WorldMap.getInstance().getCountry(csvBattle[0]));
                    				currentPlayer.getBattle().setFromCountry(WorldMap.getInstance().getCountry(csvBattle[1]));
                    				currentPlayer.getBattle().setWinner(WorldMap.getInstance().getCountry(csvBattle[2]));
                    				currentPlayer.getBattle().setNumberOfAttackerDices(Integer.parseInt(csvBattle[3]));
                    				currentPlayer.getBattle().setNumberOfDefenderDices(Integer.parseInt(csvBattle[4]));
                    				if(csvBattle[5].equals("Attacked")){
                    					currentPlayer.getBattle().setState(BattleState.Attacked);
                    				}else if(csvBattle[5].equals("Defended")){
                    					currentPlayer.getBattle().setState(BattleState.Defended);
                    				}else if(csvBattle[5].equals("Conquered")){
                     					currentPlayer.getBattle().setState(BattleState.Conquered);
                    				}
                    			}
                    		}
                		}
                		
                		if(line.startsWith("$$PhaseModel")) {
                			line = reader.readLine();
                			if(line.equals("$$CurrentPhase")) {
                				
                			}
                		}
            		}
        		}       		
        	}
        	reader.close();
        } catch(IOException ex) {
        	throw new ValidationException(gameStateFileName + ex.getMessage());
        }
	}
	@Override
	void buildPlayersModel() {	
		PlayersModel.getInstance().setFirstPlayer(stateProduct.getPlayersModel().getFirstPlayer());
		PlayersModel.getInstance().setPlayers(stateProduct.getPlayersModel().getPlayers());
//		PlayersModel.getInstance().setNumberOfPlayers(stateProduct.getPlayersModel().getNumberOfPlayers());
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
