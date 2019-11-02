package ca.concordia.encs.conquerdia.controller.command;

import java.util.List;

import ca.concordia.encs.conquerdia.exception.ValidationException;

public class AttackCommand extends AbstractCommand {
    public final static String COMMAND_HELP_MSG = "A valid \"attack\" command format is: \"attack fromcountry tocountry numdice -allout\" or \"attack -noattack\".";
	@Override
	protected void runCommand(List<String> inputCommandParts) throws ValidationException {

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