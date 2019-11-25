package ca.concordia.encs.conquerdia.model.player;

/**
 * The player that is a computer
 */
public abstract class AbstractComputerPlayer extends AbstractPlayer {
    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public AbstractComputerPlayer(String name) {
        super(name);
    }


    /**
     * @return true if this player is a computer
     */
    @Override
    boolean isComputer() {
        return true;
    }

}
