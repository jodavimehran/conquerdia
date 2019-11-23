package ca.concordia.encs.conquerdia.model.io;
import java.util.Arrays;

import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player.CardType;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameSaverBuilder extends GameStateBuilder {

	@Override
	void buildPhaseModel() {
		stateProduct.setPhaseModel(PhaseModel.getInstance());
	}
	
	@Override
	void buildCards() {
		stateProduct.setCards(CardExchangeModel.getInstance().getCards());
	}

	@Override
	void buildPlayersModel() {
		stateProduct.setPlayersModel(PlayersModel.getInstance());
		
	}
}
