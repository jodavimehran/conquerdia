package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Map Reader interface
 */
public interface IMapReader {
    /**
     * @param worldMap the map to be loaded
     * @return result
     */
    static IMapReader createMapReader(WorldMap worldMap) {
        return new MapReader(worldMap);
    }

    /**
     * @param filename filename
     * @return result
     */
    boolean readMap(String filename);
}
