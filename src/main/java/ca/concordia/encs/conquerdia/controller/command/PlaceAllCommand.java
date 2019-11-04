package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

import java.security.SecureRandom;
import java.util.List;
import java.util.Set;

public class PlaceAllCommand extends AbstractCommand {

    @Override
    protected CommandType getCommandType() {
        return CommandType.PLACE_ALL;
    }

    @Override
    protected String getCommandHelpMessage() {
        return "";
    }

    /**
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        SecureRandom randomNumber = new SecureRandom();
        PhaseModel phaseModel = PhaseModel.getInstance();
        while (phaseModel.isThereAnyUnplacedArmy()) {
            Player currentPlayer = phaseModel.getCurrentPlayer();
            if (currentPlayer.getUnplacedArmies() > 0) {
                Set<String> countryNames = currentPlayer.getCountryNames();
                String[] countriesArray = new String[countryNames.size()];
                countriesArray = countryNames.toArray(countriesArray);
                String countryName = countriesArray[randomNumber.nextInt(countryNames.size())];
                phaseModel.placeArmy(countryName);
                resultList.add(String.format("%s placed one army to %s", currentPlayer.getName(), countryName));
            }
            phaseModel.giveTurnToAnotherPlayer();
        }
        phaseLogList.add("Reinforcement phase has began.");
    }

}
