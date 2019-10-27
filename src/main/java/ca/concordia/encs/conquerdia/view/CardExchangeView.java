package ca.concordia.encs.conquerdia.view;

import ca.concordia.encs.conquerdia.util.Observable;
import ca.concordia.encs.conquerdia.util.Observer;

/**
 * The card exchange view is created only during the reinforcement phase. It display all the cards owned by the current
 * player, then allow the player to select some cards to exchange. If the player selects cards, they are given the
 * appropriate number of armies as reinforcement. The player can choose not to exchange cards and exit the card exchange
 * view. If the player own 5 cards or more, they must exchange cards. The cards exchange view should cease to exist
 * after the cards exchange.
 */
public class CardExchangeView implements Observer {
    @Override
    public void update(Observable o, Object arg) {

    }
}
