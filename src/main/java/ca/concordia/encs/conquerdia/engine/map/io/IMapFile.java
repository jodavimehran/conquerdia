package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public interface IMapFile {
    void loadMap(WorldMap map, String filename);
}
