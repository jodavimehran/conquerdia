package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

/**
 * Map Writer interface
 */
public interface IMapWriter {
    /**
     * @param worldMap map model to be written
     * @return
     */
    static IMapWriter createMapWriter(WorldMap worldMap) {
        return new MapWriter(worldMap);
    }

    /**
     * write map to a file
     *
     * @param filename filename
     * @return true if success
     */
    boolean writeMap(String filename);
}
