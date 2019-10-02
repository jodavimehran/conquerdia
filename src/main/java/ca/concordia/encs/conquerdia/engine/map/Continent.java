package ca.concordia.encs.conquerdia.engine.map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Continent in the world map of the game.
 * <p>
 * A Continent has a set of countries
 * A Continent belongs to a Map
 */
public class Continent {

    private final Map<String, Country> countries = new HashMap<>();
    private String name;
    private Integer value;
    private WorldMap worldMap;

    private Continent() {
    }

    public final Set<String> getCountriesName() {
        return countries.keySet();
    }

    public final Set<Country> getCountries() {
        return countries.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    /**
     * //TODO: DOCUMENTATION
     *
     * @return
     */
    public String removeContinentFromWorldMap() {
        return worldMap.removeContinent(this);
    }

    /**
     * TODO: Java Doc
     *
     * @param country
     * @return
     */
    public String addCountry(Country country) {
        String countryName = country.getName();
        if (worldMap.getCountriesName().contains(countryName)) {
            return String.format("Continent with name \"%s\" was not found.", countryName);
        }
        countries.put(countryName, country);
        return String.format("Country with name \"%s\" is successfully added to World Map", countryName);
    }

    /**
     * @return Return the name of this Continent.
     */
    public String getName() {
        return name;
    }

    /**
     * This Method add this continent to the map
     *
     * @return return a success message if the list of the continents in the world map does not contain this continent,
     * this condition may happen when someone try to call this function more than one time. So, if this continent is
     * already placed in the map a relevant message will return.
     */
    public String addContinentToWorldMap() {
        return worldMap.addContinent(this);
    }

    /**
     * @return return the value of the Continent
     */
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Continent continent = (Continent) o;

        return new EqualsBuilder()
                .append(name, continent.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    /**
     * The Builder for {@link Continent}
     */
    public static class Builder {
        private final Continent continent = new Continent();

        /**
         * Continent-Builder's constructor has one parameter because the name for a continent is required.
         *
         * @param name The name of continent that is required for creation of a continent
         */
        public Builder(String name) {
            continent.name = name;
        }

        public Builder setValue(Integer value) {
            continent.value = value;
            return this;
        }

        /**
         * @param worldMap
         * @return return current builder object
         */
        public Builder setWorldMap(WorldMap worldMap) {
            continent.worldMap = worldMap;
            return this;
        }

        /**
         * @return return the continent that is built by this builder
         */
        public Continent build() {
            return this.continent;
        }
    }
}
