package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();
    private  GameStatus currentStatus = GameStatus.MAP_EDIT;
    /**
     * This method is the setter for the current status of the game.
     * @param currentStatus the current status of the game.
     */
    public void setCurrentStatus(GameStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
     * This method is a getter for the current status of the map.
     * @return the current status of the game.
     */
    public GameStatus getCurrentStatus() {
		return currentStatus;
	}
	/**
     * This method gets the worldMap that contains all Continents and countries
     * @return the current worldMap of the Game.
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }
    
}
