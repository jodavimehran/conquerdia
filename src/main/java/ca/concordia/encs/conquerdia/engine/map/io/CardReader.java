package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Card;
import ca.concordia.encs.conquerdia.engine.util.FileHelper;

/**
 * Provide methods to read cards
 * 
 * @author Mosabbir
 *
 */
public class CardReader implements ICardReader {

	public static final String COMMENT_SYMBOL = ";";
	public static final String CARDS_SECTION_IDENTIFIER = "[cards]";
	public static final String TOKENS_DELIMETER = " ";
	public static final String CARDS_FOLDER = "cards";
	public static final String CARDS_FILE_EXTENSION = ".cards";
	public static final String SUPPORTED_TYPES = "INFANTRY,CAVALRY,CANNON";

	/**
	 * Reads cards from the card file and returns all the cards 
	 */
	public ArrayList<Card> loadCards(String filename) throws IOException {
		final String path = getCardsFilePath(filename);

		final File file = new File(path);
		final BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		ArrayList<Card> cards = null;

		try {
			while ((line = reader.readLine()) != null) {
				if (isCardsIdentifier(line)) {
					cards = readCards(reader);
					break;
				}
			}
		} finally {
			reader.close();
		}

		return cards;
	}

	private ArrayList<Card> readCards(BufferedReader reader) throws IOException {

		ArrayList<Card> cardList = new ArrayList<>();
		Card card;
		String line;
		String tokens[];
		String cardType;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			cardType = tokens[0];

			if (isSupprotedCardType(cardType)) {
				card = new Card(cardType);
				cardList.add(card);
			}
		}

		return cardList;
	}

	private boolean isSupprotedCardType(String cardType) {
		return SUPPORTED_TYPES.contains(cardType.toUpperCase());
	}

	private static boolean isCardsIdentifier(String line) {
		return line.equalsIgnoreCase(CARDS_SECTION_IDENTIFIER);
	}

	private static String getCardsFilePath(String filename) {
		return FileHelper.getResourcePath(FileHelper.combinePath(CARDS_FOLDER,
				FileHelper.getFileNameWithoutExtension(filename) + CARDS_FILE_EXTENSION));
	}
}
