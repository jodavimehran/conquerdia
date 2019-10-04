package ca.concordia.encs.conquerdia.engine.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapFile {

	public static final String COMMENT_SYMBOL = ";";
	public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";
	public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";
	public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";
	public static final String TOKENS_DELIMETER = " ";

	private String filePath;

	public MapFile(String filePath) {
		this.filePath = filePath;
	}

	public void loadMap() throws FileNotFoundException, IOException {

		final BufferedReader reader = new BufferedReader(new FileReader(filePath));
		WorldMap worldMap = new WorldMap();
		Continent[] continents = null;
		Country[] countries = null;
		try {
			String line;

			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					continents = loadContinents(reader);
				} else if (isCountriesIdentifier(line)) {
					countries = loadCountries(reader, continents);
				} else if (isBordersIdentifier(line)) {
					loadBorders(reader, countries);
				}
			}
		} finally {
			reader.close();
		}
	}

	/**
	 * [continents] North-America 5 yellow
	 * South-America 2 green
	 * Europe 5 blue
	 * Africa 3 orange Asia 7
	 * pink Oceania 2 red
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public Continent[] loadContinents(BufferedReader reader) throws IOException {

		ArrayList<Continent> continents = new ArrayList<>();
		Continent continent;
		String line;
		String tokens[];

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			continent = new Continent.Builder(tokens[0])
					.setValue(Integer.parseInt(tokens[1]))
					.build();
			continents.add(continent);
		}

		return continents.toArray(new Continent[continents.size()]);
	}

	/**
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public Country[] loadCountries(BufferedReader reader, Continent[] continents) throws IOException {

		ArrayList<Country> countries = new ArrayList<>();
		Country country;
		String line;
		String tokens[];
		int continentNumber;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			continentNumber = Integer.parseInt(tokens[2]);
			country = new Country.Builder(tokens[1], continents[continentNumber - 1])
					.build();

			countries.add(country);
		}

		return countries.toArray(new Country[countries.size()]);
	}

	public void loadBorders(BufferedReader reader, Country[] countries) throws IOException {
		Country country;
		String line;
		String tokens[];
		int countryNumber;

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			countryNumber = Integer.parseInt(tokens[0]);
			country = countries[countryNumber - 1];
			for (int i = 1; i < tokens.length; i++) {

			}
			country = new Country.Builder(tokens[1], continents[continentNumber - 1])
					.build();
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
