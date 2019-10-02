package ca.concordia.encs.conquerdia.engine.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.concordia.encs.conquerdia.engine.ConquerdiaModel;
import ca.concordia.encs.conquerdia.engine.command.Command;
import ca.concordia.encs.conquerdia.engine.command.CommandFactory;
import ca.concordia.encs.conquerdia.engine.util.MessageUtil;
/**
 * This class implements the validation of the WorldMap
 * @author Sadegh Aalizadeh
 * @version $Revision$
 */
public class MapValidation implements CommandFactory {
	boolean isValidMap;
	private WorldMap worldMap;
	/**
	 * This is a setter method for the MapValidation attribute in the class.
	 * @param worldMap the worldMap that should be validated.
	 */
	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}
	private Set<Continent> continents = new HashSet<Continent>();
	/**
	 * This is the setter for the continents of the worldMap.
	 * @param continents the continents that should be a connected graph of the worldMap.
	 */
	public void setContinents(Set<Continent> continents) {
		this.continents = continents;
	}
	/**
	 * The constructor of the WorldMap class.
	 */
	public MapValidation() {
		this.isValidMap = false;
		//this.continents = this.worldMap.getContinents();
	}
	/**
	 * This method overrides the CommanFactory's getCommands to Implement validation of map.
	 * @param model Game model that contain all current objects.
	 * @param inputCommandParts parameters passes in command line by user.
	 * @return List of interpreted command results for MapValidation.
	 */
    @Override
    public List<Command> getCommands(ConquerdiaModel model, List<String> inputCommandParts) {
    		this.worldMap = model.getWorldMap();
    		this.continents = worldMap.getContinents();
            List<Command> commands = new ArrayList<Command>();
    		if (inputCommandParts.size() > 1) {
                return Arrays.asList(() -> MessageUtil.MAP_VALIDATION_COMMAND_ERR1);
            }
            if(checkAllMapValidationRules()){
                commands.addAll(Arrays.asList(() -> "Map ss Valid: \n1.Map is a connected Graph\n"
                		+ "2.Every continent is a connected subgraph in the Map\n"
                		+ "3.There is no country which belongs to more than one continent." ));
            }else {
            	commands.addAll(Arrays.asList(() -> "Map is invalid: \n"));
            	if(!isMapAConnectedGraph()) {
            		commands.addAll(Arrays.asList(() -> "worldMap is not a connected graph.\n"));
            	}
            	if(!isAllContinentsAConnectedSubgraphofWorldMap(continents)) {
            		commands.addAll(Arrays.asList(() -> "All the continents in the worldMap are not connected subgraphs of worldMap.\n"));
            	}
              	if(!isEeachCountryBelongingToOnlyOneContinent()) {
            		commands.addAll(Arrays.asList(() -> "All the countries in the map do not belong to only one continent.\n"));
            	}       	
            }
            return commands;
    }
	
	/**
	 * 
	 * @param worldMap the main graph of the game.
	 * @return true if All validation rules are 
	 */
	public boolean  checkAllMapValidationRules() {
		Set<Continent> continents = worldMap.getContinents();
		if(!isAllContinentsAConnectedSubgraphofWorldMap(continents) || !isMapAConnectedGraph() ||
				!isEeachCountryBelongingToOnlyOneContinent()) {
			isValidMap = false;
			return false;
		}
		isValidMap = true;
		return true;
	}
	/**
	 * 
	 * @param continents all the continents that their validity as a connected subgraph of worldMap should be checked.
	 * @return true if all the continents are a connected subgraph of worldMap; otherwise returns false;
	 */
	public boolean isAllContinentsAConnectedSubgraphofWorldMap(Set<Continent> continents){
		for(Continent continent : continents) {
			if(!isContinentAConnectedSubGraphOfWorldMap(continent)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * This Method verifies if the continent is a connected subGraph of the worldMap.
	 * @return true if the continent is a valid Subgraph of the WorldMap; otherwise returns false
	 */
	public boolean isContinentAConnectedSubGraphOfWorldMap(Continent continent) {
		
		if(continent != null && continents.contains(continent)) {
			if(continent.getCountries().size() > 0) {
				Map<Country ,Set<Country>> mapAdjacentCountriesWithCountryAsKey = new HashMap<Country, Set<Country>>();		
				for(Country country : continent.getCountries()) {
						mapAdjacentCountriesWithCountryAsKey.put(country,country.getAdjacentCountries());
				}
				for(Country country : mapAdjacentCountriesWithCountryAsKey.keySet()) {
					Set<Country> adjacentCountries = mapAdjacentCountriesWithCountryAsKey.get(country);
					/*CONTINENT VALIDATION RULE1(Connected Subgraph):
					 *Every country in the continent should have at least one adjacent country.*/
					if(adjacentCountries.size() == 0) {
							return false;
					}
				}
				return true;	
			}else {
				// An empty continent is considered to be a connected graph by default.
				//TODO:Check game rules! 
				return true;
			}
		}else {
			return false;
		}
	}
	/**
	 * This Method verifies if the worldMap is a connected graph.
	 * @param worldMap the main graph of the game.
	 * @return
	 */
	public boolean isMapAConnectedGraph() {
		Set<Continent> disconnectedContinents = new HashSet<Continent>();
		for(Continent continent : worldMap.getContinents()) {
			Map<Country ,Set<Country>> mapAdjacentCountriesWithCountryAsKey = new HashMap<Country, Set<Country>>();		
			Map<Country , Set<Country>> mapCountryWithAdjacentCountriesInAnotherContinent = new HashMap<Country, Set<Country>>();
			for(Country country : continent.getCountries()) {
				mapAdjacentCountriesWithCountryAsKey.put(country,country.getAdjacentCountries());
			}
			for(Country country : mapAdjacentCountriesWithCountryAsKey.keySet()) {
				Set<Country> adjacentCountries = mapAdjacentCountriesWithCountryAsKey.get(country);
				for(Country adjacentCountry : adjacentCountries) {
					if(adjacentCountry.getContinent() != continent) {
						Set<Country> adjacentCountriesinAnotherContinent = new HashSet<Country>();
						adjacentCountriesinAnotherContinent.add(adjacentCountry);
						mapCountryWithAdjacentCountriesInAnotherContinent.put(country, adjacentCountriesinAnotherContinent);
					}
				}
			}
			if(mapCountryWithAdjacentCountriesInAnotherContinent.keySet().size() == 0) {
				disconnectedContinents.add(continent);
			}
		}
		/* Map is a disconnected graph when there is at least one disconnected continent
		 * otherwise it is validated as a connected graph.
		 */
		if(disconnectedContinents.size() > 0) {
			return false;
		}
		return true;
	}
	/**
	 * This method Implements the validation rule that no country should belong to more than one continent on the map.
	 * @param worldMap the main graph of the game.
	 * @return true if there is no country with more than one continent; otherwise returns false.
	 */
	public boolean isEeachCountryBelongingToOnlyOneContinent() {
		Set<Continent> continents  = worldMap.getContinents();
		Map<Country , List<Continent>> mapCountriesAndContinents = new HashMap<Country, List<Continent>>();
		for(Continent continent : continents) {
			for(Country country : continent.getCountries()) {
				if(mapCountriesAndContinents.containsKey(country)) {
					mapCountriesAndContinents.get(country).add(country.getContinent());				
				}else {
					List<Continent> continentsList = new ArrayList<Continent>();
					continentsList.add(country.getContinent());
					mapCountriesAndContinents.put(country , continentsList);
				}
			}
		}
		//No Country should belong to more than one continent. 
		for(Country country : mapCountriesAndContinents.keySet()) {
			if(mapCountriesAndContinents.get(country).size() > 1) {
				return false;
			}
		}
		return true;
	}	
}


