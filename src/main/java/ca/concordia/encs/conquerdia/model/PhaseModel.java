package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.util.Observable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represent the phases of the game
 */
public class PhaseModel extends Observable {
    private static PhaseModel instance;
    private PhaseTypes currentPhase = PhaseTypes.NONE;
    private Queue<Player> players = new LinkedList<>();
    private Player currentPlayer;


    /**
     * private Constructor to implementation of the Singleton Pattern
     */
    private PhaseModel() {
    }

    /**
     * This method is used for getting a single instance of the {@link PhaseModel}
     *
     * @return single instance of the {@link PhaseModel phase}
     */
    public static PhaseModel getInstance() {
        if (instance == null) {
            synchronized (CommandResultModel.class) {
                if (instance == null) {
                    instance = new PhaseModel();
                }
            }
        }
        return instance;
    }

    public void addPlayer(Player player) {
        if (currentPlayer == null) {
            currentPlayer = player;
        } else {
            players.add(player);
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void changePhase() {
        switch (currentPhase) {
            case FORTIFICATION:
                currentPhase = PhaseTypes.REINFORCEMENT;
                giveTurnToAnotherPlayer();
                setChanged();
                notifyObservers(this);
                currentPlayer.calculateNumberOfReinforcementArmies();
                CommandResultModel.getInstance().setResult(String.format("Dear %s, Congratulations! You've got %d armies at this phase! You can place them wherever in your territory.", currentPlayer.getUnplacedArmies()));
                break;
        }
    }

    /**
     * give turn to another player based on player positions
     */
    public void giveTurnToAnotherPlayer() {
        players.add(currentPlayer);
        currentPlayer = players.poll();
    }

    public boolean isValidCommand(CommandType commandType) {
        return currentPhase.validCommands.contains(commandType);
    }

    public String getValidCommands() {
        return currentPhase.validCommands.stream().map(CommandType::getName).collect(Collectors.joining(", "));
    }

    public String getPhaseStatus() {
        return String.format("Phase: %s, Player: %s", currentPhase.getName(), currentPlayer.getName());
    }

    /**
     * Phase Types
     */
    public enum PhaseTypes {
        NONE("None", new HashSet<>(Arrays.asList(CommandType.LOAD_MAP, CommandType.EDIT_MAP))),
        EDIT_MAP("Edit Map", new HashSet<>(Arrays.asList(CommandType.EDIT_CONTINENT, CommandType.EDIT_COUNTRY, CommandType.EDIT_NEIGHBOR, CommandType.SHOW_MAP, CommandType.SAVE_MAP, CommandType.VALIDATE_MAP))),
        START_UP("Startup", new HashSet<>(Arrays.asList(CommandType.SHOW_MAP, CommandType.GAME_PLAYER, CommandType.POPULATE_COUNTRIES))),
        REINFORCEMENT("Reinforcement", new HashSet<>(Arrays.asList(CommandType.SHOW_MAP))),
        FORTIFICATION("Fortification", new HashSet<>(Arrays.asList(CommandType.SHOW_MAP))),
        ATTACK("Attack", new HashSet<>(Arrays.asList(CommandType.SHOW_MAP)));

        private final String name;
        private final Set<CommandType> validCommands;

        /**
         * @param validCommands The legal command that can be executed during this Phase of game
         */
        PhaseTypes(String name, Set<CommandType> validCommands) {
            this.name = name;
            this.validCommands = validCommands;
        }

        /**
         * @return name of the phase
         */
        public String getName() {
            return name;
        }

    }
}
