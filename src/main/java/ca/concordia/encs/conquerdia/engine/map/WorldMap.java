package ca.concordia.encs.conquerdia.engine.map;

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
    private String fileName;
    private boolean newMapFromScratch;
    private boolean readyForEdit;

    /**
     * @return return message result
     */
    public String editMap() {
        newMapFromScratch = !openMapFile();
        if (!(readyForEdit = validateMapFileName()))
            return String.format("File name \"%s\" is not a valid name.", fileName);
        return String.format("Map with file name \"%s\" is ready to edit", fileName);
    }

    /**
     * @return return true when a fileName is a valid name.
     */
    private boolean validateMapFileName() {
        //TODO: Implementation
        return true;
    }
    
    /**
     * @return
     */
    public String save() {
        if (!readyForEdit)
            return String.format(NO_MAP_TO_EDIT_ERROR, "save");
        return String.format("Map with file name \"%s\" has been saved successfully", fileName);
    }

    /**
     * @return return true if load a map from an existing “domination” map file successfully. return false if the file does not exist.
     */
    private boolean openMapFile() {
        //TODO: Implementation
        return false;
    }


    /**
     * @param continentName The name of the continent
     * @return return the continent if the name match a continent name or return null if there is no continent with the provided name
     */
    public Continent findContinentByName(String continentName) {
        if (continents.containsKey(continentName))
            return continents.get(continentName);
        return null;
    }

    public String addContinent(Continent continent) {
        String continentName = continent.getName();
        if (continents.containsKey(continentName))
            return String.format("Continent with name \"%s\" already exists in World Map!", continentName);
        continents.put(continentName, continent);
        return String.format("Continent with name \"%s\" is successfully added to World Map", continentName);
    }

    public String removeContinent(Continent continent) {
        String continentName = continent.getName();
        if (!continents.containsKey(continentName))
            return String.format("Continent with name \"%s\" does not exist in World Map!", continentName);
        continents.remove(continentName);
        return String.format("Continent with name \"%s\" is successfully removed from World Map", continentName);
    }

    public Set<String> getCountriesName() {
        return continents.entrySet().stream().map(entry -> entry.getValue()).flatMap(continent -> continent.getCountriesName().stream()).collect(Collectors.toSet());
    }
    /**
     * This method returns the name of continents and countries that are already populated in the map.
     * @return The Result of showmap for world map.
     */
     @Override
    public String toString() {
    	 Set<Continent> continents = this.getContinents();
    	 StringBuilder showMapResult = new StringBuilder();
    	 for(Continent continent: continents) {
    		 showMapResult.append(continent.toString()).append("\n");
    	 } 
    	 return showMapResult.toString();
    }
     
    public Set<Continent> getContinents() {
        return continents.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    /**
     * TODO: JAVADOC
     *
     * @return
     */
    public String validateMap() {
        return new MapValidation(this).validate();
    }
}
