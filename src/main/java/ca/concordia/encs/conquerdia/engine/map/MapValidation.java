package ca.concordia.encs.conquerdia.engine.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * This class implements the validation of the WorldMap
 * @author Sadegh Aalizadeh
 * @version $Revision$
 */
public class MapValidation {
	private WorldMap worldMap;
	private Set<Continent> continents = new HashSet<Continent>();
	boolean isValidMap;
	public MapValidation(WorldMap worldMap) {
		this.worldMap = worldMap;
		this.continents = worldMap.getContinents();
	}	
	/**
	 * This Method verifies if the continent is a connected subGraph of the worldMap.
	 * @return true if the continent is a valid Subgraph of the WorldMap; otherwise returns false
	 */
	public boolean isContinentAConnectedSubGraphOfWorldMap(Continent continent) {
		
		if(continent != null & continents.contains(continent)) {
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
	 * @param worldMap the main grapgh of the game.
	 * @return
	 */
	public boolean isMapAConnectedGraph(WorldMap worldMap) {
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
	public boolean isEeachCountryBelongingToOnlyOneContinent(WorldMap worldMap) {
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


