package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.PlayersModel;

import java.util.List;

public class AttackCommand extends AbstractCommand {
	public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";

	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {

		if (hasMinimumNumberofParameters(inputCommandParts)) {
			Player currentPlayer = PlayersModel.getInstance().getCurrentPlayer();

			if (isNoAttack(inputCommandParts)) {
				currentPlayer.setAttackFinished();
				phaseLogList.add(String.format("\"-noattack\" is selected by %s.", currentPlayer.getName()));
				return;
			}

			String fromCountryName = inputCommandParts.get(1);
			String toCountryName = inputCommandParts.get(2);
			String thirdParam = inputCommandParts.get(3);

			// attack A B -allout
			if ("-allout".equals(thirdParam)) {
				currentPlayer.attack(fromCountryName, toCountryName, -1, true);
				phaseLogList.add(String.format("Player %s all out attacked from %s to %s .",
						currentPlayer.getName(), fromCountryName, toCountryName));
			} else {
				try {
					int numberOfDices = Integer.valueOf(thirdParam);

					currentPlayer.attack(fromCountryName, toCountryName, numberOfDices, false);
					phaseLogList.add(String.format("Player %s attacked from %s to %s by %d armies.",
							currentPlayer.getName(), fromCountryName, toCountryName, numberOfDices));
				} catch (NumberFormatException ex) {
					throw new ValidationException(
							"Number of dices(3rd parameter) must be a positive integer number or -allout or -noattack.");
				}
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
		return CommandType.ATTACK;
	}

	private boolean isNoAttack(List<String> inputCommandParts) {
		return inputCommandParts.contains("-noattack") &&
				(inputCommandParts.size() == 2 || inputCommandParts.size() == 4);
	}
}
