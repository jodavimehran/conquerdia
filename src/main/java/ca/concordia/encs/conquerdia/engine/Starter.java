package ca.concordia.encs.conquerdia.engine;

import java.util.Scanner;

import ca.concordia.encs.conquerdia.engine.map.io.MapIO;
import ca.concordia.encs.conquerdia.engine.util.FileHelper;

public class Starter {
	private final ConquerdiaController conquerdia = new ConquerdiaController();

	public static void main(String[] args) {
		FileHelper.CreateDirectoryIfNotExists(MapIO.MAPS_FOLDER_PATH);
		new Starter().conquerdia.start(new Scanner(System.in), System.out);
	}
}
