package ca.concordia.encs.conquerdia.engine.map;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a country in the world map of the game.
 * <p>
 * A country can place on a Continent
 * A country has a set of adjacent countries
 */
public class Country {

    /**
     * Represents the borders of a country.
     * All neighbor countries of a country must add to this set.
     */
    private final Set<Country> adjacentCountries = new HashSet<>();

    /**
     * name of the country
     */
    private String name;

    /**
     * Represents the continent that this country is placed on
     */
    private Continent continent;

    /**
     * Country class has one and only one private constructor to force the user's of this class to use the Builder {@link Country.Builder}
     */
    private Country() {
    }

    /**
     * This Method add this country to the map
     *
     * @return return message result
     */
    public String addCountry() {
        return continent.addCountry(this);
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
     * This getter method return the set that contains all neighbor countries of this country
     *
     * @return HashSet of adjacent countries
     */
    public Set<Country> getAdjacentCountries() {
        return adjacentCountries;
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
     * The Builder for {@link Country}
     */
    public static class Builder {
        private final Country country = new Country();

        /**
         * Country-Builder's constructor has two parameter because name and continent for a country is required.
         *
         * @param name The name of a country
         */
        public Builder(String name, Continent continent) {
            country.name = name;
            country.continent = continent;
        }

        /**
         * This method add a country to the set of adjacent countries.
         *
         * @param adjacentCountry The country that is adjacent to this country that is under construction
         * @return return current builder object
         */
        public Builder adjacentTo(Country adjacentCountry) {
            country.getAdjacentCountries().add(adjacentCountry);
            return this;
        }

        /**
         * This method add countries to the set of adjacent countries.
         *
         * @param adjacentCountries The countries that is adjacent to this country that is under construction
         * @return return current builder object
         */
        public Builder adjacentTo(Set<Country> adjacentCountries) {
            country.getAdjacentCountries().addAll(adjacentCountries);
            return this;
        }

        /**
         * @return return built country
         */
        public Country build() {
            return this.country;
        }
    }
}
