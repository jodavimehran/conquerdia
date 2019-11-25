package ca.concordia.encs.conquerdia.model.player;

/**
 * A human player that requires user interaction to make decisions.
 */
public class Human extends AbstractPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Human(String name) {
        super(name);
    }

    /**
     * @return true if this player is a computer
     */
    @Override
    boolean isComputer() {
        return false;
    }
}
