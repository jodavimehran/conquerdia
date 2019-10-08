package ca.concordia.encs.conquerdia.engine.map.io;

import java.util.ArrayList;

class CountryRow {

	private static final String delimeter = MapIO.TOKENS_DELIMETER;

	private int number;
	private String name;
	private int continentNumber;
	private int xPosition;
	private int yPosition;
	private String[] adjacentCountryNames; 
	
	public CountryRow(int number, String name, int continentNumber) {
		this.number = number;
		this.name = name;
		this.continentNumber = continentNumber;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public int getContinentNumber() {
		return continentNumber;
	}

	public void setContinentNumber(int continentNumber) {
		this.continentNumber = continentNumber;
	}

	@Override
	public String toString() {

		return number + delimeter + name + delimeter + continentNumber + delimeter + xPosition
				+ delimeter + yPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public String[] getAdjacentCountryNames() {
		return adjacentCountryNames;
	}

	public void setAdjacentCountryNames(String[] adjacentCountryNames) {
		this.adjacentCountryNames = adjacentCountryNames;
	}
}
