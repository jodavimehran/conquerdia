package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.util.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Card Exchange Model
 */
public class CardExchangeModel extends Observable {
    private static CardExchangeModel instance;
    private final List<CardType> cards = new ArrayList<>();
    private boolean reinforcementPhaseActive;

    private CardExchangeModel() {
    }

    /**
     * @return the instance
     */
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

    /**
     * Clear this model
     */
    public static void clear() {
        instance = null;
    }

    /**
     * @param cards card to be added
     */
    public void addCards(List<CardType> cards) {
        if (cards != null && !cards.isEmpty()) {
            this.cards.clear();
            this.cards.addAll(cards);
            setChanged();
            notifyObservers(this);
        }
    }

    /**
     * @return true if the reinforce phase is active
     */
    public boolean isReinforcementPhaseActive() {
        return reinforcementPhaseActive;
    }

    /**
     * @param reinforcementPhaseActive true if the reinforce phase is active
     */
    public void setReinforcementPhaseActive(boolean reinforcementPhaseActive) {
        this.reinforcementPhaseActive = reinforcementPhaseActive;
    }

    /**
     * @return the list of the cards
     */
    public List<CardType> getCards() {
        return cards;
    }
}
