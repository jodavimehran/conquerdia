package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;

import java.util.List;

public class AttackCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";

    @Override
    protected void runCommand(List<String> inputCommandParts) throws ValidationException {
        if ("-noattack".equals(inputCommandParts.get(1))) {
            PhaseModel.getInstance().getCurrentPlayer().setAttackFinished();
            phaseLogList.add(String.format("\"-noattack\" is selected by %s.", PlayersModel.getInstance().getCurrentPlayer().getName()));
            return;
        }
        if (inputCommandParts.size() < 4) {
            throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        }
        String fromCountryName = inputCommandParts.get(1);
        String toCountryName = inputCommandParts.get(2);
        String thirdParam = inputCommandParts.get(3);

        //attack A B -allout
        if ("-allout".equals(thirdParam)) {
            //TODO: Implement allout
        } else {
            try {
                int numberOfDices = Integer.valueOf(thirdParam);
                PhaseModel.getInstance().getCurrentPlayer().attack(fromCountryName, toCountryName, numberOfDices);
                phaseLogList.add(String.format("Player %s attacked from %s to %s by %d armies.", fromCountryName, toCountryName, numberOfDices));
            } catch (NumberFormatException ex) {
                throw new ValidationException("\"Number of dices(3rd parameter) must be an integer number.\"");
            }
        }
    }

    @Override
    protected String getCommandHelpMessage() {
        return COMMAND_HELP_MSG;
    }

    @Override
    protected CommandType getCommandType() {
        return CommandType.ATTACK;
    }
}