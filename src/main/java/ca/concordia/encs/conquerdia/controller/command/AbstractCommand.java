package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All Commands must extend this class.
 * This class implement all common things that is needed and is important to run a command
 */
public abstract class AbstractCommand implements Command {

    protected final List<String> errorList = new ArrayList<>();
    protected final List<String> phaseLogList = new ArrayList<>();
    protected final List<String> resultList = new ArrayList<>();

    /**
     * Clear all things
     */
    private void clear() {
        errorList.clear();
        phaseLogList.clear();
        resultList.clear();
        CommandResultModel.getInstance().clear();
    }

    /**
     * @param inputCommandParts the command line parameters.
     */
    @Override
    public final void execute(List<String> inputCommandParts) {
        clear();
        if (!PhaseModel.getInstance().isValidCommand(getCommandType())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Command! Only below mentioned commands are valid at this phase.").append(System.getProperty("line.separator"));
            sb.append("[").append(PhaseModel.getInstance().getValidCommands()).append("]");
            CommandResultModel.getInstance().addResult(sb.toString());
            return;
        } else if (inputCommandParts.size() < getCommandType().getMinNumberOfParts()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid \"").append(getCommandType().getName()).append("\" Command! ").append(getCommandHelpMessage());
            CommandResultModel.getInstance().addResult(sb.toString());
            return;
        } else {
            try {
                runCommand(inputCommandParts);
                PhaseModel.getInstance().changePhase();
                PhaseModel.getInstance().addPhaseLogs(phaseLogList);
                CommandResultModel.getInstance().addResultList(resultList);
                if (!errorList.isEmpty()) {
                    CommandResultModel.getInstance().addResult(errorList.stream().collect(Collectors.joining(System.getProperty("line.separator"))));
                }
            } catch (Exception ex) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid Command! ").append(getCommandHelpMessage());
                CommandResultModel.getInstance().addResult(sb.toString());
                return;
            }
        }
    }

    /**
     * @param inputCommandParts all input parameters parts
     * @throws ValidationException validation Exception
     */
    protected abstract void runCommand(List<String> inputCommandParts) throws ValidationException;

    /**
     * @return the help message for the command
     */
    protected abstract String getCommandHelpMessage();

    /**
     * @return the type of the command {@link CommandType}
     */
    protected abstract CommandType getCommandType();


}
