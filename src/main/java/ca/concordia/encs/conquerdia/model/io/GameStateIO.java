package ca.concordia.encs.conquerdia.model.io;

public class GameStateIO {
	
    /**
     * The Director is to use a specific "build plan": the GameStateBuilder
     */
    private GameStateBuilder builder;

    public void setBuilder(GameStateBuilder newGameStateBuilder) {
        builder = newGameStateBuilder;
    }

    /**
     * The Director assumes that all GameStates have the same parts
     * and each part is built by calling the same method
     * though what these specific methods do may be different
     */
    public void constructGameState() {
        builder.createNewGameState();
        builder.buildMap();
        builder.buildCards();
        builder.buildPlayers();
        builder.buildPhase();
    }

    /**
     * @return gets the GameState after it has been built
     */
    public GameState getGameState() {
        return builder.getGameState();
    }
}
