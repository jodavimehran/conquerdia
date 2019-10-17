package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Card;

/**
 * Interface to provide card file reading functionalities
 * @author Mosabbir
 *
 */
public interface ICardReader {
	/**
	 * Loads the cards defined in the filename
	 * @param filename The name of the card file
	 * @return An ArrayList containing the card objects read from the file
	 */
	ArrayList<Card> loadCards(String filename) throws IOException;
}
