package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Concrete implementation of game map operations
 */
public class GameMap implements IGameMap {
	private final IMapReader reader;
	private final IMapWriter writer;
	
	public GameMap(WorldMap worldMap) {
		reader = new MapReader(worldMap);
		writer = new MapWriter(worldMap);
	}

	@Override
	public boolean loadFrom(String filename) {
		return reader.readMap(filename);
	}

	@Override
	public boolean saveTo(String filename) {
		return writer.writeMap(filename);
	}
}
