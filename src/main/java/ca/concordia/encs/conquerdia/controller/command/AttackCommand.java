package ca.concordia.encs.conquerdia.controller.command;

import java.util.Iterator;
import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class AttackCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
       
		
		if ("-noattack".equals(inputCommandParts.get(1))) {
            return;
        }
        if (inputCommandParts.size() < CommandType.ATTACK.getMinNumberOfParts() || inputCommandParts.get(1).isEmpty()
        		|| inputCommandParts.get(2).isEmpty())
            throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        
        try {
            phaseLogList.add(PhaseModel.getInstance().getCurrentPlayer().attack(inputCommandParts.get(1), inputCommandParts.get(2),  Integer.valueOf(inputCommandParts.get(3)), inputCommandParts.get(4), inputCommandParts.get(5)));
        } catch (NumberFormatException ex) {
            throw new ValidationException("\"Number of dices(3rd parameter) must be an integer number.\"");
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