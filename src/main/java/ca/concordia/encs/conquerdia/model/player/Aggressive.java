package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.List;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its strongest country, then always attack
 * with it until it cannot attack anymore, then fortifies in order to maximize aggregation of forces in one country).
 */
class Aggressive extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Aggressive(String name) {
        super(name);
    }

    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @return
     * @throws ValidationException
     */
    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        return super.reinforce(findMyStrongestCountry().getName(), unplacedArmies);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     * @return result of command
     * @throws ValidationException when a validation exception happen
     */
    @Override
    public String placeArmy(String countryName) throws ValidationException {
        return super.placeArmy(findMyStrongestCountry().getName());
    }

    @Override
    public List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut, boolean noAttack) throws ValidationException {
        if (!hasAttackOpportunities()) {
            noAttack = true;
        } else {
            Country myStrongestCountry = findMyStrongestCountry();
            Country weakestAdjacent = null;
            for (Country adjacent : myStrongestCountry.getAdjacentCountries()) {
                if ((weakestAdjacent == null || adjacent.getNumberOfArmies() < weakestAdjacent.getNumberOfArmies()) && !adjacent.getOwner().equals(this)) {
                    weakestAdjacent = adjacent;
                }
            }
            fromCountryName = myStrongestCountry.getName();
            toCountryName = weakestAdjacent.getName();
            numdice = -1;
            isAllOut = true;
            noAttack = false;
        }
        return super.attack(fromCountryName, toCountryName, numdice, isAllOut, noAttack);
    }
}

