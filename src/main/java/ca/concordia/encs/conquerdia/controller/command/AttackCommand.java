package ca.concordia.encs.conquerdia.controller.command;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.Player;
import ca.concordia.encs.conquerdia.model.PlayersModel;

public class AttackCommand extends AbstractCommand {
	public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";

	private boolean isNoAttack;
	private String fromCountryName;
	private String toCountryName;
	private boolean isAllOut;
	private int numberOfDices;

	Player currentPlayer;

	public AttackCommand() {
		super();
		currentPlayer = PlayersModel.getInstance().getCurrentPlayer();
	}

	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		String error = validateAttackCommand(inputCommandParts);

		if (error == null) {
			if (isNoAttack) {
				handleNoAttack();
			} else {
				ArrayList<String> logs = currentPlayer.attack(fromCountryName, toCountryName,
						isAllOut ? -1 : numberOfDices, isAllOut);
				phaseLogList.addAll(logs);
			}
		} else {
			throw new ValidationException("Invalid input! " + error + " " + getCommandHelpMessage());
		}
	}

	private void handleNoAttack() throws ValidationException {
		if (!currentPlayer.canPerformAttackMove()) {
			currentPlayer.setAttackFinished();
			phaseLogList.add(String.format("\"-noattack\" is selected by %s.", currentPlayer.getName()));
		} else {
			throw new ValidationException(String.format(
					"Cannot finish attack since you have unplaced armies in your newly conquered country %s",
					currentPlayer.getBattle().getToCountry().getName()));
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

	private boolean validateNoAttackCommand(List<String> inputCommandParts) throws ValidationException {
		if (inputCommandParts.contains("-noattack")) {
			if ((inputCommandParts.size() == 2 || inputCommandParts.size() == 4)) {
				return true;
			} else {
				throw new ValidationException(getCommandHelpMessage());
			}
		}
		return false;
	}

	private String validateAttackCommand(List<String> inputCommandParts) throws ValidationException {
		String error = null;
		if (!hasMinimumNumberofParameters(inputCommandParts)) {
			throw new ValidationException(getCommandHelpMessage());
		}

		if (validateNoAttackCommand(inputCommandParts)) {
			isNoAttack = true;
		} else {
			fromCountryName = inputCommandParts.get(1);
			toCountryName = inputCommandParts.get(2);
			String thirdParam = inputCommandParts.get(3);

			if ("-allout".equals(thirdParam)) {
				isAllOut = true;
			} else {
				try {
					numberOfDices = Integer.valueOf(thirdParam);
					if (numberOfDices < 1) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException ex) {
					error = "Number of dices(3rd parameter) must be a positive integer number or -allout or -noattack.";
				}
			}
		}

		return error;
	}
}
