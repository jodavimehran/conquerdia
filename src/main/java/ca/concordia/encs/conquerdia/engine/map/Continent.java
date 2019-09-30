package ca.concordia.encs.conquerdia.engine.map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

/**
 * Represents a Continent in the world map of the game.
 * <p>
 * A Continent has a set of countries
 * A Continent belongs to a Map
 */
public class Continent {
    private Integer id;
    private String name;
    private Integer value;
    private Set<Country> countries;
    private WorldMap worldMap;

    private Continent() {
    }

    /**
     * This Method add this continent to the map
     *
     * @return return a success message if the list of the continents in the world map does not contain this continent,
     * this condition may happen when someone try to call this function more than one time. So, if this continent is
     * already placed in the map a relevant message will return.
     */
    public String addContinentToWorldMap() {
        if (!worldMap.getContinents().contains(this)) {
            worldMap.getContinents().add(this);
            return String.format("Continent with name \"%s\" is successfully added to World Map", name);
        } else {
            return String.format("Continent with name \"%s\" already exists in World Map!", name);
        }
    }

    /**
     * //TODO: DOCUMENTATION
     *
     * @return
     */
    public String removeContinentFromWorldMap() {
        if (worldMap.getContinents().contains(this)) {
            worldMap.getContinents().remove(this);
            return String.format("Continent with name \"%s\" is successfully removed from World Map", name);
        } else {
            return String.format("Continent with name \"%s\" does not exist in World Map!", name);
        }
    }

    /**
     * @return Return the name of this Continent.
     */
    public String getName() {
        return name;
    }

    /**
     * @return return the value of the Continent
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @return Return the Id o the Continent
     */
    public Integer getId() {
        return id;
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

        /**
         * Set value to the continent
         *
         * @param value The value of continent
         * @return return current builder object
         */
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
