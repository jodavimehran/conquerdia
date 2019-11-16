package ca.concordia.encs.conquerdia.model.io;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class GameSaverBuilder extends GameStateBuilder {

	@Override
	void buildMap() {
		stateProduct.setWorldMap(WorldMap.getInstance());
	}
	
	@Override
	void buildPlayers() {
		stateProduct.setPlayers(PlayersModel.getInstance().getPlayers());
	}

	@Override
	void buildPhase() {
		stateProduct.setCurrentPhase(PhaseModel.getInstance().getCurrentPhase());
	}

	@Override
	void buildCards() {
		stateProduct.setCards(CardExchangeModel.getInstance().getCards());
	}
}
