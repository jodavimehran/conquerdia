package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Card;

public interface ICardReader {
	ArrayList<Card> loadCards(String filename) throws IOException;
}
