package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;

public class AttackMoveCommand extends AbstractCommand {

	private static final String COMMAND_HELP_MSG = "A valid \"attackmove\" command format is \"attackmove num\".";

	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		if (hasMinimumNumberofParameters(inputCommandParts)) {
			try {
				int numOfArmiesToMove = Integer.parseInt(inputCommandParts.get(1));
				if (numOfArmiesToMove < 0) {
					throw new NumberFormatException();
				}

				Player player = PhaseModel.getInstance().getCurrentPlayer();
				phaseLogList.add(player.attackMove(numOfArmiesToMove));

			} catch (NumberFormatException ex) {
				throw new ValidationException("Number of armies to be moved must be a positive integer number.");
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
