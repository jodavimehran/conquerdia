package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

import java.util.HashSet;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();
    private final HashSet<String> players = new HashSet<>();
    private GamePhases currentPhase;

    public String loadMap(String fileName) {
        String result = worldMap.loadMap(fileName);
        if (worldMap.isMapLoaded()) {
            currentPhase = GamePhases.START_UP;
        }
        return result;
    }


    /**
     * This method gets the worldMap that contains all Continents and countries
     *
     * @return the current worldMap of the Game.
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }

}
