package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.PlayersModel;

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
        Player currentPlayer = phaseModel.getCurrentPlayer();
        currentPlayer.placeArmy(countryName);
        phaseLogList.add(String.format("%s placed one army to %s", currentPlayer.getName(), countryName));
        PlayersModel.getInstance().giveTurnToAnotherPlayer();
        boolean thereAnyUnplacedArmy = PlayersModel.getInstance().isThereAnyUnplacedArmy();
        while (thereAnyUnplacedArmy && currentPlayer.getUnplacedArmies() <= 0) {
            PlayersModel.getInstance().giveTurnToAnotherPlayer();
        }
    }
}