package ca.concordia.encs.conquerdia.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ca.concordia.encs.conquerdia.model.map.Country;

public class Battle {
	private Country winner;
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

	public String simulateAttack() {
		int attackerKilledArmies = 0;
		int defenderKilledArmies = 0;
		int maxDiceRolled = numberOfAttackerDices > numberOfDefenderDices ? numberOfAttackerDices:numberOfDefenderDices;
		Integer[] attackerDiceRolled = new Integer[numberOfAttackerDices];
		Integer[] defenderDiceRolled = new Integer[numberOfDefenderDices];
		Arrays.sort(attackerDiceRolled, (a, b) -> b - a);
		Arrays.sort(defenderDiceRolled, (a, b) -> b - a);
		for(int i = 0 ; i < maxDiceRolled ; i++) {
			if(defenderDiceRolled[i] >=  attackerDiceRolled[i]) {
				defenderKilledArmies++;
			}else {
				attackerKilledArmies++;
			}
		}
		fromCountry.removeArmy(attackerKilledArmies);
		toCountry.removeArmy(defenderKilledArmies);
		return null;
	}

	public void allOutAttack(Country fromCountry, Country toCountry) {

	}

}
