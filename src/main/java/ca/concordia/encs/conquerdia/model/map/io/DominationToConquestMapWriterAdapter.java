package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Adapter for conquest map which has a different write map signature than the
 * MapWriter
 */
class DominationToConquestMapWriterAdapter extends MapWriter {

	private final ConquestMapWriter conquestMapWriter;

	DominationToConquestMapWriterAdapter(WorldMap worldMap, ConquestMapWriter conquestMapWriter) {
		super(worldMap);
		this.conquestMapWriter = conquestMapWriter;
	}

	@Override
	public boolean writeMap(String filename) {
		return conquestMapWriter.write(filename, worldMap);
	}
}
