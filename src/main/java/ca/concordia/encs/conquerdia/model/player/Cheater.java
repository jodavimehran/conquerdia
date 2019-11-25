package ca.concordia.encs.conquerdia.model.player;

/**
 * A cheater computer player strategy whose reinforce() method doubles the number of armies on all its countries,
 * whose attack() method automatically conquers all the neighbors of all its countries, and whose fortify() method
 * doubles the number of armies on its countries that have neighbors that belong to other players.
 */
public class Cheater extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Cheater(String name) {
        super(name);
    }


}
