package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

public class ConquerdiaModel {
    private final WorldMap worldMap = new WorldMap();


    public WorldMap getWorldMap() {
        return worldMap;
    }
}
