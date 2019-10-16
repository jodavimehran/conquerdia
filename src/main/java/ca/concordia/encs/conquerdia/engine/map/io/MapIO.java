package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.util.FileHelper;

import java.util.ArrayList;

public abstract class MapIO {
    public static final String COMMENT_SYMBOL = ";";
    public static final String CONTINENTS_SECTION_IDENTIFIER = "[continents]";
    public static final String COUNTRIES_SECTION_IDENTIFIER = "[countries]";
    public static final String BORDERS_SECTION_IDENTIFIER = "[borders]";
    public static final String FILES_SECTION_IDENTIFIER = "[files]";
    public static final String TOKENS_DELIMETER = " ";
    public static final String MAPS_FOLDER = "maps";
    public static final String MAP_FILE_EXTENSION = ".map";

    protected ArrayList<String> continentList = new ArrayList<>();
    protected ArrayList<String> countryList = new ArrayList<>();

    /**
     * Returns the absolute path to the map file residing in the
     * {@value #MAPS_FOLDER}
     *
     * @param filename The name of the map file with or without extension
     * @return The absolute path to the file
     */
    protected static String getMapFilePath(String filename) {
        return FileHelper.getResourcePath(FileHelper.combinePath(MAPS_FOLDER,
                FileHelper.getFileNameWithoutExtension(filename) + MAP_FILE_EXTENSION));
    }

    /**
     * Checks if the line in file starts the comment symbol
     *
     * @param line A line from the map file
     * @return true if line starts with {@value #COMMENT_SYMBOL} otherwise false
     */
    protected static boolean isComment(String line) {
        return line.startsWith(COMMENT_SYMBOL);
    }

    /**
     * Checks if the line in file matches the continent tag
     *
     * @param line A line from the map file
     * @return true if line is {@value #CONTINENTS_SECTION_IDENTIFIER} otherwise
     * false
     */
    protected static boolean isContientsIdentifier(String line) {
        return line.equalsIgnoreCase(CONTINENTS_SECTION_IDENTIFIER);
    }

    /**
     * Checks if the line in file matches the countries tag
     *
     * @param line A line from the map file
     * @return true if line is {@value #COUNTRIES_SECTION_IDENTIFIER} otherwise
     * false
     */
    protected static boolean isCountriesIdentifier(String line) {
        return line.equalsIgnoreCase(COUNTRIES_SECTION_IDENTIFIER);
    }

    /**
     * Checks if the line in file matches the borders tag
     *
     * @param line A line from the map file
     * @return true if line is {@value #BORDERS_SECTION_IDENTIFIER} otherwise false
     */
    protected static boolean isBordersIdentifier(String line) {
        return line.equalsIgnoreCase(BORDERS_SECTION_IDENTIFIER);
    }
}
