package ca.concordia.encs.conquerdia.engine.map.io;

import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Continent;

public interface IMapFile {

	ArrayList<Continent> loadMap(String filename);
}
