package ca.concordia.encs.conquerdia.engine.command;

import ca.concordia.encs.conquerdia.engine.command.factory.*;


/**
 * Every command in the game must be declare in this Enum class and also every command must have an implementation Factory
 * that implement {@link Command command factory}.
 */
public enum CommandType {
    EDIT_CONTINENT("editcontinent", new EditContinentCommand()),
    EDIT_COUNTRY("editcountry", new EditCountryCommand()),
    EDIT_NEIGHBOR("editneighbor", new EditNeighborCommand()),
    SHOW_MAP("showmap", new ShowMapCommand()),
    SAVE_MAP("savemap", new SaveMapCommand()),
    EDIT_MAP("editmap", new EditMapCommand()),
    VALIDATE_MAP("validatemap", new ValidateMapCommand()),
    LOAD_MAP("loadmap", new LoadMapCommand()),
    GAME_PLAYER("gameplayer", new GamePlayerCommand()),
    POPULATE_COUNTRIES("populatecountries", new PopulateCountriesCommand()),
    PLACE_ARMY("placearmy", new PlaceArmyCommand()),
    PLACE_ALL("placeall", new PlaceAllCommand()),
    REINFORCE("reinforce", new ReinforceCommand()),
    FORTIFY("fortify", new FortifyCommand());

    private final String name;
    private final Command factory;

    /**
     * @param name    The name of the command which is the exact command that user must pass to the game.
     * @param factory The implementation of the factory for the command
     */
    CommandType(String name, Command factory) {
        this.name = name;
        this.factory = factory;
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
        return factory;
    }
}
