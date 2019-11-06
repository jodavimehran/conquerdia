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

	public String simulate() {
		int attackerKilledArmies = 0;
		int defenderKilledArmies = 0;

		int minDiceRolled = Math.min(numberOfAttackerDices, numberOfDefenderDices);

		Integer[] attackerDiceRolled = new Integer[numberOfAttackerDices];
		Integer[] defenderDiceRolled = new Integer[numberOfDefenderDices];
		
		Arrays.sort(attackerDiceRolled, (a, b) -> b - a);
		Arrays.sort(defenderDiceRolled, (a, b) -> b - a);
		
		for (int i = 0; i < minDiceRolled; i++) {
			if (defenderDiceRolled[i] >= attackerDiceRolled[i]) {
				attackerKilledArmies++;
			} else {
				defenderKilledArmies++;
			}
		}
		
		fromCountry.removeArmy(attackerKilledArmies);
		toCountry.removeArmy(defenderKilledArmies);
		
		// Check if toCuntry is Conquered
		Player currentPlayer = PhaseModel.getInstance().getCurrentPlayer();
		if (toCountry.getNumberOfArmies() == 0) {
			toCountry.setOwner(currentPlayer);
			PhaseModel.getInstance().getCurrentPlayer().setHasSuccessfulAttack(true);
			/// Check Number of countries owned by the defender, if 0 gives all cards to
			/// attacker and remove player from model player queue
			Player defender = toCountry.getOwner();
			if (defender.getNumberOfCountries() == 0) {
				currentPlayer.getCards().addAll(defender.getCards());
				PlayersModel.getInstance().getPlayers().remove(defender);
			}
		}
		return null;
	}

	public void allOutAttack(Country fromCountry, Country toCountry) {

	}
}
