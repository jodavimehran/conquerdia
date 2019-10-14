package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();
    private final HashMap<String, Player> players = new HashMap<>();
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
     * Add a new player to the game if player name will not found in current player name is
     *
     * @param playerName
     * @return
     */
    public String addPlayer(String playerName) {
        if (StringUtils.isBlank(playerName))
            return "Player name is not valid!";
        if (players.containsKey(playerName))
            return String.format("Player with name \"%s\" is already exist.", playerName);
        players.put(playerName, new Player.Builder(playerName).build());
        return String.format("Player with name \"%s\" is successfully added.", playerName);
    }

    /**
     * @param playerName
     * @return
     */
    public String removePlayer(String playerName) {
        if (!players.containsKey(playerName)) {
            return String.format("Player with name \"%s\" is not found.", playerName);
        }
        players.remove(playerName);
        return String.format("Player with name \"%s\" is successfully removed.", playerName);
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
