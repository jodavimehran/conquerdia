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

	public AttackCommand() {
		super();
	}

	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {
		validateAttackCommand(inputCommandParts);

		Player currentPlayer = PlayersModel.getInstance().getCurrentPlayer();
		if (isNoAttack) {
			handleNoAttack(currentPlayer);
		} else {
			ArrayList<String> logs = currentPlayer.attack(fromCountryName, toCountryName,
					isAllOut ? -1 : numberOfDices, isAllOut);
			phaseLogList.addAll(logs);
		}
	}
	/**
	 * 
	 * @param currentPlayer
	 * @throws ValidationException
	 */
	private void handleNoAttack(Player currentPlayer) throws ValidationException {

		if (currentPlayer.canPerformDefend()) {
			throw new ValidationException(DefendCommand.COMMAND_HELP_MSG);
		}

		if (!currentPlayer.canPerformAttackMove()) {
			currentPlayer.setAttackFinished();
			phaseLogList.add(String.format("\"-noattack\" is selected by %s.", currentPlayer.getName()));
		} else {
			throw new ValidationException(String.format(
					"Cannot finish attack since you have to place armies in your newly conquered country %s",
					currentPlayer.getBattle().getToCountry().getName()));
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCommandHelpMessage() {
		return COMMAND_HELP_MSG;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandType getCommandType() {
		return CommandType.ATTACK;
	}
	/**
	 * Validating the -noAttack command options in input parameters
	 * @param inputCommandParts input parameters of attack command.
	 * @return true if the noAtatck command is valid; otherwise return false.
	 * @throws ValidationException
	 */
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
	/**
	 * Validates the attack command inout parameters
	 * @param inputCommandParts input parameters parts.
	 * @throws ValidationException
	 */
	private void validateAttackCommand(List<String> inputCommandParts) throws ValidationException {

		if (!hasMinimumNumberofParameters(inputCommandParts) || inputCommandParts.size() > 4) {
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
					throw new ValidationException(
							"Number of dices(3rd parameter) must be a positive integer number or -allout or -noattack.");
				}
			}
		}
	}
}
