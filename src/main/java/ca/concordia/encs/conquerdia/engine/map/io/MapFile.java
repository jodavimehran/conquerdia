package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class MapFile implements IMapFile {
    @Override
    public void loadMap(WorldMap map, String filename) {
        new MapReader(map).readMap(filename);
    }
}
