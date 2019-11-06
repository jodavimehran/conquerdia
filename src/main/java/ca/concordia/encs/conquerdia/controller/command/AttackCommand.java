package ca.concordia.encs.conquerdia.controller.command;

import java.util.Iterator;
import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class AttackCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
       
    	//attack A B -noAttack
    	if(inputCommandParts.size() == 2) {
        	String noAttack = inputCommandParts.get(1);
        	if("-noattack".equals(noAttack)) {
        		PhaseModel.getInstance().getCurrentPlayer().setAttackFinished();
        	}else {
                throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        	}
    	}
        if (inputCommandParts.size() < CommandType.ATTACK.getMinNumberOfParts() || inputCommandParts.get(1).isEmpty()
        		|| inputCommandParts.get(2).isEmpty() )
            throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        
        try {
        	
        	
        	String fromCountryName = inputCommandParts.get(1);
        	String toCountryName = inputCommandParts.get(2);
        	String thirdParam = inputCommandParts.get(3);

        	//attack A B -allout
        	if("-allout".equals(thirdParam)) {
//        		PhaseModel.getInstance().getCurrentPlayer().getBattle().;
        	}else {
                throw new ValidationException("Invalid input! " + getCommandHelpMessage());
        	}
        	int numberOfDices = Integer.valueOf(thirdParam);
        	PhaseModel.getInstance().getCurrentPlayer().attack(fromCountryName,toCountryName , numberOfDices );
            phaseLogList.add(String.format("Player %s attacked from %s to %s by %d armies.", fromCountryName, toCountryName, numberOfDices));
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