package ca.concordia.encs.conquerdia.engine.map;

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
     * Country class has one and only one private constructor to force the user's of this class to use the Builder {@link Country.Builder}
     */
    public Country(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
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
