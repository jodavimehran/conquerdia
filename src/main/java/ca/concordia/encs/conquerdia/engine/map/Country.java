package ca.concordia.encs.conquerdia.engine.map;

import ca.concordia.encs.conquerdia.engine.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a country in the world map of the game.
 * <p>
 * A country can place on a Continent
 * A country has a set of adjacent countries
 */
public class Country {

    /**
     * name of the country
     */
    private final String name;
    /**
     * Represents the borders of a country.
     * All neighbor countries of a country must add to this set.
     */
    private final Map<String, Country> adjacentCountries = new HashMap<>();
    /**
     * Represents the continent that this country is placed on
     */
    private final Continent continent;

    /**
     * Number of armies that are placed in this country
     */
    private int numberOfArmies;

    /**
     * The player that this county belongs to
     */
    private Player owner;

    /**
     * Country class has one and only one private constructor to force the user's of this class to use the Builder {@link Country.Builder}
     */
    public Country(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
    }

    /**
     * @return the owner of this country
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @param owner The owner of this country
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Add the specified country to this country's adjacency list
     * This implies that this country has a an edge / border to the specified country
     *
     * @param adjacentCountry A country which should have a border to this country
     */
    public void addNeighbour(Country adjacentCountry) {
        adjacentCountries.put(adjacentCountry.getName(), adjacentCountry);
    }

    /**
     * remove an adjacent country
     *
     * @param adjacentCountryName
     * @return <tt>true</tt> if the adjacent country was successfully removed, else return <tt>false</tt>
     */
    public boolean removeNeighbour(String adjacentCountryName) {
        return adjacentCountries.remove(adjacentCountryName) != null;
    }

    /**
     * This getter method return the name of this country
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The number of armies that are placed in this country
     */
    public int getNumberOfArmies() {
        return numberOfArmies;
    }


    /**
     * This method place one army to this country
     */
    public void placeOneArmy() {
        numberOfArmies++;
    }

    /**
     * This method place a number of aries to this country
     *
     * @param numberOfArmiesToBePlaces The number of armies to be places in this country
     */
    public void placeArmy(int numberOfArmiesToBePlaces) {
        if (numberOfArmiesToBePlaces > 0)
            numberOfArmies += numberOfArmiesToBePlaces;
    }

    /**
     * This method remove armies from this country.
     *
     * @param numberOfArmiesToRemove The number of armies to remove from this county.
     */
    public void removeArmy(int numberOfArmiesToRemove) {
        this.numberOfArmies -= numberOfArmiesToRemove;
        if (this.numberOfArmies < 0)
            this.numberOfArmies = 0;
    }

    /**
     * This method returns the name of the country.
     *
     * @return returns the name of the country.
     */
    @Override
    public String toString() {
        return this.getName();
    }


    /**
     * This getter method return the continent object that this country is placed on
     *
     * @return the continent object that this country is placed on
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * @return return all adjacent countries
     */
    public Set<Country> getAdjacentCountries() {
        return adjacentCountries.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    /**
     * @param countryName
     * @return
     */
    public boolean isAdjacentTo(String countryName) {
        return adjacentCountries.containsKey(countryName);
    }

    /**
     * The Builder for {@link Country}
     */
    public static class Builder {
        private final Country country;

        /**
         * Country-Builder's constructor has two parameter because name and continent for a country is required.
         *
         * @param name The name of a country
         */
        public Builder(String name, Continent continent) {
            country = new Country(name, continent);
        }


        /**
         * @return return built country
         */
        public Country build() {
            return this.country;
        }
    }
}
