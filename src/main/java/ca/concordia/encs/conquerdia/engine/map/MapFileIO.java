package ca.concordia.encs.conquerdia.engine.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class MapFileIO {

	public static final String COMMENT_SYMBOL = ";";
	public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";
	public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";
	public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";
	public static final String TOKENS_DELIMETER = " ";

	private String filePath;

	public MapFileIO(String filePath) {
		String root = getUsersProjectRootDirectory();
		this.filePath = Paths.get(root, "res", "maps", filePath).toString();
	}

	public void loadMap() throws FileNotFoundException, IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		WorldMap worldMap = new WorldMap();

		while ((line = reader.readLine()) != null) {
			if (!isComment(line)) {
				if (isBordersIdentifier(line)) {

				} else if (isContientsIdentifier(line)) {
					loadContinents(reader);
				}
			}
		}
	}

	/**
	 * [continents] North-America 5 yellow
	 * South-America 2 green
	 * Europe 5 blue
	 * Africa 3 orange
	 * Asia 7 pink
	 * Oceania 2 red
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public Set<Continent> loadContinents(BufferedReader reader) throws IOException {

		Set<Continent> continents = new HashSet<Continent>();
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

		return continents;
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

	public static String getUsersProjectRootDirectory() {
		String envRootDir = System.getProperty("user.dir");
		Path rootDir = Paths.get(".").normalize().toAbsolutePath();
		if (rootDir.startsWith(envRootDir)) {
			return rootDir.toString();
		} else {
			throw new RuntimeException("Root dir not found in user directory.");
		}
	}
}
