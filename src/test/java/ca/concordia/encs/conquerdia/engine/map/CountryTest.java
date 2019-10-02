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

    private Country country;
    private Continent testContinent;
    private Country country1;
    private Country country2;
    private Country country3;

    /**
     * All common activities are placed here
     */
    @Before
    public void setUp() {
        testContinent = new Continent.Builder("testContinent").build();
        country1 = new Country.Builder("country one", testContinent).build();
        country2 = new Country.Builder("country two", testContinent).build();
        country3 = new Country.Builder("country tree", testContinent).build();


        country = new Country.Builder("test", testContinent)
                .adjacentTo(country1)
                .adjacentTo(new HashSet<>(Arrays.asList(country2, country3)))
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
     * This test case is designed to check {@link Country#getContinent()}  getContinent} method of {@link Country country}.
     * Continent object of a country that is build by {@link Country.Builder} class must be same object that is passed to constructor.
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
        assertTrue(country.getAdjacentCountries().contains(country1));
    }

    /**
     * This test case is designed to check {@link Country.Builder#adjacentTo(Country) adjacentTo} method of {@link Country.Builder builder}.
     */
    @Test
    public void addAdjacentCountriesTestCase() {
        assertTrue(country.getAdjacentCountries().contains(country2));
        assertTrue(country.getAdjacentCountries().contains(country3));
    }
}
