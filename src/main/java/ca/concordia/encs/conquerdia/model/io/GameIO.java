package ca.concordia.encs.conquerdia.model.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Battle;
import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PhaseModel.PhaseTypes;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.player.Player;

public class GameIO {
	private GameStateIO gameStateIO;
	public GameIO() {
		this.gameStateIO = new GameStateIO();
	}
	public String SaveGame(String fileName) throws ValidationException {
		gameStateIO.setBuilder(new GameSaverBuilder(fileName));
		gameStateIO.constructGameState();
		GameState gameSaveState = gameStateIO.getGameState();
		String saveGameLog = "";
		//Save gameSaveState in required file with a defined format
		PlayersModel playersModel = gameSaveState.getPlayersModel();  
    	Queue<Player> players = playersModel.getPlayers();
    	Player firstPlayers = playersModel.getFirstPlayer();
    	Player currentPlayer = playersModel.getCurrentPlayer();
    	PhaseModel phaseModel = gameSaveState.getPhaseModel();
        PhaseTypes currentPhase = phaseModel.getCurrentPhase();
        String phaseStatus = phaseModel.getPhaseStatus();
           	        
        File file = new File(fileName +".state");
        StringBuilder sb = new StringBuilder();
        sb.append("$$PlayersModel").append("\n");
        sb.append("$$Players").append("\n");
        sb.append("[PlayerName,strategy,#Continents,#Countries,#TotalArmies,Cards,FortificationFinished, AttackFinished, SuccessfulAttack , ContinentNames,CountryNames]").append("\n");
        for(Player player: players ) {
        	sb.append(player.getName()).append("|");
        	sb.append("human").append("|");
        	sb.append(player.getNumberOfContinents()).append("|");
        	sb.append(player.getNumberOfCountries()).append("|");
        	sb.append(player.getTotalNumberOfArmies()).append("|");
        	sb.append(player.getUnplacedArmies()).append("|");
        	List<CardType> playerCards = player.getCards();
        	for(CardType cardType : playerCards) {
        		if(playerCards.isEmpty()) {
        			sb.append("|");
        			break;
        		}
        		sb.append(cardType.getName()).append("|");
        	}
        	sb.append(player.isFortificationFinished()).append("|");
        	sb.append(player.isAttackFinished()).append("|");
        	sb.append(player.hasSuccessfulAttack()).append("|");
        	sb.append(player.getContinentNames()).append("|");
        	sb.append(player.getCountryNames()).append("\n");
        }
        sb.append("$$CurrentPlayer").append("\n");
        sb.append(currentPlayer.getName()).append("\n");
        sb.append("$$FirstPlayer").append("\n");
        sb.append(firstPlayers.getName()).append("\n"); 
        sb.append("$$Battle").append("\n");
        Battle currentPlayerBattle = playersModel.getCurrentPlayer().getBattle();
        sb.append("[FromCountry,ToCountry,winner, #AttackerDice,#DefenderDice,state]").append("\n");
        if(currentPlayerBattle != null) {
        	sb.append( currentPlayerBattle.getFromCountry().getName()).append("|");
            sb.append( currentPlayerBattle.getToCountry().getName()).append("|");
            sb.append( currentPlayerBattle.getWinner().getName()).append("|");
            sb.append( currentPlayerBattle.getNumberOfAttackerDices()).append("|");
            sb.append( currentPlayerBattle.getNumberOfDefenderDices()).append("|");
            sb.append( currentPlayerBattle.getState()).append("\n");	
        }else {
        	sb.append("\n");
        }
        sb.append("$$PhaseModel").append("\n");
        sb.append("$$CurrentPhase").append("\n");
        sb.append(currentPhase).append("\n");
        sb.append("$$PhaseStatus").append("\n");
        sb.append(phaseStatus).append("\n");
        
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());
        } catch (IOException e) {
        	throw new ValidationException(e.getMessage());
		} finally {
            if (writer != null)
				try {
					writer.close();
					saveGameLog = "Game state was saved in the "+ fileName+ ".state file" +" successfuly! You can Exit the game.";
				} catch (IOException e) {
		        	throw new ValidationException(e.getMessage());
				}
        }
        return saveGameLog;
	}
	
	public List<String> LoadGame(String fileName) throws Exception {
		gameStateIO.setBuilder(new GameLoaderBuilder(fileName));
		gameStateIO.constructGameState();
		GameState gameLoadState = gameStateIO.getGameState();
		//Assign gameLoadState objects to Game Model elements
		return null;
	}
	
}
