package ca.concordia.encs.conquerdia.engine.map;

import java.util.HashMap;
import java.util.HashSet;
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
	 * @param worldMap
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
	
}


