package ca.concordia.encs.conquerdia.view;

import ca.concordia.encs.conquerdia.model.CommandResultModel;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

public class CommandResultView implements Observer {
    private final PrintStream output;

    public CommandResultView(PrintStream output) {
        this.output = output;
    }

    @Override
    public void update(Observable o, Object arg) {
        output.println(((CommandResultModel) o).getResult());
    }
}
