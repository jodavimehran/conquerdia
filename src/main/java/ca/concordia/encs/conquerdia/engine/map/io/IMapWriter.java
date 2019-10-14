package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public interface IMapWriter {
    /**
     * @param worldMap
     * @return
     */
    static IMapWriter createMapWriter(WorldMap worldMap) {
        return new MapWriter(worldMap);
    }

    boolean writeMap(String filename);
}
