package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

import java.util.HashSet;
import java.util.List;

/**
 * A benevolent computer player strategy that focuses on protecting its weak countries (reinforces its weakest
 * countries, never attacks, then fortifies in order to move armies to weaker countries).
 */
class Benevolent extends AbstractComputerPlayer {

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
        Country weakest = null;
        {
            HashSet<String> exclude = new HashSet<>();
            while (weakest == null) {
                weakest = findMyWeakestCountry(exclude);
                boolean adjacentWithFriend = false;
                for (Country adjacent : weakest.getAdjacentCountries()) {
                    if (adjacent.getOwner().getName().equals(name)) {
                        adjacentWithFriend = true;
                        break;
                    }
                }
                if (adjacentWithFriend) {
                    break;
                } else {
                    exclude.add(weakest.getName());
                    weakest = null;
                }
            }
        }
        Country strongest = null;
        {
            HashSet<String> exclude = new HashSet<>();
            while (strongest == null) {
                strongest = findMyStrongestCountry(exclude);
                if (!WorldMap.isTherePath(strongest, weakest)) {
                    exclude.add(strongest.getName());
                    strongest = null;
                }
            }
        }
        numberOfArmy = (strongest.getNumberOfArmies() - weakest.getNumberOfArmies()) / 2;
        return super.fortify(fromCountryName, toCountryName, numberOfArmy);
    }

    @Override
    public List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut, boolean noAttack) throws ValidationException {
        return super.attack(fromCountryName, toCountryName, numdice, isAllOut, true);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     * @return result of command
     * @throws ValidationException when a validation exception happen
     */
    @Override
    public String placeArmy(String countryName) throws ValidationException {
        return super.placeArmy(findMyWeakestCountry().getName());
    }

}
