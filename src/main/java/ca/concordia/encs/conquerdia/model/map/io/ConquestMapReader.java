package ca.concordia.encs.conquerdia.model.map.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.WorldMap;

/**
 * This class provides a set of methods to read a Conquest map file. It also
 * implements the {@link IDominationMapReader } interface to expose the map
 * reading method
 *
 * @author Mosabbir
 */
class ConquestMapReader extends ConquestMapIO{

	private final WorldMap worldMap;
	private BufferedReader reader;

	/**
	 * Constructor takes a worldMap instance to populate it from the file
	 *
	 * @param worldMap
	 */
	public ConquestMapReader(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	/**
	 * Parses the conquest .map file and load the continents and territories from it
	 *
	 * @param filename Name of the map file with or without the extension
	 * @return true if map file reading is successful, false if an exception
	 *         occurred
	 *         during reading
	 */
	public boolean read(String filename) {
		try {
			final String path = getMapFilePath(filename);
			final File file = new File(path);
			String line;

			reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					readContinents();
				} else if (isTerritoriesIdentifier(line)) {
					readTerritories();
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Reads all continents which are under the continent section in file add them
	 * to the map instance
	 */
	protected void readContinents() throws IOException, ValidationException {
		String line;
		String[] tokens;
		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(CONTINENTS_DELIMETER);
			String continentName = tokens[0];
			int continentValue = Integer.parseInt(tokens[1]);
			worldMap.addContinent(continentName, continentValue);
		}
	}

	/**
	 * Reads all countries which are under the Territories section in file add them
	 * to the map instance
	 */
	protected void readTerritories()
			throws IOException, ValidationException {
		String line;
		String[] tokens;
		String countryName;
		String contientName;
		HashMap<String, ArrayList<String>> countryNeighborsMap = new HashMap<>();
		ArrayList<String> neighbors;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TERRITORIES_DELIMETER);
			countryName = tokens[0];
			contientName = tokens[3];

			worldMap.addCountry(countryName, contientName);

			// Traverse and save neighbors
			neighbors = new ArrayList<String>();
			for (int i = 4; i < tokens.length; i++) {
				neighbors.add(tokens[i]);
			}
			countryNeighborsMap.put(countryName, neighbors);
		}

		// Add the neighbors of each country to the world map
		for (Entry<String, ArrayList<String>> entry : countryNeighborsMap.entrySet()) {
			countryName = entry.getKey();
			for (String neighbor : entry.getValue()) {
				worldMap.addNeighbour(countryName, neighbor);
			}
		}
	}
}
