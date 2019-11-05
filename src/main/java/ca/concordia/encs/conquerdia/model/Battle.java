package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.model.map.Country;

public class Battle {
	private Country fromCountry;
	private Country toCountry;
	private int numberOfAttackerDices;
	private int numberOfDefenderDices;
	
	public Country getFromCountry() {
		return fromCountry;
	}
	public void setFromCountry(Country fromCountry) {
		this.fromCountry = fromCountry;
	}
	public Country getToCountry() {
		return toCountry;
	}
	public void setToCountry(Country toCountry) {
		this.toCountry = toCountry;
	}
	public int getNumberOfAttackerDices() {
		return numberOfAttackerDices;
	}
	public void setNumberOfAttackerDices(int numberOfAttackerDices) {
		this.numberOfAttackerDices = numberOfAttackerDices;
	}
	public int getNumberOfDefenderDices() {
		return numberOfDefenderDices;
	}
	public void setNumberOfDefenderDices(int numberOfDefenderDices) {
		this.numberOfDefenderDices = numberOfDefenderDices;
	}
	
}
