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
	 * 
	 * @return true if the continent is a valid Subgraph of the WorldMap; otherwise returns false
	 */
	boolean isContinentAConnectedSubGraphOfWorldMap(Continent continent) {
		
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
}
