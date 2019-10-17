package ca.concordia.encs.conquerdia.engine.map.io;

import ca.concordia.encs.conquerdia.engine.map.Continent;
import ca.concordia.encs.conquerdia.engine.map.Country;
import ca.concordia.encs.conquerdia.engine.map.WorldMap;
import ca.concordia.encs.conquerdia.engine.util.FileHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Map Writer class
 */
class MapWriter extends MapIO implements IMapWriter {

    static final String CONTINENT_ROW_FORMAT = "%s" + TOKENS_DELIMETER + "%s" + TOKENS_DELIMETER + "%s";
    private final WorldMap worldMap;
    protected BufferedWriter writer;

    /**
     * @param worldMap map model
     */
    public MapWriter(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * @param filename file name
     * @return true if success
     */
    public boolean writeMap(String filename) {
        String[] borderRows;
        ArrayList<CountryRow> countryRows;

        try {
            final String mapName = FileHelper.getFileNameWithoutExtension(filename);
            final String path = MapIO.getMapFilePath(filename);

            writer = new BufferedWriter(new FileWriter(path));

            writeComments(new String[]{"RISK MAP", "Conquerdia Map Editor"});
            writer.newLine();

            writeResourceFiles(new String[]{"pic " + mapName + "_pic.png",
                    "map " + mapName + "_map.gif",
                    "crd card.cards", "prv " + mapName + ".jpg"});
            writer.newLine();

            writeMapName(mapName.toUpperCase());
            writer.newLine();

            writeContinents();
            writer.newLine();

            countryRows = getAllCountryRows(new ArrayList<Continent>(worldMap.getContinents()));
            writeCountries(countryRows);
            writer.newLine();

            borderRows = getBorders(countryRows);
            writeBorders(borderRows);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                }
            }
        }

        return true;
    }

    /**
     * @param borderRows
     * @throws IOException
     */
    private void writeBorders(String[] borderRows) throws IOException {
        writeSection(BORDERS_SECTION_IDENTIFIER, borderRows);
    }

    /**
     * @param countryRows
     * @throws IOException
     */
    private void writeCountries(ArrayList<CountryRow> countryRows) throws IOException {
        String[] rows = countryRows.stream()
                .map(CountryRow::toString)
                .toArray(String[]::new);
        writeSection(COUNTRIES_SECTION_IDENTIFIER, rows);
    }

    /**
     * @throws IOException
     */
    private void writeContinents() throws IOException {
        Set<Continent> continents = worldMap.getContinents();
        String[] rows = new String[continents.size()];
        int i = 0;
        for (Continent continent : continents) {
            rows[i++] = String.format(CONTINENT_ROW_FORMAT, continent.getName(), continent.getValue(), "Black");
        }
        writeSection(CONTINENTS_SECTION_IDENTIFIER, rows);
    }

    private void writeResourceFiles(String[] files) throws IOException {
        writeSection(FILES_SECTION_IDENTIFIER, files);
    }

    /**
     * Writes the name of the map to the file in name {@code mapName} Map
     *
     * @param mapName The name of the map
     * @throws IOException
     */
    private void writeMapName(String mapName) throws IOException {
        writeLine("name " + mapName + " map");
    }

    /**
     * @param comments
     * @throws IOException
     */
    private void writeComments(String[] comments) throws IOException {
        for (String comment : comments) {
            writeLine(COMMENT_SYMBOL + " " + comment);
        }
    }

    /**
     * @param sectionIdentifier
     * @param rows
     * @throws IOException
     */
    private void writeSection(String sectionIdentifier, String[] rows) throws IOException {
        writeLine(sectionIdentifier);
        for (String row : rows) {
            writeLine(row);
        }
    }

    /**
     * @param line
     * @throws IOException
     */
    private void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    /**
     * @param continents
     * @return
     */
    private ArrayList<CountryRow> getAllCountryRows(ArrayList<Continent> continents) {
        ArrayList<CountryRow> rows = new ArrayList<CountryRow>();
        Set<Country> countries;
        CountryRow countryRow;
        int countryNumber = 0;

        for (int i = 0; i < continents.size(); i++) {
            countries = continents.get(i).getCountries();

            for (Country country : countries) {
                countryNumber++;
                countryRow = countryRowFromCountry(country, countryNumber, i + 1);
                rows.add(countryRow);
            }
        }
        return rows;
    }

    /**
     * @param countryRows
     * @return
     */
    private String[] getBorders(ArrayList<CountryRow> countryRows) {
        String[] borders = new String[countryRows.size()];
        CountryRow countryRow;
        HashMap<String, Integer> countryNumbers = new HashMap<>();
        StringBuilder builder;

        for (int i = 0; i < countryRows.size(); i++) {
            countryRow = countryRows.get(i);
            countryNumbers.put(countryRow.getName(), countryRow.getNumber());
        }

        for (int i = 0; i < countryRows.size(); i++) {
            builder = new StringBuilder();
            countryRow = countryRows.get(i);
            builder.append(countryRow.getNumber());

            for (String countryName : countryRow.getAdjacentCountryNames()) {
                builder.append(TOKENS_DELIMETER + countryNumbers.get(countryName));
            }
            borders[i] = builder.toString();
        }
        return borders;
    }

    /**
     * @param country
     * @param number
     * @param continentNumber
     * @return
     */
    private CountryRow countryRowFromCountry(Country country, int number, int continentNumber) {
        CountryRow countryRow = new CountryRow(number, country.getName(), continentNumber);
        countryRow.setAdjacentCountryNames(country.getAdjacentCountries()
                .stream()
                .map(Country::getName)
                .toArray(String[]::new));

        return countryRow;
    }
}
