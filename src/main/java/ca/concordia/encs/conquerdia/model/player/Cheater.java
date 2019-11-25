package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;

import java.util.Arrays;
import java.util.List;

/**
 * A cheater computer player strategy whose reinforce() method doubles the number of armies on all its countries,
 * whose attack() method automatically conquers all the neighbors of all its countries, and whose fortify() method
 * doubles the number of armies on its countries that have neighbors that belong to other players.
 */
class Cheater extends AbstractComputerPlayer {

    /**
     * @param name The name of a player that must be determined when you want to create a player
     */
    public Cheater(String name) {
        super(name);
    }

    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        countries.entrySet().stream().forEach(entry -> {
            entry.getValue().placeArmy(entry.getValue().getNumberOfArmies());
        });
        unplacedArmies = 0;
        return String.format("%s doubles the number of armies on all its countries.", name);
    }

    /**
     * @param countryName Name of the country that one army be placed on it
     */
    public String placeArmy(String countryName) throws ValidationException {
        String[] countyNames = new String[countries.keySet().size()];
        countyNames = countries.keySet().toArray(countyNames);
        return super.placeArmy(countyNames[random.nextInt(countyNames.length)]);
    }

    @Override
    public List<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut, boolean noAttack) throws ValidationException {
        countries.entrySet().stream().forEach(entry -> entry.getValue().getAdjacentCountries().stream().forEach(country -> {
            country.getOwner().removeCountry(country.getName());
            addCountry(country);
        }));
        return Arrays.asList(String.format("%s conquers all the neighbors of all its countries!!!!"));
    }
}
