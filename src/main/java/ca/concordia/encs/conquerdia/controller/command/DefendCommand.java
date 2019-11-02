package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

public class DefendCommand extends AbstractCommand {
	private static final String COMMAND_HELP_MSG = "A valid \"defend\" command format is \"defend numdice\".";

	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		if (hasMinimumNumberofParameters(inputCommandParts)) {			
			try {
				int numDice = Integer.parseInt(inputCommandParts.get(1));
				Player defender = PhaseModel.getInstance().getCurrentPlayer();
				phaseLogList.add(defender.defend(numDice));
			} catch (NumberFormatException ex) {
				throw new ValidationException("Number of dices must be an integer number.");
			}
		} else {
			throw new ValidationException("Invalid input! " + getCommandHelpMessage());
		}
	}

	@Override
	protected String getCommandHelpMessage() {
		return COMMAND_HELP_MSG;
	}

	@Override
	protected CommandType getCommandType() {
		return CommandType.DEFEND;
	}
}
