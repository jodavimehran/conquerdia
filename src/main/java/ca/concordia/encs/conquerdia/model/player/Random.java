package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;

import java.security.SecureRandom;

/**
 * A random computer player strategy that reinforces random a random country, attacks a random number of times a random
 * country, and fortifies a random country, all following the standard rules for each phase
 */
public class Random extends AbstractComputerPlayer {

    private SecureRandom random;

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Random(String name) {
        super(name);
    }


    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        String[] countyNames = new String[countries.keySet().size()];
        countyNames = countries.keySet().toArray(countyNames);

        return super.reinforce(countyNames[random.nextInt(countyNames.length)], random.nextInt(unplacedArmies) + 1);
    }
}
