package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.Country;
import ca.concordia.encs.conquerdia.engine.map.WorldMap;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Set;

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
     * @param playerName name of the plater to add
     * @return the result message
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
     * This Method remove a player
     *
     * @param playerName name of the player to remove
     * @return the result message
     */
    public String removePlayer(String playerName) {
        if (!players.containsKey(playerName)) {
            return String.format("Player with name \"%s\" is not found.", playerName);
        }
        players.remove(playerName);
        return String.format("Player with name \"%s\" is successfully removed.", playerName);
    }

    /**
     * This method randomly assign a country to a player
     *
     * @return the result message
     */
    public String populateCountries() {
        if (!GamePhases.START_UP.equals(this.currentPhase))
            return "Invalid Command! This command is one of the startup phase commands. Currently the game is not in this phase.";
        int numberOfPlayers = players.size();
        if (numberOfPlayers < 3)
            return "The game need at least Three players to start.";
        Set<Country> countries = worldMap.getCountries();
        int numberOfCountries = countries.size();
        if (numberOfPlayers > numberOfCountries)
            return "Too Many Players! Number of player must be equal or lower than number of countries in map!";
        Player[] playerArray = new Player[numberOfPlayers];
        playerArray = players.keySet().toArray(playerArray);
        SecureRandom randomNumber = new SecureRandom();
        int i = randomNumber.nextInt(numberOfPlayers - 1);
        while (!countries.isEmpty()) {
            Country[] countryArray = new Country[countries.size()];
            countryArray = countries.toArray(countryArray);
            int value = randomNumber.nextInt(countries.size());
            Country country = countryArray[value];
            country.setOwner(playerArray[i++]);
            countries.remove(country);
            if (i >= numberOfPlayers)
                i = 0;
        }
        currentPhase = GamePhases.COUNTRIES_ARE_POPULATED;
        int numberOfInitialArmies = calculateNumberOfInitialArmies(numberOfPlayers);
        players.forEach((key, value) -> value.addUnplacedArmies(numberOfInitialArmies));
        return String.format("All %d countries are populated and each of %d players are allocated %d initial armies.", numberOfCountries, numberOfPlayers, numberOfInitialArmies);
    }

    /**
     * Calculate number of initial armies depending on the number of players.
     *
     * @param numberOfPlayers the number of players
     * @return number of initial armies
     */
    private int calculateNumberOfInitialArmies(int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            default:
                return 20;
        }
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
