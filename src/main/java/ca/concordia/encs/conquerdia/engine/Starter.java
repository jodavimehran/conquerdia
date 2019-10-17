package ca.concordia.encs.conquerdia.engine;

import ca.concordia.encs.conquerdia.engine.map.io.MapIO;
import ca.concordia.encs.conquerdia.engine.util.FileHelper;

import java.util.Scanner;

/**
 * Project Starter
 */
public class Starter {
    private final ConquerdiaController conquerdia = new ConquerdiaController();

    /**
     * Project main method
     *
     * @param args input arguments
     */
    public static void main(String[] args) {
        FileHelper.CreateDirectoryIfNotExists(MapIO.MAPS_FOLDER_PATH);
        new Starter().conquerdia.start(new Scanner(System.in), System.out);
    }
}
