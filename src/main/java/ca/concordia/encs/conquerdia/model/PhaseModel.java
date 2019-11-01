package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.controller.command.CommandType;
import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import ca.concordia.encs.conquerdia.util.Observable;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represent the phases of the game
 */
public class PhaseModel extends Observable {
    private static PhaseModel instance;
    private final List<String> phaseLog = new ArrayList<>();
    private final Set<String> playerNames = new HashSet<>();
    private final Queue<Player> players = new LinkedList<>();
    private PhaseTypes currentPhase = PhaseTypes.NONE;
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

    /**
     * Add a new player to the game if player name will not found in current player
     * name is
     *
     * @param playerName name of the plater to add
     */
    public void addPlayer(String playerName) throws ValidationException {
        if (StringUtils.isBlank(playerName))
            throw new ValidationException("Player name is not valid!");
        if (playerNames.contains(playerName))
            throw new ValidationException(String.format("Player with name \"%s\" is already exist.", playerName));
        playerNames.add(playerName);
        Player player = new Player.Builder(playerName).build();
        if (currentPlayer == null) {
            currentPlayer = player;
        } else {
            players.add(player);
        }
    }

    /**
     * This Method remove a player
     *
     * @param playerName name of the player to remove
     */
    public void removePlayer(String playerName) throws ValidationException {
        if (!playerNames.contains(playerName))
            throw new ValidationException(String.format("Player with name \"%s\" is not found.", playerName));
        playerNames.remove(playerName);
        if (currentPlayer.getName().equals(playerName)) {
            currentPlayer = players.poll();
        } else {
            players.removeIf(player -> player.getName().equals(playerName));
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void changePhase() {
        switch (currentPhase) {
            case NONE: {
                if (WorldMap.getInstance().isMapLoaded()) {
                    changePhase(PhaseTypes.START_UP);
                } else if (WorldMap.getInstance().isReadyForEdit()) {
                    changePhase(PhaseTypes.EDIT_MAP);
                }
                break;
            }
            case EDIT_MAP: {
                if (WorldMap.getInstance().isMapLoaded()) {
                    changePhase(PhaseTypes.START_UP);
                }
                break;
            }
            case FORTIFICATION: {
                changePhase(PhaseTypes.REINFORCEMENT);
                giveTurnToAnotherPlayer();
                currentPlayer.calculateNumberOfReinforcementArmies();
                CommandResultModel.getInstance().addResult(String.format("Dear %s, Congratulations! You've got %d armies at this phase! You can place them wherever in your territory.", currentPlayer.getUnplacedArmies()));
                break;
            }
        }
    }

    /**
     * @param phaseTypes
     */
    private void changePhase(PhaseTypes phaseTypes) {
        phaseLog.clear();
        currentPhase = phaseTypes;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Phase: ").append(currentPhase.getName());
        if (currentPlayer != null)
            sb.append(",Player: ").append(currentPlayer.getName());
        if (!phaseLog.isEmpty()) {
            for (String log : phaseLog) {
                sb.append(System.getProperty("line.separator")).append(log);
            }
        }
        return sb.toString();
    }

    public void addPhaseLogs(List<String> logs) {
        if (logs != null && !logs.isEmpty()) {
            LocalTime now = LocalTime.now();
            logs.stream().forEach(log -> phaseLog.add(now + "-" + log));
            setChanged();
            notifyObservers(this);
        }
    }

    public void addPhaseLog(String log) {
        if (StringUtils.isNotBlank(log)) {
            phaseLog.add(java.time.LocalTime.now() + "-" + log);
            setChanged();
            notifyObservers(this);
        }
    }

    /**
     * Phase Types
     */
    public enum PhaseTypes {
        NONE("None", new HashSet<>(Arrays.asList(CommandType.LOAD_MAP, CommandType.EDIT_MAP))),
        EDIT_MAP("Edit Map", new HashSet<>(Arrays.asList(CommandType.LOAD_MAP, CommandType.EDIT_CONTINENT, CommandType.EDIT_COUNTRY, CommandType.EDIT_NEIGHBOR, CommandType.SHOW_MAP, CommandType.SAVE_MAP, CommandType.VALIDATE_MAP))),
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
