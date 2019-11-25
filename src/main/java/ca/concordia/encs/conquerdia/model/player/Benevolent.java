package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * A benevolent computer player strategy that focuses on protecting its weak countries (reinforces its weakest
 * countries, never attacks, then fortifies in order to move armies to weaker countries).
 */
public class Benevolent extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Benevolent(String name) {
        super(name);
    }

    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        return super.reinforce(findMyWeakestCountry().getName(), 1);
    }

    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy) throws ValidationException {
        Country weakest = findMyCountry(true);
        Country strongest = findMyCountry(false);

        numberOfArmy = (strongest.getNumberOfArmies() - weakest.getNumberOfArmies()) / 2;
        return super.fortify(fromCountryName, toCountryName, numberOfArmy);
    }

    @Override
    public ArrayList<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut) throws ValidationException {
        //TODO
        return null;
    }

    protected Country findMyStrongestCountry() {
        return findMyCountry(false, null);
    }

    protected Country findMyWeakestCountry() {
        return findMyCountry(false, null);
    }

    protected Country findMyStrongestCountry(Set<String> exclude) {
        return findMyCountry(false, exclude);
    }

    protected Country findMyWeakestCountry(Set<String> exclude) {
        return findMyCountry(false, exclude);
    }

    protected Country findMyCountry(boolean weakest, Set<String> exclude) {
        Map.Entry<String, Country> foundCountry = null;
        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            if (exclude != null && exclude.contains(entry.getKey())) {
                continue;
            }
            boolean adjacentWithEnemy = false;
            for (Country adjacent : entry.getValue().getAdjacentCountries()) {
                if (!adjacent.getOwner().getName().equals(name)) {
                    adjacentWithEnemy = true;
                    break;
                }
            }
            if (adjacentWithEnemy) {
                if (foundCountry == null) {
                    foundCountry = entry;
                } else {
                    if (weakest ? foundCountry.getValue().getNumberOfArmies() > entry.getValue().getNumberOfArmies() : foundCountry.getValue().getNumberOfArmies() < entry.getValue().getNumberOfArmies()) {
                        foundCountry = entry;
                    }
                }
            }
        }
        return foundCountry != null ? foundCountry.getValue() : null;
    }

}
