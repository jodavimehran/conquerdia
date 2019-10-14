package ca.concordia.encs.conquerdia.engine.map.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.encs.conquerdia.engine.map.Card;

public class CardReaderTest {

	private ICardReader reader;

	@Before
	public void beforeTests() {
		reader = new CardReader();
	}

	@Test
	public void testLoadCard() {
		String path = "lotr.cards";

		ArrayList<Card> cardList = null;

		try {
			cardList = reader.loadCards(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Card lastValidCard = cardList.get(63);
		assertTrue(lastValidCard.isTypeOf("Infantry"));
	}
}
