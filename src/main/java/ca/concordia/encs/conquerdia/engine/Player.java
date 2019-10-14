package ca.concordia.encs.conquerdia.engine;

/**
 * Represents a player in the Game
 */
public class Player {
    private final String name;

    private Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * The Builder for {@link Player}
     */
    public static class Builder {
        private final Player player;

        /**
         * Player-Builder's constructor has one parameter because a player must have name.
         *
         * @param name The name of a player
         */
        public Builder(String name) {
            player = new Player(name);
        }


        /**
         * @return return built player
         */
        public Player build() {
            return this.player;
        }
    }
}
