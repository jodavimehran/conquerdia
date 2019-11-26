package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.model.map.Country;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;

/**
 * The player that is a computer
 */
abstract class AbstractComputerPlayer extends AbstractPlayer {

    /**
     * random generator
     */
    protected SecureRandom random = new SecureRandom();

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
    public boolean isComputer() {
        return true;
    }


    protected Country findMyStrongestCountry() {
        return findMyCountry(false, null);
    }

    protected Country findMyWeakestCountry() {
        return findMyCountry(true, null);
    }

    protected Country findMyStrongestCountry(Set<String> exclude) {
        return findMyCountry(false, exclude);
    }

    protected Country findMyWeakestCountry(Set<String> exclude) {
        return findMyCountry(true, exclude);
    }

    protected Country findMyCountry(boolean weakest, Set<String> exclude) {
        Map.Entry<String, Country> foundCountry = null;
        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            if ((exclude != null && exclude.contains(entry.getKey()))) {
                continue;
            }
            if (foundCountry == null) {
                foundCountry = entry;
            } else {
                if (weakest ? foundCountry.getValue().getNumberOfArmies() > entry.getValue().getNumberOfArmies() : foundCountry.getValue().getNumberOfArmies() < entry.getValue().getNumberOfArmies()) {
                    foundCountry = entry;
                }
            }

        }
        return foundCountry != null ? foundCountry.getValue() : null;
    }
}
