package ca.concordia.encs.conquerdia.model;

import java.security.SecureRandom;

/**
 * The game model
 */
public class GameModel {
    private static GameModel instance;

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
     * @param countryName Name of the country that one army be placed on it
     * @return the result
     */
    public String placeArmy(String countryName) {
        //TODO: change and move
//        Country country = WorldMap.getInstance().getCountry(countryName);
//        if (country == null)
//            return String.format("Country with name \"%s\" was not found!", countryName);
//        String currentPlayerName = PhaseModel.getInstance().getCurrentPlayer().getName();
//        if (country.getOwner() == null || !country.getOwner().getName().equals(currentPlayerName))
//            return String.format("Dear %s, Country with name \"%s\" does not belong to you!", currentPlayerName, countryName);
//        Player player = players.get(currentPlayerName);
//        StringBuilder sb = new StringBuilder();
//        if (player.getUnplacedArmies() < 1) {
//            sb.append("You Don't have any unplaced army!");
////            giveTurnToAnotherPlayer();
//            appendPlaceArmyMessage(sb);
//            return sb.toString();
//        }
//        player.minusUnplacedArmies(1);
//        country.placeOneArmy();
//        sb.append(currentPlayerName).append(" placed one army to ").append(countryName);
////        for (int i = 0; i < playersPosition.length; i++) {
////            giveTurnToAnotherPlayer();
////            if (players.get(currentPlayerName).getUnplacedArmies() > 0) {
////                appendPlaceArmyMessage(sb);
////                return sb.toString();
////            }
////        }
//        sb.append(System.getProperty("line.separator"));
//        sb.append("All players have placed their armies.").append(System.getProperty("line.separator"));
//        sb.append("Startup phase is finished.").append(System.getProperty("line.separator"));
//        sb.append("The turn-based main play phase is began.").append(System.getProperty("line.separator"));
//        currentPhase = GamePhases.REINFORCEMENTS;
//        currentPlayer = 0;
//        runMainPlayPhase(sb);
//        return sb.toString();
        return null;
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


}
