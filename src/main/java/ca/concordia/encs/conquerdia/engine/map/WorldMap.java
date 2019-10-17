package ca.concordia.encs.conquerdia.engine.map;

import ca.concordia.encs.conquerdia.engine.map.io.IMapReader;
import ca.concordia.encs.conquerdia.engine.map.io.IMapWriter;
import ca.concordia.encs.conquerdia.engine.util.MapFormattor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the world map of the game.
 */
public class WorldMap {
    private final static String NO_MAP_TO_EDIT_ERROR = "There is no map to %s. Use \"editmap filename\" command to load or create a map.";
    private final Map<String, Continent> continents = new HashMap<>();
    private final Map<String, Country> countries = new HashMap<>();
    private final MapValidation mapValidation = new MapValidation(this);
    private String fileName;
    private boolean newMapFromScratch;
    private boolean readyForEdit;
    private boolean mapLoaded;

    /**
     * Loading a map from an existing “domination” map file to edit or create a new map from scratch if the file does not
     * exist.
     *
     * @param fileName name of the map file file to edit
     * @return the result message
     */
    public String editMap(String fileName) {
        this.fileName = fileName;
        readyForEdit = true;
        newMapFromScratch = !openMapFile();
        return String.format("Map with file name \"%s\" is ready to edit", fileName);
    }

    /**
     * @param fileName name of the map file file to load
     * @return the result message
     */
    public String loadMap(String fileName) {
        this.fileName = fileName;
        this.readyForEdit = true;
        boolean mapIsLoaded = openMapFile();
        this.readyForEdit = false;
        if (!mapIsLoaded)
            return String.format("Map with file name \"%s\" is not found!", fileName);
        if (!mapValidation.checkAllMapValidationRules())
            return mapValidation.validate();
        mapLoaded = true;
        return String.format("Map with file name \"%s\" is loaded successfully.", fileName);
    }

    /**
     * @return return true when a fileName is a valid name.
     */
    private boolean validateMapFileName() {
        return true;
    }

