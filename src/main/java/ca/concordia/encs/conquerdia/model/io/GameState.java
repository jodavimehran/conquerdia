package ca.concordia.encs.conquerdia.model.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PhaseModel.PhaseTypes;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player.CardType> cards = new ArrayList<>();
	private PhaseModel phaseModel;
	private PlayersModel playersModel;
	
    public PlayersModel getPlayersModel() {
		return playersModel;
	}
	public void setPlayersModel(PlayersModel playersModel) {
		this.playersModel = playersModel;
	}
	public PhaseModel getPhaseModel() {
		return phaseModel;
	}
	public void setPhaseModel(PhaseModel phaseModel) {
		this.phaseModel = phaseModel;
	}

	public List<Player.CardType> getCards() {
		return cards;
	}
	public void setCards(List<AbstractPlayer.CardType> cards) {
		this.cards = cards;
	}


	
	
}
