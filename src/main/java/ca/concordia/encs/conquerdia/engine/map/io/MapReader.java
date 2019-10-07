package ca.concordia.encs.conquerdia.engine.map.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.map.Continent;
import ca.concordia.encs.conquerdia.engine.map.Country;

class MapReader extends MapIO implements IMapReader {

	/**
	 * Parses the .map file and load the continents, countries and borders from it
	 * 
	 * @param filename Only the name of the map file with or without the extension
	 *                 eg. uk, risk.map etc.
	 * @return An ArrayList containing the maps and countries & their neighbors
	 *         represented in the map. NULL if there is a parsing error for
	 *         continents
	 * @throws IOException
	 */
	public ArrayList<Continent> readMap(String filename) throws IOException {
		final String path = MapIO.getMapFilePath(filename);

		final File file = new File(path);
		final BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					continentList = readContinents(reader);
				} else if (isCountriesIdentifier(line)) {
					countryList = readCountries(reader, continentList);
				} else if (isBordersIdentifier(line)) {
					readBorders(reader, countryList);
				}
			}
		} finally {
			reader.close();
		}

		return continentList;
	}

	protected ArrayList<Continent> readContinents(BufferedReader reader) throws IOException {

		ArrayList<Continent> continentList = new ArrayList<>();
		Continent continent;
		String line;
		String tokens[];

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			continent = new Continent.Builder(tokens[0]).setValue(Integer.parseInt(tokens[1])).build();
			continentList.add(continent);
		}

		return continentList;
	}

	protected ArrayList<Country> readCountries(BufferedReader reader, ArrayList<Continent> continents)
			throws IOException {

		ArrayList<Country> countries = new ArrayList<>();
		Country country;
		String line;
		String tokens[];
		int continentNumber;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			continentNumber = Integer.parseInt(tokens[2]);
			country = new Country.Builder(tokens[1], continents.get(continentNumber - 1)).build();
			countries.add(country);
		}

		return countries;
	}

	protected void readBorders(BufferedReader reader, ArrayList<Country> countries) throws IOException {
		String line;
		String tokens[];
		Country country;
		int countryIndex;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			countryIndex = Integer.parseInt(tokens[0]) - 1;
			country = countries.get(countryIndex);
			addNeighbours(country, tokens);
		}
	}

	private void addNeighbours(Country country, String[] countryBorders) {
		int neighborIndex;
		for (int i = 1; i < countryBorders.length; i++) {
			neighborIndex = Integer.parseInt(countryBorders[i]) - 1;
			country.addNeighbour(countryList.get(neighborIndex));
		}
	}

}
