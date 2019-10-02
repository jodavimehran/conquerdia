package ca.concordia.encs.conquerdia.engine.map;

import java.util.*;

/**
 * This class implements the validation of the WorldMap
 *
 * @author Sadegh Aalizadeh
 * @version $Revision$
 */
public class MapValidation {
    boolean isValidMap;
    private WorldMap worldMap;

    /**
     * The constructor of the WorldMap class.
     */
    public MapValidation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Validate the map
     *
     * @return validation result
     */
    public String validate() {
        StringBuilder validationResult = new StringBuilder();
        if (checkAllMapValidationRules()) {
            validationResult.append("Map is Valid:").append(System.getProperty("line.separator"));
            validationResult.append("1.Map is a connected Graph").append(System.getProperty("line.separator"));
            validationResult.append("2.Every continent is a connected subgraph in the Map").append(System.getProperty("line.separator"));
            validationResult.append("3.There is no country which belongs to more than one continent.").append(System.getProperty("line.separator"));
        } else {
            validationResult.append("Map is not Valid:").append(System.getProperty("line.separator"));
            if (!isMapAConnectedGraph()) {
                validationResult.append("worldMap is not a connected graph.").append(System.getProperty("line.separator"));
            }
            if (!isAllContinentsAConnectedSubgraphofWorldMap(worldMap.getContinents())) {
                validationResult.append("All the continents in the worldMap are not connected subgraphs of worldMap.").append(System.getProperty("line.separator"));
            }
            if (!isEeachCountryBelongingToOnlyOneContinent()) {
                validationResult.append("All the countries in the map do not belong to only one continent.").append(System.getProperty("line.separator"));
            }
        }
        return validationResult.toString();
    }

    /**
     * @return true if All validation rules are
     */
    public boolean checkAllMapValidationRules() {
        Set<Continent> continents = worldMap.getContinents();
        if (!isAllContinentsAConnectedSubgraphofWorldMap(continents) || !isMapAConnectedGraph() ||
                !isEeachCountryBelongingToOnlyOneContinent()) {
            isValidMap = false;
            return false;
        }
        isValidMap = true;
        return true;
    }

    /**
     * @param continents all the continents that their validity as a connected subgraph of worldMap should be checked.
     * @return true if all the continents are a connected subgraph of worldMap; otherwise returns false;
     */
    public boolean isAllContinentsAConnectedSubgraphofWorldMap(Set<Continent> continents) {
        for (Continent continent : continents) {
            if (!isContinentAConnectedSubGraphOfWorldMap(continent)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This Method verifies if the continent is a connected subGraph of the worldMap.
     *
     * @return true if the continent is a valid Subgraph of the WorldMap; otherwise returns false
     */
    public boolean isContinentAConnectedSubGraphOfWorldMap(Continent continent) {

        if (continent != null && worldMap.getContinents().contains(continent)) {
            if (continent.getCountries().size() > 0) {
                Map<Country, Set<Country>> mapAdjacentCountriesWithCountryAsKey = new HashMap<Country, Set<Country>>();
                for (Country country : continent.getCountries()) {
                    mapAdjacentCountriesWithCountryAsKey.put(country, country.getAdjacentCountries());
                }
                for (Country country : mapAdjacentCountriesWithCountryAsKey.keySet()) {
                    Set<Country> adjacentCountries = mapAdjacentCountriesWithCountryAsKey.get(country);
                    /*CONTINENT VALIDATION RULE1(Connected Subgraph):
                     *Every country in the continent should have at least one adjacent country.*/
                    if (adjacentCountries.size() == 0) {
                        return false;
                    }
                }
                return true;
            } else {
                // An empty continent is considered to be a connected graph by default.
                //TODO:Check game rules!
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * This Method verifies if the worldMap is a connected graph.
     *
     * @return
     */
    public boolean isMapAConnectedGraph() {
        Set<Continent> disconnectedContinents = new HashSet<Continent>();
        for (Continent continent : worldMap.getContinents()) {
            Map<Country, Set<Country>> mapAdjacentCountriesWithCountryAsKey = new HashMap<Country, Set<Country>>();
            Map<Country, Set<Country>> mapCountryWithAdjacentCountriesInAnotherContinent = new HashMap<Country, Set<Country>>();
            for (Country country : continent.getCountries()) {
                mapAdjacentCountriesWithCountryAsKey.put(country, country.getAdjacentCountries());
            }
            for (Country country : mapAdjacentCountriesWithCountryAsKey.keySet()) {
                Set<Country> adjacentCountries = mapAdjacentCountriesWithCountryAsKey.get(country);
                for (Country adjacentCountry : adjacentCountries) {
                    if (adjacentCountry.getContinent() != continent) {
                        Set<Country> adjacentCountriesinAnotherContinent = new HashSet<Country>();
                        adjacentCountriesinAnotherContinent.add(adjacentCountry);
                        mapCountryWithAdjacentCountriesInAnotherContinent.put(country, adjacentCountriesinAnotherContinent);
                    }
                }
            }
            if (mapCountryWithAdjacentCountriesInAnotherContinent.keySet().size() == 0) {
                disconnectedContinents.add(continent);
            }
        }
        /* Map is a disconnected graph when there is at least one disconnected continent
         * otherwise it is validated as a connected graph.
         */
        return disconnectedContinents.size() <= 0;
    }

    /**
     * This method Implements the validation rule that no country should belong to more than one continent on the map.
     *
     * @return true if there is no country with more than one continent; otherwise returns false.
     */
    public boolean isEeachCountryBelongingToOnlyOneContinent() {
        Set<Continent> continents = worldMap.getContinents();
        Map<Country, List<Continent>> mapCountriesAndContinents = new HashMap<Country, List<Continent>>();
        for (Continent continent : continents) {
            for (Country country : continent.getCountries()) {
                if (mapCountriesAndContinents.containsKey(country)) {
                    mapCountriesAndContinents.get(country).add(country.getContinent());
                } else {
                    List<Continent> continentsList = new ArrayList<Continent>();
                    continentsList.add(country.getContinent());
                    mapCountriesAndContinents.put(country, continentsList);
                }
            }
        }
        //No Country should belong to more than one continent.
        for (Country country : mapCountriesAndContinents.keySet()) {
            if (mapCountriesAndContinents.get(country).size() > 1) {
                return false;
            }
        }
        return true;
    }
}


