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
	public void testLoadCard() throws IOException {
		String path = "lotr.cards";

		ArrayList<Card> cardList = null;
		cardList = reader.loadCards(path);

		Card lastValidCard = cardList.get(63);
		assertTrue(lastValidCard.isTypeOf("Infantry"));
	}
}
