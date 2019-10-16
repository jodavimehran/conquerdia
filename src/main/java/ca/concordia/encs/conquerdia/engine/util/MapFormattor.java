package ca.concordia.encs.conquerdia.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import ca.concordia.encs.conquerdia.engine.map.Country;
import dnl.utils.text.table.TextTable;

public class MapFormattor {

	private Map<String, Country> countries;

	public MapFormattor(Map<String, Country> countries) {
		this.countries = countries;
	}

	public String format() {
		String[] columnNames = getColumnNames();

		Object[][] data = {
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe",
						"Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black",
						"Knitting", new Integer(2), new Boolean(false) },
				{ "Jane", "White",
						"Speed reading", new Integer(20), new Boolean(true) },
				{ "Joe", "Brown",
						"Pool", new Integer(10), new Boolean(false) }
		};
		return format(columnNames, data);
	}

	private String[] getColumnNames() {
		return new String[] { "Country", "Contijent"};
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
