package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Continent;

public class MapFile implements IMapFile {

	IMapReader mapReader;
	IMapWriter mapWriter;

	public MapFile() {
		mapReader = new MapReader();
		mapWriter = new MapWriter();
	}

	public MapFile(IMapReader mapReader, IMapWriter mapWriter) {
		this.mapReader = mapReader;
		this.mapWriter = mapWriter;
	};

	@Override
	public ArrayList<Continent> loadMap(String filename) {
		try {
			return mapReader.readMap(filename);
		} catch (IOException e) {
			System.out.println("Error: Exception occured during map loading");
			e.printStackTrace();
		}
		return null;
	}
}
