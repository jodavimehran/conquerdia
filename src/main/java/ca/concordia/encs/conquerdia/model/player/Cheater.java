package ca.concordia.encs.conquerdia.model.player;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    /**
     * @param country doubles the number of armies on this country
     */
    private static void doubleNumberOfArmy(Country country) {
        country.placeArmy(country.getNumberOfArmies());
    }

    @Override
    public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
        countries.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .forEach(Cheater::doubleNumberOfArmy);
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
        List<Country> toAddCountries = new ArrayList<>();
        countries.entrySet().stream().forEach(entry -> entry.getValue().getAdjacentCountries().stream().forEach(country -> {
            toAddCountries.add(country);
        }));
        toAddCountries.stream().forEach(country -> {
                    country.getOwner().removeCountry(country.getName());
                    addCountry(country);
                }
        );
        attackFinished = true;
        return Arrays.asList(String.format("%s conquers all the neighbors of all its countries!!!!", name));
    }

    @Override
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy, boolean noneFortify) throws ValidationException {
        countries.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(Country::isAdjacentToOtherPlayerCountries)
                .forEach(Cheater::doubleNumberOfArmy);
        fortificationFinished = true;

        return String.format("%s doubles the number of armies on its countries that have neighbors that belong to other players", name);
    }
}
