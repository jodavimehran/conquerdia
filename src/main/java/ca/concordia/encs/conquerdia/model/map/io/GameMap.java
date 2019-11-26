package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Concrete implementation of game map operations
 */
public class GameMap implements IGameMap {
	private final WorldMap worldMap;

	public GameMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	@Override
	public boolean loadFrom(String filename) {
		IMapReader reader;
		if (isConquestMapFile(filename)) {
			reader = new DominationToConquestMapReaderAdapter(worldMap,
					new ConquestMapReader(worldMap));
		} else {
			reader = new MapReader(worldMap);
		}

		return reader.readMap(filename);
	}

	@Override
	public boolean saveTo(String filename) {
		IMapWriter writer;

		if (isConquestMapFile(filename)) {
			writer = new DominationToConquestMapWriterAdapter(worldMap,
					new ConquestMapWriter());
		} else {
			writer = new MapWriter(worldMap);
		}

		return writer.writeMap(filename);
	}

	private boolean isConquestMapFile(String filename) {
		// TODO check file type
		return false;
	}
}
