package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

import java.util.List;

public class PlaceArmyCommand extends AbstractCommand {
    private static final String COMMAND_HELP_MSG = "A valid \"placearmy\" command is something like \"placearmy countryname\".";

    @Override
    protected CommandType getCommandType() {
        return CommandType.PLACE_ARMY;
    }

    @Override
    protected String getCommandHelpMessage() {
        return COMMAND_HELP_MSG;
    }

    /**
     * @param inputCommandParts the command line parameters.
     * @return List of Command Results
     */
    @Override
    public void runCommand(List<String> inputCommandParts) throws ValidationException {
        PhaseModel phaseModel = PhaseModel.getInstance();
        if (!phaseModel.isAllCountriesArePopulated()) {
            throw new ValidationException("Before this command you must run \"populatecountries\" command!");
        }
        String countryName = inputCommandParts.get(1);
        phaseModel.placeArmy(countryName);
        Player currentPlayer = phaseModel.getCurrentPlayer();
        phaseLogList.add(String.format("%s placed one army to %s", currentPlayer.getName(), countryName));
        phaseModel.giveTurnToAnotherPlayer();
        boolean thereAnyUnplacedArmy = phaseModel.isThereAnyUnplacedArmy();
        while (thereAnyUnplacedArmy && currentPlayer.getUnplacedArmies() <= 0) {
            phaseModel.giveTurnToAnotherPlayer();
        }
    }
}