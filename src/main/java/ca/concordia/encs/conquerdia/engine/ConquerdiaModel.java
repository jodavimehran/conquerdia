package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

import java.util.HashSet;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();
    private final HashSet<String> players = new HashSet<>();
    private GamePhases currentPhase;

    /**
     * @param fileName
     * @return
     */
    public String loadMap(String fileName) {
        String result = worldMap.loadMap(fileName);
        if (worldMap.isMapLoaded()) {
            currentPhase = GamePhases.START_UP;
        }
        return result;
    }

    /**
     * @param playerName
     * @return
     */
    public String addPlayer(String playerName) {
        if (StringUtils.isBlank(playerName))
            return "Player name is not valid!";
        if (players.contains(playerName))
            return String.format("Player with name \"%s\" is already exist.", playerName);
        players.add(playerName);
        return String.format("Player with name \"%s\" is successfully added.", playerName);
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
