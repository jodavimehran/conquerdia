package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.List;

public abstract class AbstractCommand implements Command {

    @Override
    public final void execute(List<String> inputCommandParts) {
        if (!PhaseModel.getInstance().isValidCommand(getCommandType())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! Only below mentioned commands are valid at this phase.").append(System.getProperty("line.separator"));
            sb.append("[").append(PhaseModel.getInstance().getValidCommands()).append("]");
            showResult(sb.toString());
            return;
        } else if (inputCommandParts.size() < getCommandType().getMinNumberOfParts()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! ").append(getCommandHelpMessage()).append(System.getProperty("line.separator"));
            showResult(sb.toString());
            return;
        } else {
            try {
                List<String> results = runCommand(inputCommandParts);
                PhaseModel.getInstance().changePhase();
                showResult(results);
            } catch (Exception ex) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid Command! ").append(getCommandHelpMessage()).append(System.getProperty("line.separator"));
                showResult(sb.toString());
                return;
            }
        }
    }

    protected abstract List<String> runCommand(List<String> inputCommandParts);

    protected abstract String getCommandHelpMessage();

    protected abstract CommandType getCommandType();

    private final void showResult(List<String> result) {
        if (result != null)
            result.stream().forEach(this::showResult);
    }

    private final void showResult(String result) {
        CommandResultModel.getInstance().setResult(result);
    }
}
