package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The game model
 */
public class GameModel {
    private static GameModel instance;
    private final Map<String, Player> players = new HashMap<>();

    private GameModel() {
    }

    /**
     * This method is used for getting a single instance of the {@link GameModel}
     *
     * @return single instance of the {@link GameModel}
     */
    public static GameModel getInstance() {
        if (instance == null) {
            synchronized (GameModel.class) {
                if (instance == null) {
                    instance = new GameModel();
                }
            }
        }
        return instance;
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
        int numberOfPlayers = players.size();
        if (numberOfPlayers < 3)
            return "The game need at least Three players to start.";
        Set<Country> countries = WorldMap.getInstance().getCountries();
        int numberOfCountries = countries.size();
        if (numberOfPlayers > numberOfCountries)
            return "Too Many Players! Number of player must be equal or lower than number of countries in map!";
        SecureRandom randomNumber = new SecureRandom();
        {
            Player[] playerArray = new Player[numberOfPlayers];
            playerArray = players.values().toArray(playerArray);

            int firstOne = randomNumber.nextInt(numberOfPlayers - 1);
            for (int i = 0; i < numberOfPlayers; i++) {
                PhaseModel.getInstance().addPlayer(playerArray[firstOne++]);
                if (firstOne >= numberOfPlayers)
                    firstOne = 0;
            }
        }
        {
            while (!countries.isEmpty()) {
                Country[] countryArray = new Country[countries.size()];
                countryArray = countries.toArray(countryArray);
                int value = randomNumber.nextInt(countries.size());
                Country country = countryArray[value];
                Player player = PhaseModel.getInstance().getCurrentPlayer();
                country.setOwner(player);
                country.placeOneArmy();
                player.addCountry(country);
                if (player.ownedAll(country.getContinent().getCountriesName()))
                    player.addContinent(country.getContinent());
                countries.remove(country);
                PhaseModel.getInstance().giveTurnToAnotherPlayer();
            }
        }
        int numberOfInitialArmies = calculateNumberOfInitialArmies(numberOfPlayers);
        players.forEach((key, value) -> value.addUnplacedArmies(numberOfInitialArmies - value.getNumberOfCountries()));
        StringBuilder sb = new StringBuilder();
        sb.append("All ").append(numberOfCountries).append(" countries are populated and each of ").append(numberOfPlayers).append(" players are allocated ").append(numberOfInitialArmies).append(" initial armies.").append(System.getProperty("line.separator"));
        appendPlaceArmyMessage(sb);
        return sb.toString();
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     * @return the result
     */
    public String placeArmy(String countryName) {
        Country country = WorldMap.getInstance().getCountry(countryName);
        if (country == null)
            return String.format("Country with name \"%s\" was not found!", countryName);
        String currentPlayerName = PhaseModel.getInstance().getCurrentPlayer().getName();
        if (country.getOwner() == null || !country.getOwner().getName().equals(currentPlayerName))
            return String.format("Dear %s, Country with name \"%s\" does not belong to you!", currentPlayerName, countryName);
        Player player = players.get(currentPlayerName);
        StringBuilder sb = new StringBuilder();
        if (player.getUnplacedArmies() < 1) {
            sb.append("You Don't have any unplaced army!");
//            giveTurnToAnotherPlayer();
            appendPlaceArmyMessage(sb);
            return sb.toString();
        }
        player.minusUnplacedArmies(1);
        country.placeOneArmy();
        sb.append(currentPlayerName).append(" placed one army to ").append(countryName);
//        for (int i = 0; i < playersPosition.length; i++) {
//            giveTurnToAnotherPlayer();
//            if (players.get(currentPlayerName).getUnplacedArmies() > 0) {
//                appendPlaceArmyMessage(sb);
//                return sb.toString();
//            }
//        }
        sb.append(System.getProperty("line.separator"));
        sb.append("All players have placed their armies.").append(System.getProperty("line.separator"));
        sb.append("Startup phase is finished.").append(System.getProperty("line.separator"));
        sb.append("The turn-based main play phase is began.").append(System.getProperty("line.separator"));
//        currentPhase = GamePhases.REINFORCEMENTS;
//        currentPlayer = 0;
//        runMainPlayPhase(sb);
        return sb.toString();
    }

    /**
     * This method automatically randomly place all remaining unplaced armies for all players
     *
     * @return the result
     */
    public String placeAll() {
//        if (!GamePhases.COUNTRIES_ARE_POPULATED.equals(currentPhase))
//            return "Invalid Command! This command is one of the startup phase commands and valid when all countries are populated.";
        SecureRandom randomNumber = new SecureRandom();
        StringBuilder sb = new StringBuilder();
//        while (!GamePhases.REINFORCEMENTS.equals(currentPhase)) {
//            Set<String> countryNames = players.get(playersPosition[currentPlayer]).getCountryNames();
//            String[] countriesArray = new String[countryNames.size()];
//            countriesArray = countryNames.toArray(countriesArray);
//            sb.append(placeArmy(countriesArray[randomNumber.nextInt(countryNames.size())]));
//            sb.append(System.getProperty("line.separator"));
//        }
        return sb.toString();
    }

    /**
     * @param stringBuilder The builder for append result
     */
//    private void runMainPlayPhase(StringBuilder stringBuilder) {
//        String currentPlayerName = playersPosition[currentPlayer];
//        stringBuilder.append(System.getProperty("line.separator")).append(currentPlayerName).append("'s turn").append(System.getProperty("line.separator"));
//        switch (currentPhase) {
//            case REINFORCEMENTS:
//                stringBuilder.append("Reinforcement Phase:").append(System.getProperty("line.separator"));
//                int numberOfReinforcementArmies = players.get(currentPlayerName).calculateNumberOfReinforcementArmies();
//                stringBuilder.append("Dear ").append(currentPlayerName).append(",").append(System.getProperty("line.separator"))
//                        .append("Congratulations! You've got ").append(numberOfReinforcementArmies)
//                        .append(" extra armies at this phase! You can place them wherever in your territory.")
//                        .append(System.getProperty("line.separator"));
//                break;
//            case FORTIFICATION:
//                stringBuilder.append("Fortification Phase:").append(System.getProperty("line.separator"));
//                stringBuilder.append("You can move your armies.").append(System.getProperty("line.separator"));
//                break;
//        }
//    }


    /**
     * @param stringBuilder string builder
     */
    private void appendPlaceArmyMessage(StringBuilder stringBuilder) {
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("===========================================").append(System.getProperty("line.separator"));
//        stringBuilder.append("Dear ").append(currentPlayerName).append(", you have ").append(players.get(currentPlayerName).getUnplacedArmies()).append(" unplaced armies.").append(System.getProperty("line.separator"));
        stringBuilder.append("Use \"placearmy\" to place one of them or use \"placeall\" to automatically randomly place all remaining unplaced armies for all players.");
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


}
