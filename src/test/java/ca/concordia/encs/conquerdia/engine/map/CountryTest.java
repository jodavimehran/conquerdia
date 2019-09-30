package ca.concordia.encs.conquerdia.engine.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * This class is design to test all different functionality of {@link Country} and {@link Country.Builder}.
 */
public class CountryTest {

    private final Country adjacentCountry = new Country.Builder(1, "country one").build();
    private final Country[] adjacentCountries = {
            new Country.Builder(2, "country two").build(),
            new Country.Builder(3, "country tree").build(),
    };
    private Country country;
    private Continent testContinent;

    /**
     * All common activities are placed here
     */
    @Before
    public void setUp() {
        testContinent = new Continent.Builder("testContinent").build();
        country = new Country.Builder(1, "test")
                .placedIn(testContinent)
                .adjacentTo(adjacentCountry)
                .adjacentTo(new HashSet<>(Arrays.asList(adjacentCountries)))
                .build();
    }


    /**
     * This test case is designed to check {@link Country.Builder#build() build} method of {@link Country.Builder builder}
     * by using the fact that the {@link Country} object that is built by calling this method must be not null.
     */
    @Test
    public void buildTestCase() {
        assertNotNull(country);
    }

    /**
     * This test case is designed to check {@link Country.Builder#placedIn(Continent)}  placedIn} method of {@link Country.Builder builder} and {@link Country#getContinent()}  getContinent} method of {@link Country country}.
     * Continent object of a country that is build by {@link Country.Builder} class must be same object that is passed to {@link Country.Builder#placedIn(Continent)}  placedIn} method.
     */
    @Test
    public void addContinentTestCase() {
        assertSame(country.getContinent(), testContinent);
    }

    /**
     * This test case is designed to check {@link Country.Builder#adjacentTo(Country) adjacentTo} method of {@link Country.Builder builder}.
     */
    @Test
    public void addAdjacentCountryTestCase() {
        assertTrue(country.getAdjacentCountries().contains(adjacentCountry));
    }

    /**
     * This test case is designed to check {@link Country.Builder#adjacentTo(Country) adjacentTo} method of {@link Country.Builder builder}.
     */
    @Test
    public void addAdjacentCountriesTestCase() {
        assertTrue(country.getAdjacentCountries().contains(adjacentCountries[0]));
        assertTrue(country.getAdjacentCountries().contains(adjacentCountries[1]));
    }
}
