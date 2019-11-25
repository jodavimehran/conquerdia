package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.Map;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its strongest country, then always attack
 * with it until it cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country).
 */
public class Aggressive extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Aggressive(String name) {
        super(name);
    }

    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        Map.Entry<String, Country> strongestCountry = null;
        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            boolean adjacentWithEnemy = false;
            for (Country adjacent : entry.getValue().getAdjacentCountries()) {
                if (!adjacent.getOwner().getName().equals(name)) {
                    adjacentWithEnemy = true;
                    break;
                }
            }
            if (adjacentWithEnemy) {
                if (strongestCountry == null) {
                    strongestCountry = entry;
                } else {
                    if (strongestCountry.getValue().getNumberOfArmies() < entry.getValue().getNumberOfArmies()) {
                        strongestCountry = entry;
                    }
                }
            }
        }

        return super.reinforce(strongestCountry.getValue().getName(), unplacedArmies);
    }

}

