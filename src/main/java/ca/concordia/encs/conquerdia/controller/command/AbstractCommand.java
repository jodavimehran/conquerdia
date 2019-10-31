package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCommand implements Command {

    protected final List<String> errorList = new ArrayList<>();
    protected final List<String> resultList = new ArrayList<>();

    @Override
    public final void execute(List<String> inputCommandParts) {
        errorList.clear();
        resultList.clear();
        if (!PhaseModel.getInstance().isValidCommand(getCommandType())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! Only below mentioned commands are valid at this phase.").append(System.getProperty("line.separator"));
            sb.append("[").append(PhaseModel.getInstance().getValidCommands()).append("]");
            showError(sb.toString());
            return;
        } else if (inputCommandParts.size() < getCommandType().getMinNumberOfParts()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! ").append(getCommandHelpMessage()).append(System.getProperty("line.separator"));
            showError(sb.toString());
            return;
        } else {
            try {
                runCommand(inputCommandParts);
                PhaseModel.getInstance().changePhase();
                PhaseModel.getInstance().addPhaseLogs(resultList);
                showError(errorList.stream().collect(Collectors.joining(System.getProperty("line.separator"))));
            } catch (Exception ex) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid Command! ").append(getCommandHelpMessage()).append(System.getProperty("line.separator"));
                showError(sb.toString());
                return;
            }
        }
    }

    protected abstract List<String> runCommand(List<String> inputCommandParts);

    protected abstract String getCommandHelpMessage();

    protected abstract CommandType getCommandType();

    private final void showError(String result) {
        CommandResultModel.getInstance().setResult(result);
    }
}
