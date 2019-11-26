package ca.concordia.encs.conquerdia.model.map.io;

import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * Map Writer interface
 */
public interface IMapWriter {
//    /**
//     * @param worldMap map model to be written
//     * @return result
//     */
//    static IMapWriter createMapWriter(WorldMap worldMap) {
//        return new MapWriter(worldMap);
//    }
//
    /**
     * write map to a file
     *
     * @param filename filename
     * @return true if success
     */
    boolean writeMap(String filename);
}
