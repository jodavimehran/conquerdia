package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.util.Observable;

import java.util.ArrayList;
import java.util.List;

public class CardExchangeModel extends Observable {
    private static CardExchangeModel instance;
    private final List<Player.CardType> cards = new ArrayList<>();
    private boolean reinforcementPhaseActive;

    private CardExchangeModel() {
    }

    public static CardExchangeModel getInstance() {
        if (instance == null) {
            synchronized (CommandResultModel.class) {
                if (instance == null) {
                    instance = new CardExchangeModel();
                }
            }
        }
        return instance;
    }

    public void addCards(List<Player.CardType> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        setChanged();
        notifyObservers(this);
    }

    public boolean isReinforcementPhaseActive() {
        return reinforcementPhaseActive;
    }

    public void setReinforcementPhaseActive(boolean reinforcementPhaseActive) {
        this.reinforcementPhaseActive = reinforcementPhaseActive;
    }

    public List<Player.CardType> getCards() {
        return cards;
    }
}
