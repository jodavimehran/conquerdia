package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Continent;

public interface IMapReader {

	ArrayList<Continent> readMap(String filename) throws IOException;
}
