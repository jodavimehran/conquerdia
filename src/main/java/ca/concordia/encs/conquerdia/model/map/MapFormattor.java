package ca.concordia.encs.conquerdia.model.map;

import ca.concordia.encs.conquerdia.model.map.Country;
import dnl.utils.text.table.TextTable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapFormattor {
    
	private WorldMap worldMap;
    
    public MapFormattor(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public String format() {
        return this.format(FormatType.Default);
    }

    public String format(FormatType type) {
    	Set<Country> countries = worldMap.getCountries();
    	
        String[] columnNames = getColumnNames(type);
        Object[][] data = new Object[countries.size()][columnNames.length];
       
        int count = 0;
        for (Country country : countries) {
            data[count] = formatCountry(country, type);
            count++;
        }

        return format(columnNames, data);
    }
    
    private String[] formatCountry(Country country, FormatType type) {
        ArrayList<String> values = new ArrayList<>();

        String neighborsAsCsv = String.join(",", country.getAdjacentCountries()
                .stream()
                .map(Country::getName)
                .collect(Collectors.toList()));

        values.add(country.getName());
        if (type == FormatType.Detail) {
            values.add(country.getNumberOfArmies() + "");
            values.add(country.getOwner() != null ? country.getOwner().getName() : "");
        }
        values.add(country.getContinent().getName() + " (" + country.getContinent().getValue() + ")");
        values.add(neighborsAsCsv);
        return values.toArray(new String[0]);
    }

    private String[] getColumnNames(FormatType type) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Country");

        if (type == FormatType.Detail) {
            columns.add("Armies");
            columns.add("Owner");
        }
        columns.add("Continent (ControlValue)");
        columns.add("Adjacent Countries");
        return columns.toArray(new String[0]);
    }

    private String format(String[] columnNames, Object[][] data) {
        TextTable tt = new TextTable(columnNames, data);
        tt.setAddRowNumbering(false);
        // sort by the first column
        tt.setSort(0);
        return getTTString(tt, 0);
    }

    private String getTTString(TextTable tt, int indent) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {
            tt.printTable(ps, indent);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        return data;
    }

    public enum FormatType {
        Default,
        Detail
    }
}
