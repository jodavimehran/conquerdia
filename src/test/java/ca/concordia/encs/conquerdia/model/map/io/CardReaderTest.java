package ca.concordia.encs.conquerdia.model.map.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.model.map.io.CardReader;
import ca.concordia.encs.conquerdia.model.map.io.ICardReader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.concordia.encs.conquerdia.model.map.Card;

@Ignore
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
