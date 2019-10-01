package ca.concordia.encs.conquerdia.engine.map;

import java.util.HashSet;
import java.util.Set;

public class WorldMap {
    private Set<Continent> continents = new HashSet<>();

    public void setContinents(Set<Continent> continents) {
		this.continents = continents;
	}

	public Set<Continent> getContinents() {
        return continents;
    }
}
