package ca.concordia.encs.conquerdia.engine.command;

import ca.concordia.encs.conquerdia.engine.command.factory.*;


/**
 * Every command in the game must be declare in this Enum class and also every command must have an implementation Factory
 * that implement {@link CommandFactory command factory}.
 */
public enum CommandType {
    EDIT_CONTINENT("editcontinent", new EditContinentCommandFactory()),
    EDIT_COUNTRY("editcountry", new EditCountryCommandFactory()),
    EDIT_NEIGHBOR("editneighbor", new EditNeighborCommandFactory()),
    SHOW_MAP("showmap", new ShowMapCommandFactory()),
    SAVE_MAP("savemap", new SaveMapCommandFactory()),
    EDIT_MAP("editmap", new EditMapCommandFactory()),
    VALIDATE_MAP("validatemap", new ValidateMapCommandFactory()),
    LOAD_MAP("loadmap", new LoadMapCommandFactory()),
    GAME_PLAYER("gameplayer", new GamePlayerCommandFactory()),
    POPULATE_COUNTRIES("populatecountries", new PopulateCountriesCommandFactory()),
    PLACE_ARMY("placearmy", new PlaceArmyCommandFactory()),
    PLACE_ALL("placeall", new PlaceAllCommandFactory()),
    REINFORCE("reinforce", new ReinforceCommandFactory()),
    FORTIFY("fortify", new FortifyCommandFactory());

    private final String name;
    private final CommandFactory factory;

    /**
     * @param name    The name of the command which is the exact command that user must pass to the game.
     * @param factory The implementation of the factory for the command
     */
    CommandType(String name, CommandFactory factory) {
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
    public CommandFactory getFactory() {
        return factory;
    }
}
