package ca.concordia.encs.conquerdia.engine.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.encs.conquerdia.engine.util.FileHelper;

public class MapFile {

	public static final String COMMENT_SYMBOL = ";";
	public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";
	public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";
	public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";
	public static final String TOKENS_DELIMETER = " ";
	public static final String MAPS_FOLDER = "maps";

	private ArrayList<Continent> continentList;
	private ArrayList<Country> countryList;

	public ArrayList<Continent> loadMap(String fileName) throws FileNotFoundException, IOException {
		final String path = FileHelper.getResourcePath(FileHelper.combinePath(MAPS_FOLDER, fileName));
		final File file = new File(path);
		final BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					continentList = loadContinents(reader);
				} else if (isCountriesIdentifier(line)) {
					countryList = loadCountries(reader, continentList);
				} else if (isBordersIdentifier(line)) {
					loadBorders(reader, countryList);
				}
			}
		} finally {
			reader.close();
		}

		return continentList;
	}

	protected ArrayList<Continent> loadContinents(BufferedReader reader) throws IOException {

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

	protected ArrayList<Country> loadCountries(BufferedReader reader, ArrayList<Continent> continents)
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

	protected void loadBorders(BufferedReader reader, ArrayList<Country> countries) throws IOException {
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

	protected boolean isComment(String line) {
		return line.startsWith(COMMENT_SYMBOL);
	}

	protected boolean isContientsIdentifier(String line) {
		return line.equalsIgnoreCase(CONTINENTS_SECTION_IDENTIFIER);
	}

	protected boolean isCountriesIdentifier(String line) {
		return line.equalsIgnoreCase(COUNTRIES_SECTION_IDENTIFIER);
	}

	protected boolean isBordersIdentifier(String line) {
		return line.equalsIgnoreCase(BORDERS_SECTION_IDENTIFIER);
	}
}
