package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.Continent;
import ca.concordia.encs.conquerdia.engine.map.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a player in the Game
 */
public class Player {
    /**
     * Player Name
     */
    private final String name;

    /**
     * Countries owned by this player
     */
    private final HashMap<String, Country> countries = new HashMap<>();

    /**
     * Continents owned by this player
     */
    private final HashMap<String, Continent> continents = new HashMap<>();

    /**
     * The number of armies that belong to this player and are not placed on any country.
     */
    private int unplacedArmies = 0;


    /**
     * @param name The name of a player must be determined when you want to create a player
     */
    private Player(String name) {
        this.name = name;
    }

    /**
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * @param add The number of armies that you want to add to unplaced armies of this player
     */
    public void addUnplacedArmies(int add) {
        unplacedArmies += add;
    }

    /**
     * @param minus the number of armies that you want to minus from unplaced armies of this player
     */
    public void minusUnplacedArmies(int minus) {
        unplacedArmies -= minus;
        if (unplacedArmies < 0)
            unplacedArmies = 0;
    }

    /**
     * Add a country to the list of the countries that owned by this player
     *
     * @param country The country to add
     */
    public void addCountry(Country country) {
        countries.put(country.getName(), country);
    }

    /**
     * Remove a country from the list of the countries that owned by this player
     *
     * @param countryName
     */
    public void removeCountry(String countryName) {
        countries.remove(countryName);
    }

    /**
     * @return the number of countries this player owns
     */
    public int getNumberOfCountries() {
        return countries.size();
    }

    /**
     * @return name of the countries this player owns
     */
    public Set<String> getCountryNames() {
        return countries.keySet();
    }

    /**
     * @return the number of continents this player owns
     */
    public int getNumberOfContinent() {
        return continents.size();
    }

    /**
     * Add a continent to the list of the continents that owned by this player
     *
     * @param continent The continent to be added to the list of continents that owned by this player
     */
    public void addContinent(Continent continent) {
        continents.put(continent.getName(), continent);
    }

    /**
     * Remove a continent from the list of the continents that owned by this player
     *
     * @param continentName Name of the continent to be removed
     */
    public void removeContinent(String continentName) {
        continents.remove(continentName);
    }


    /**
     * Returns <tt>true</tt> if this player own all of the countries of the specified collection.
     *
     * @param countries countries to be checked for owned by this player
     * @return <tt>true</tt> if this player own all of the countries of the specified collection
     */
    public boolean ownedAll(Set<String> countries) {
        return this.countries.keySet().containsAll(countries);
    }

    /**
     * @return number of reinforcement armies according to the Risk rules.
     */
    public int calculateNumberOfReinforcementArmies() {
        int numberOfReinforcementArmies = 0;
        numberOfReinforcementArmies += countries.size() / 3;
        for (Map.Entry<String, Continent> entry : continents.entrySet()) {
            numberOfReinforcementArmies += entry.getValue().getValue();
        }
        if (numberOfReinforcementArmies < 3)
            numberOfReinforcementArmies = 3;
        unplacedArmies = numberOfReinforcementArmies;
        return numberOfReinforcementArmies;

    }

    /**
     * @return The number of armies that belong to this player and are not placed on any country.
     */
    public int getUnplacedArmies() {
        return unplacedArmies;
    }

    /**
     * The Builder for {@link Player}
     */
    public static class Builder {
        private final Player player;

        /**
         * Player-Builder's constructor has one parameter because a player must have name.
         *
         * @param name The name of a player
         */
        public Builder(String name) {
            player = new Player(name);
        }


        /**
         * @return return built player
         */
        public Player build() {
            return this.player;
        }
    }
}
