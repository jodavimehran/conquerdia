package ca.concordia.encs.conquerdia.engine.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MapFileIO {

	public static final String COMMENT_SYMBOL = ";";
	public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";
	public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";
	public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";
	public static final String TOKENS_DELIMETER = " ";

	private String filePath;

	public MapFileIO(String filePath) {
		this.filePath = filePath;
	}

	public void loadMap() throws FileNotFoundException, IOException {

		final BufferedReader reader = new BufferedReader(new FileReader(filePath));

		try {
			String line;

			while ((line = reader.readLine()) != null) {
				if (isContientsIdentifier(line)) {
					loadContinents(reader);
				} else if (isCountriesIdentifier(line)) {
					loadCountries(reader);
				} else if (isBordersIdentifier(line)) {

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
	public HashMap<String, Continent> loadContinents(BufferedReader reader) throws IOException {

		HashMap<String, Continent> continents = new HashMap<String, Continent>();
		Continent continent;
		String line;
		String tokens[];

		while ((line = reader.readLine()) != null && line.length() > 0) {
			tokens = line.split(TOKENS_DELIMETER);
			continent = new Continent.Builder(tokens[0])
					.setValue(Integer.parseInt(tokens[1]))
					.build();
			continents.put(continent.getName(), continent);
		}

		return continents;
	}

	public HashMap<String, Country> loadCountries(BufferedReader reader) throws IOException {

		// TODO
		return null;
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
