package ca.concordia.encs.conquerdia.model.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PhaseModel.PhaseTypes;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldMap worldMap;
	private Queue<Player> players = new LinkedList<>();
	private List<Player.CardType> cards = new ArrayList<>();
	private PhaseTypes currentPhase = PhaseTypes.NONE;
	private PhaseModel phaseModel;
	
    public PhaseModel getPhaseModel() {
		return phaseModel;
	}
	public void setPhaseModel(PhaseModel phaseModel) {
		this.phaseModel = phaseModel;
	}
	public WorldMap getWorldMap() {
		return worldMap;
	}
	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

    public Queue<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Queue<Player> players) {
		this.players = players;
	}

	public List<Player.CardType> getCards() {
		return cards;
	}
	public void setCards(List<Player.CardType> cards) {
		this.cards = cards;
	}

	public PhaseTypes getCurrentPhase() {
		return currentPhase;
	}
	public void setCurrentPhase(PhaseTypes currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	
}
