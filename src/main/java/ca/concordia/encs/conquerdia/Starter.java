package ca.concordia.encs.conquerdia;

import ca.concordia.encs.conquerdia.controller.CommandController;
import ca.concordia.encs.conquerdia.model.CardExchangeModel;
import ca.concordia.encs.conquerdia.model.CommandResultModel;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import ca.concordia.encs.conquerdia.model.map.io.FileHelper;
import ca.concordia.encs.conquerdia.model.map.io.MapIO;
import ca.concordia.encs.conquerdia.view.CardExchangeView;
import ca.concordia.encs.conquerdia.view.CommandResultView;
import ca.concordia.encs.conquerdia.view.PhaseView;
import ca.concordia.encs.conquerdia.view.PlayersWorldDominationView;

import javax.swing.*;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        CardExchangeModel.getInstance().addObserver(new CardExchangeView(output));
        PlayersModel.getInstance().addObserver(new PlayersWorldDominationView(output));
        while (!PhaseModel.getInstance().isFinished()) {
            output.print("> ");
            String commandStr = scanner.nextLine();
            if ("exit".equals(commandStr)) {
                return;
            }
            if (PhaseModel.getInstance().getCurrentPhase().equals(PhaseModel.PhaseTypes.NONE) && commandStr.startsWith("tournament")) {
                try {
                    HashSet<String> playerTypes = new HashSet<>(Arrays.asList("aggressive", "benevolent", "random", "cheater"));
                    Set<String> maps = new HashSet<>();
                    Set<String> players = new HashSet<>();
                    int numberOfGames = -1;
                    int maxNumberOfTurns = -1;
                    String status = "start";
                    {
                        int i = 0;
                        for (String part : commandStr.trim().split(" ")) {
                            i++;
                            switch (status) {
                                case "start":
                                    if ("tournament".equals(part) && i == 1) {
                                        continue;
                                    }
                                    if ("-M".equals(part) && i == 2) {
                                        status = "maps";
                                        continue;
                                    }
                                    throw new Exception();
                                case "maps":
                                    if ("-P".equals(part) && maps.size() > 0) {
                                        status = "players";
                                        continue;
                                    }
                                    if (maps.size() < 6) {
                                        maps.add(part);
                                        continue;
                                    }
                                    throw new Exception();
                                case "players":
                                    if ("-G".equals(part) && players.size() > 0) {
                                        status = "games";
                                        continue;
                                    }
                                    if (players.size() < 5) {
                                        if (playerTypes.contains(part.toLowerCase())) {
                                            players.add(part.toLowerCase());
                                            continue;
                                        }
                                    }
                                    throw new Exception();
                                case "games":
                                    if ("-D".equals(part) && numberOfGames != -1) {
                                        status = "maxTurns";
                                        continue;
                                    }
                                    if (numberOfGames == -1) {
                                        Integer integer = Integer.valueOf(part);
                                        if (integer >= 1 && integer <= 5) {
                                            numberOfGames = integer;
                                            continue;
                                        }
                                    }
                                    throw new Exception();
                                case "maxTurns":
                                    if (maxNumberOfTurns == -1) {
                                        Integer integer = Integer.valueOf(part);
                                        if (integer >= 10 && integer <= 50) {
                                            maxNumberOfTurns = integer;
                                            continue;
                                        }
                                    }
                                    throw new Exception();

                            }
                        }
                    }
                    for (String map : maps) {
                        for (int i = 0; i < numberOfGames; i++) {
                            String result;
                            CommandResultModel.clearModel();
                            PhaseModel.clear();
                            CardExchangeModel.clear();
                            PlayersModel.clear();

                            PlayersModel.getInstance().addObserver(new PlayersWorldDominationView(output));
                            PhaseModel.getInstance().addObserver(new PhaseView(output));

                            {
                                commandController.executeCommand("loadmap " + map);
                            }
                            if (PhaseModel.getInstance().getCurrentPhase().equals(PhaseModel.PhaseTypes.START_UP)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("gameplayer");

                                for (String player : players) {
                                    sb.append(" -add ").append(player).append(" ").append(player);
                                }
                                commandController.executeCommand(sb.toString());
                                commandController.executeCommand("populatecountries");
                                result = PhaseModel.getInstance().getCurrentPlayer().getName();
                            } else {
                                result = "map not found!";
                            }
                        }
                    }

                } catch (Exception ex) {
                    output.println("Invalid Tournament Command! tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
                }
            } else {
                commandController.executeCommand(commandStr);
            }
        }
        output.println("************************************************************************************************");
        output.println("************************************************************************************************");
        output.println("************************************************************************************************");
        output.println("Game Over!");
        output.println(String.format("Congratulations %s you win the game.", PlayersModel.getInstance().getCurrentPlayer().getName()));
    }
}
