package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.WorldMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class MapReader extends MapIO implements IMapReader {
	private final WorldMap worldMap;

	public MapReader(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	/**
	 * Parses the .map file and load the continents, countries and borders from it
	 *
	 * @param filename Only the name of the map file with or without the extension
	 *                 eg. uk, risk.map etc.
	 * @return An ArrayList containing the maps and countries & their neighbors
	 *         represented in the map. NULL if there is a parsing error for
	 *         continents
	 */
	public boolean readMap(String filename) {
		final String path = MapIO.getMapFilePath(filename);
		final File file = new File(path);
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			countryList = new ArrayList<String>();
			continentList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					readContinents(reader);
				} else if (isCountriesIdentifier(line)) {
					readCountries(reader);
				} else if (isBordersIdentifier(line)) {
					readBorders(reader);
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	protected void readContinents(BufferedReader reader) throws IOException {
		String line;
		String[] tokens;
		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			String continentName = tokens[0];
			int continentValue = Integer.parseInt(tokens[1]);
			worldMap.addContinent(continentName, continentValue);
			continentList.add(continentName);
		}
	}

	protected void readCountries(BufferedReader reader)
			throws IOException {
		String line;
		String[] tokens;
		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			String countryName = tokens[1];
			int continentIndex = Integer.parseInt(tokens[2]) - 1;
			worldMap.addCountry(countryName, continentList.get(continentIndex));
			countryList.add(countryName);
		}
	}

	protected void readBorders(BufferedReader reader) throws IOException {
		String line;
		String[] tokens;
		int countryIndex;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			countryIndex = Integer.parseInt(tokens[0]) - 1;
			addNeighbours(countryList.get(countryIndex), tokens);
		}
	}

	private void addNeighbours(String countryName, String[] countryBorders) {
		int neighborIndex;
		for (int i = 1; i < countryBorders.length; i++) {
			neighborIndex = Integer.parseInt(countryBorders[i]) - 1;
			worldMap.addNeighbour(countryName, countryList.get(neighborIndex));
		}
	}

}