    /**
     * @return return true if a map file was successfully saved.
     * return false if a map file was successfully saved
     */
    public String saveMap(String fileName) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "save");
        IMapWriter.createMapWriter(this).writeMap(fileName);
        return String.format("Map with file name \"%s\" has been saved successfully", fileName);
    }

    /**
     * @return return true if load a map from an existing “domination” map file successfully. return false if the file does not exist.
     */
    private boolean openMapFile() {
        return IMapReader.createMapReader(this).readMap(fileName);
    }

    /**
     * This method add a continent to map
     *
     * @param continentName  name of the continent
     * @param continentValue value of the continent
     * @return return the result
     */
    public String addContinent(String continentName, Integer continentValue) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "add a continent to it");
        if (StringUtils.isBlank(continentName))
            return "Continent name is not valid!";
        if (continentValue == null || continentValue <= 0)
            return "Continent value is not valid!";
        if (continents.containsKey(continentName)) {
            return String.format("Continent with name \"%s\" is already exist.", continentName);
        }
        Continent continent = new Continent.Builder(continentName)
                .setValue(Integer.valueOf(continentValue))
                .build();
        continents.put(continentName, continent);
        return String.format("Continent with name \"%s\" is successfully added to map", continentName);
    }

    /**
     * @param continentName
     * @return
     */
    public String removeContinent(String continentName) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "remove a continent from it");
        if (StringUtils.isBlank(continentName))
            return "Continent name is not valid!";
        if (!continents.containsKey(continentName)) {
            return String.format("Continent with name \"%s\" is not found.", continentName);
        }
        Continent toRemove = continents.get(continentName);
        for (String countryName : toRemove.getCountriesName()) {
            removeCountry(countryName);
        }
        continents.remove(continentName);
        return String.format("Continent with name \"%s\" is successfully removed from World Map", continentName);
    }


    /**
     * @param countryName
     * @param continentName
     * @return
     */
    public String addCountry(String countryName, String continentName) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "add a country to it");
        if (StringUtils.isBlank(countryName))
            return "Country name is not valid!";
        if (StringUtils.isBlank(continentName))
            return "Continent name is not valid!";
        if (countries.containsKey(countryName)) {
            return String.format("Country with name \"%s\" is already exist.", countryName);
        }
        if (!continents.containsKey(continentName)) {
            return String.format("Continent with name \"%s\" does not exist in World Map!", continentName);
        }
        Country country = new Country.Builder(countryName, continents.get(continentName)).build();
        countries.put(countryName, country);
        continents.get(continentName).addCountry(country);
        return String.format("Country with name \"%s\" is successfully added to \"%s\"", countryName, continentName);
    }

    /**
     * This Method add this country to the map
     *
     * @return return message result
     */
    public String removeCountry(String countryName) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "remove a country from it");
        if (!countries.containsKey(countryName)) {
            return String.format("Country with name \"%s\" is not found.", countryName);
        }
        Country removed = countries.remove(countryName);
        removed.getContinent().removeCountry(countryName);
        for (Country adjacentCountry : removed.getAdjacentCountries()) {
            removeNeighbour(removed, adjacentCountry);
        }
        return String.format("Country with name \"%s\" is successfully removed from World Map", countryName);
    }

    /**
     * @param firstCountryName
     * @param secondCountryName
     * @return
     */
    public String addNeighbour(String firstCountryName, String secondCountryName) {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "modify");
        if (!countries.containsKey(firstCountryName))
            return String.format("Country with name \"%s\" is not found.", firstCountryName);
        if (!countries.containsKey(secondCountryName))
            return String.format("Country with name \"%s\" is not found.", secondCountryName);
        Country firstCountry = countries.get(firstCountryName);
        Country secondCountry = countries.get(secondCountryName);

        if (firstCountry.isAdjacentTo(secondCountryName) && secondCountry.isAdjacentTo(firstCountryName))
            return String.format("\"%s\" and \"%s\" are already adjacent countries.", firstCountryName, secondCountryName);

        firstCountry.addNeighbour(secondCountry);
        secondCountry.addNeighbour(firstCountry);

        return String.format("\"%s\" and \"%s\" are adjacent countries now.", firstCountryName, secondCountryName);
    }

    /**
     * @param firstCountryName
     * @param secondCountryName
     * @return
     */
    public String removeNeighbour(String firstCountryName, String secondCountryName) {
        if (!countries.containsKey(firstCountryName))
            return String.format("Country with name \"%s\" is not found.", firstCountryName);
        if (!countries.containsKey(secondCountryName))
            return String.format("Country with name \"%s\" is not found.", secondCountryName);
        Country firstCountry = countries.get(firstCountryName);
        Country secondCountry = countries.get(secondCountryName);
        if (!firstCountry.isAdjacentTo(secondCountryName) && !secondCountry.isAdjacentTo(firstCountryName))
            return String.format("\"%s\" and \"%s\" are not adjacent countries.", firstCountryName, secondCountryName);
        removeNeighbour(firstCountry, secondCountry);
        if (!countries.containsKey(firstCountryName))
            return String.format("Country with name \"%s\" is not found.", firstCountryName);

        return String.format("\"%s\" and \"%s\" are not adjacent countries now.", firstCountryName, secondCountryName);
    }

    /**
     * @param firstCountry
     * @param secondCountry
     */
    private void removeNeighbour(Country firstCountry, Country secondCountry) {
        firstCountry.removeNeighbour(secondCountry.getName());
        secondCountry.removeNeighbour(firstCountry.getName());
    }


    /**
     * @return return all continents in map
     */
    public Set<Continent> getContinents() {
        return continents.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    public Set<String> getCountryNames() {
        return countries.keySet();
    }

    /**
     * @return All counties in map
     */
    public Set<Country> getCountries() {
        return countries.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    /**
     * Find a continent by name and return it
     *
     * @param continentName name of the continent
     * @return the continent if the continent exist, return null when a continent does not exist.
     */
    public Continent getContinent(String continentName) {
        if (continents.containsKey(continentName))
            return continents.get(continentName);
        return null;
    }

    /**
     * Find a Country by name and return it
     *
     * @param countryName name of the country
     * @return the country if the country exist, return null when a country does not exist.
     */
    public Country getCountry(String countryName) {
        if (countries.containsKey(countryName))
            return countries.get(countryName);
        return null;
    }

    /**
     * @param countryName
     * @return return true if the map contains country
     */
    public boolean isMapContainsCountry(String countryName) {
        return countries.containsKey(countryName);
    }

    /**
     * This method validates the map and checks different constraints of validity.
     *
     * @return A string that shows the validation result of the map.
     */
    public String validateMap() {
        return new MapValidation(this).validate();
    }

    /**
     * @return return true if the map was loaded
     */
    public boolean isMapLoaded() {
        return mapLoaded;
    }

    /**
     * @return the map
     */
    public String showMap() {
        return new MapFormattor(countries).format(mapLoaded ? MapFormattor.FormatType.Detail : MapFormattor.FormatType.Default);
    }

}
