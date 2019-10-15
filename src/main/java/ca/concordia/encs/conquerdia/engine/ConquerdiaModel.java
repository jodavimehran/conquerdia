package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.Country;
import ca.concordia.encs.conquerdia.engine.map.WorldMap;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Set;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();
    private final LinkedHashMap<String, Player> players = new LinkedHashMap<>();
    private GamePhases currentPhase;
    private String[] playersPosition;
    private int currentPlayer;

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
     * Add a new player to the game if player name will not found in current player
     * name is
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
        if (!players.containsKey(playerName))
            return String.format("Player with name \"%s\" is not found.", playerName);
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
        SecureRandom randomNumber = new SecureRandom();
        {
            Player[] playerArray = new Player[numberOfPlayers];
            this.playersPosition = new String[numberOfPlayers];
            playerArray = players.keySet().toArray(playerArray);

            int firstOne = randomNumber.nextInt(numberOfPlayers - 1);
            for (int i = 0; i < numberOfPlayers; i++) {
                this.playersPosition[i] = playerArray[firstOne++].getName();
                if (firstOne >= numberOfPlayers)
                    firstOne = 0;
            }
        }
        {
            this.currentPlayer = 0;
            int i = 0;
            while (!countries.isEmpty()) {
                Country[] countryArray = new Country[countries.size()];
                countryArray = countries.toArray(countryArray);
                int value = randomNumber.nextInt(countries.size());
                Country country = countryArray[value];
                Player player = players.get(this.playersPosition[i++]);
                country.setOwner(player);
                player.addCountry(country);
                if (player.ownedAll(country.getContinent().getCountriesName()))
                    player.addContinent(country.getContinent());

                countries.remove(country);
                if (i >= numberOfPlayers)
                    i = 0;
            }
        }
        currentPhase = GamePhases.COUNTRIES_ARE_POPULATED;
        int numberOfInitialArmies = calculateNumberOfInitialArmies(numberOfPlayers);
        players.forEach((key, value) -> value.addUnplacedArmies(numberOfInitialArmies));
        StringBuilder sb = new StringBuilder();
        sb.append("All ").append(numberOfCountries).append(" countries are populated and each of ").append(numberOfPlayers).append(" players are allocated ").append(numberOfInitialArmies).append(" initial armies.").append(System.getProperty("line.separator"));
        appendPlaceArmyMessage(sb);
        return sb.toString();
    }

    /**
     * @param countryName
     * @return
     */
    public String placeArmy(String countryName) {
        if (!GamePhases.COUNTRIES_ARE_POPULATED.equals(currentPhase))
            return "Invalid Command! This command is one of the startup phase commands and valid when all countries are populated.";
        Country country = worldMap.getCountry(countryName);
        if (country == null)
            return String.format("Country with name \"%s\" was not found!", countryName);
        String currentPlayerName = playersPosition[currentPlayer];
        if (country.getOwner() == null || !country.getOwner().getName().equals(currentPlayerName))
            return String.format("Country with name \"%s\" does not belong to you!", countryName);
        Player player = players.get(currentPlayerName);
        StringBuilder sb = new StringBuilder();
        if (player.getUnplacedArmies() < 1) {
            sb.append("You Dont have any unplaced army!");
            giveTurnToAnotherPlayer();
            appendPlaceArmyMessage(sb);
            return sb.toString();
        }
        player.minusUnplacedArmies(1);
        country.placeOneArmy();
        sb.append("Player with name ").append(currentPlayerName).append(" placed one army to ").append(countryName);
        for (int i = 0; i < playersPosition.length; i++) {
            giveTurnToAnotherPlayer();
            if (players.get(currentPlayerName).getUnplacedArmies() > 0) {
                appendPlaceArmyMessage(sb);
                return sb.toString();
            }
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("All players have placed their armies.").append(System.getProperty("line.separator"));
        sb.append("Startup phase is finished.").append(System.getProperty("line.separator"));
        sb.append("The turn-based main play phase is began.").append(System.getProperty("line.separator"));
        currentPhase = GamePhases.REINFORCEMENTS;
        currentPlayer = 0;
        runMainPlayPhase(sb);
        return sb.toString();
    }

    /**
     * @return
     */
    private void runMainPlayPhase(StringBuilder sb) {
        String currentPlayerName = playersPosition[currentPlayer];
        sb.append(System.getProperty("line.separator")).append(currentPlayerName).append("'s turn").append(System.getProperty("line.separator"));
        switch (currentPhase) {
            case REINFORCEMENTS:
                sb.append("Reinforcement Phase:").append(System.getProperty("line.separator"));
                int numberOfReinforcementArmies = players.get(currentPlayerName).calculateNumberOfReinforcementArmies();
                sb.append("Dear ").append(currentPlayerName).append(",").append(System.getProperty("line.separator"))
                        .append("Congratulations! You've got ").append(numberOfReinforcementArmies)
                        .append(" extra armies at this phase! You can place them wherever in your territory.")
                        .append(System.getProperty("line.separator"));
                break;
            case FORTIFICATION:
                sb.append("Fortification Phase:").append(System.getProperty("line.separator"));
                sb.append("You can move your armies.").append(System.getProperty("line.separator"));
                break;
        }
    }

    public String reinforce(String countryName, int numberOfArmy) {
        if (!GamePhases.REINFORCEMENTS.equals(currentPhase))
            return "Invalid Command! This command is one of the main play phase commands and valid when game is in reinforcement phase.";
        Country country = worldMap.getCountry(countryName);
        if (country == null)
            return String.format("Country with name \"%s\" was not found!", countryName);
        if (numberOfArmy < 1)
            return "Invalid number! Number of armies must be greater than 0.";
        String currentPlayerName = playersPosition[currentPlayer];
        if (country.getOwner() == null || !country.getOwner().getName().equals(currentPlayerName))
            return String.format("Country with name \"%s\" does not belong to you!", countryName);
        Player player = players.get(currentPlayerName);
        StringBuilder sb = new StringBuilder();
        int realNumberOfArmies = numberOfArmy > player.getUnplacedArmies() ? player.getUnplacedArmies() : numberOfArmy;
        country.placeArmy(realNumberOfArmies);
        player.minusUnplacedArmies(realNumberOfArmies);
        sb.append("Dear ").append(currentPlayerName).append(", you placed ").append(realNumberOfArmies).append(" armies to ").append(countryName);
        if (player.getUnplacedArmies() > 0) {
            sb.append(" and ").append(player.getUnplacedArmies()).append(" armies are remain unplaced.");
            return sb.toString();
        }
        sb.append(" and finished reinforcement phase successfully.");
        currentPhase = GamePhases.FORTIFICATION;
        runMainPlayPhase(sb);
        return sb.toString();
    }

    /**
     *
     */
    private void giveTurnToAnotherPlayer() {
        currentPlayer++;
        if (currentPlayer >= playersPosition.length)
            currentPlayer = 0;
    }

    /**
     * @param stringBuilder
     */
    private void appendPlaceArmyMessage(StringBuilder stringBuilder) {
        stringBuilder.append(System.getProperty("line.separator"));
        String currentPlayerName = playersPosition[currentPlayer];
        stringBuilder.append("Dear ").append(currentPlayerName).append(", you have ").append(players.get(currentPlayerName).getUnplacedArmies()).append(" unplaced armies.").append(System.getProperty("line.separator"));
        stringBuilder.append("Use \"placearmy\" to place one of them or use  \"placeall\" to automatically randomly place all remaining unplaced armies for all players.");
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
