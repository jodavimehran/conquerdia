package ca.concordia.encs.conquerdia.model.io;

import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;

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
