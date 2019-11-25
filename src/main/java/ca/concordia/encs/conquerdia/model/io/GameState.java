package ca.concordia.encs.conquerdia.model.io;

import ca.concordia.encs.conquerdia.model.CardType;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<CardType> cards = new ArrayList<>();
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

    public List<CardType> getCards() {
        return cards;
    }

    public void setCards(List<CardType> cards) {
        this.cards = cards;
    }


}
