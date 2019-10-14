package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

/**
 *
 */
public interface IMapReader {
    /**
     * @param worldMap
     * @return
     */
    static IMapReader createMapReader(WorldMap worldMap) {
        return new MapReader(worldMap);
    }

    /**
     * @param filename
     * @return
     */
    boolean readMap(String filename);
}
