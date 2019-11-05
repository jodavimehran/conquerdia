package ca.concordia.encs.conquerdia;

import ca.concordia.encs.conquerdia.controller.CommandController;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.map.io.FileHelper;
import ca.concordia.encs.conquerdia.model.map.io.MapIO;
import ca.concordia.encs.conquerdia.view.CardExchangeView;
import ca.concordia.encs.conquerdia.view.CommandResultView;
import ca.concordia.encs.conquerdia.view.PhaseView;

import javax.swing.*;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Project Starter
 */
public class Starter extends JFrame {
    private final CommandController commandController = new CommandController();

    /**
     * Project main method
     *
     * @param args input arguments
     */
    public static void main(String[] args) {
        FileHelper.CreateDirectoryIfNotExists(MapIO.MAPS_FOLDER_PATH);
        new Starter().start(new Scanner(System.in), System.out);
    }

    /**
     * @param scanner the scanner
     * @param output  the output stream
     */
    void start(Scanner scanner, PrintStream output) {
        output.println("Welcome to Conquerdia Game");
        CommandResultModel.getInstance().addObserver(new CommandResultView(output));
        PhaseModel.getInstance().addObserver(new PhaseView(output));
        CardExchangeModel.getInstance().addObserver(new CardExchangeView());
        while (true) {
            output.print("> ");
            String commandStr = scanner.nextLine();
            if ("exit".equals(commandStr)) {
                break;
            } else {
                commandController.executeCommand(commandStr);
            }
        }
    }
}
