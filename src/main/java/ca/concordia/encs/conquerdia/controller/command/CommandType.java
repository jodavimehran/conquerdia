package ca.concordia.encs.conquerdia.controller.command;


/**
 * Every command in the game must be declare in this Enum class and also every command must have an implementation Factory
 * that implement {@link Command command factory}.
 */
public enum CommandType {
    EDIT_CONTINENT("editcontinent", new EditContinentCommand(), 3),
    EDIT_COUNTRY("editcountry", new EditCountryCommand(), 3),
    EDIT_MAP("editmap", new EditMapCommand(), 2),
    EDIT_NEIGHBOR("editneighbor", new EditNeighborCommand(), 4),
    SHOW_MAP("showmap", new ShowMapCommand(), 1),
    SAVE_MAP("savemap", new SaveMapCommand(), 2),
    VALIDATE_MAP("validatemap", new ValidateMapCommand(), 1),
    LOAD_MAP("loadmap", new LoadMapCommand(), 2),
    GAME_PLAYER("gameplayer", new GamePlayerCommand(), 3),
    POPULATE_COUNTRIES("populatecountries", new PopulateCountriesCommand(), 1),
    PLACE_ARMY("placearmy", new PlaceArmyCommand(), 2),
    PLACE_ALL("placeall", new PlaceAllCommand(), 1),
    REINFORCE("reinforce", new ReinforceCommand(), 3),
    FORTIFY("fortify", new FortifyCommand(), 2);

    private final String name;
    private final Command command;
    private final int minNumberOfParts;

    /**
     * @param name    The name of the command which is the exact command that user must pass to the game.
     * @param command The implementation of the factory for the command
     */
    CommandType(String name, Command command, int minNumberOfParts) {
        this.name = name;
        this.command = command;
        this.minNumberOfParts = minNumberOfParts;
    }

    /**
     * @param name Name of the {@link CommandType command type}
     * @return return the {@link CommandType command type}
     */
    public static CommandType findCommandTypeByName(String name) {
        for (CommandType commandType : values()) {
            if (commandType.name.equals(name)) {
                return commandType;
            }
        }
        return null;
    }

    /**
     * @return return the Implementation of the factory
     */
    public Command getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public int getMinNumberOfParts() {
        return minNumberOfParts;
    }
}
