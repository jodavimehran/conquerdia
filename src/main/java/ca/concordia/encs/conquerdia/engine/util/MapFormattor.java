package ca.concordia.encs.conquerdia.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import ca.concordia.encs.conquerdia.engine.map.Country;
import dnl.utils.text.table.TextTable;

public class MapFormattor {

	private Map<String, Country> countries;

	public MapFormattor(Map<String, Country> countries) {
		this.countries = countries;
	}

	public String format() {
		String[] columnNames = getColumnNames();
		Object[][] data = new Object[countries.size()][columnNames.length];
		Country country;
		String neighborsAsCsv;

		int count = 0;
		for (Map.Entry<String, Country> item : countries.entrySet()) {
			country = item.getValue();
			data[count][0] = country.getName();
			data[count][1] = country.getContinent().getName();
			neighborsAsCsv = String.join(",", country.getAdjacentCountries()
					.stream()
					.map(Country::getName)
					.collect(Collectors.toList()));
			data[count][2] = neighborsAsCsv;
			count++;
		}

		return format(columnNames, data);
	}

	private String[] getColumnNames() {
		return new String[] { "Country", "Continent", "Adjacent Countries" };
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
}
