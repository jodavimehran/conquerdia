package ca.concordia.encs.conquerdia.engine.map;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the validation of the WorldMap
 *
 * @author Sadegh Aalizadeh
 * @version $Revision$
 */
public class MapValidation {
    private boolean connectedGraph;
    private boolean connectedSubGraph;

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
        boolean result = checkAllMapValidationRules();
        StringBuilder validationResult = new StringBuilder();
        if (result) {
            validationResult.append("Map is Valid:").append(System.getProperty("line.separator"));
            validationResult.append("1.Map is a connected Graph").append(System.getProperty("line.separator"));
            validationResult.append("2.Every continent is a connected subgraph in the Map").append(System.getProperty("line.separator"));
        } else {
            validationResult.append("Map is not Valid:").append(System.getProperty("line.separator"));
            if (!connectedGraph) {
                validationResult.append("worldMap is not a connected graph.").append(System.getProperty("line.separator"));
            }
            if (!connectedSubGraph) {
                validationResult.append("All the continents in the worldMap are not connected subgraphs of worldMap.").append(System.getProperty("line.separator"));
            }
        }
        return validationResult.toString();
    }

    /**
     * @return true if All validation rules are
     */
    public boolean checkAllMapValidationRules() {
        {
            Set<String> countryNames = worldMap.getCountryNames();
            connectedGraph = isMapConnected(worldMap.getCountry(countryNames.iterator().next()), countryNames);
        }
        {
            connectedSubGraph = true;
            for (Continent continent : worldMap.getContinents()) {
                Set<String> countryNames = continent.getCountriesName();
                if (countryNames.isEmpty())
                    continue;
                connectedSubGraph &= isMapConnected(continent.getCountries().iterator().next(), countryNames);
            }

        }
        return connectedGraph && connectedSubGraph;
    }

    private void traversCountry(Country country, HashSet<String> countries) {
        countries.add(country.getName());
        for (Country adjacent : country.getAdjacentCountries()) {
            if (!countries.contains(adjacent.getName())) {
                traversCountry(adjacent, countries);
            }
        }
    }

    /**
     * This Method verifies if the worldMap is a connected graph.
     *
     * @return
     */
    public boolean isMapConnected(Country firstCountry, Set<String> countryNames) {
        HashSet<String> countries = new HashSet<>();
        if (countryNames.isEmpty())
            return true;
        traversCountry(firstCountry, countries);
        boolean result = countries.containsAll(countryNames);
        return result;
    }


}


