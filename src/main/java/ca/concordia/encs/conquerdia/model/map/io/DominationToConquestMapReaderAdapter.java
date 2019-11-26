package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

public class DominationToConquestMapReaderAdapter extends MapReader {
	private final ConquestMapReader conquestMapReader;

	public DominationToConquestMapReaderAdapter(WorldMap worldMap,
			ConquestMapReader conquestMapReader) {
		super(worldMap);
		this.conquestMapReader = conquestMapReader;
	}

	@Override
	public boolean readMap(String filename) {
		return conquestMapReader.read(filename);
	}
}
