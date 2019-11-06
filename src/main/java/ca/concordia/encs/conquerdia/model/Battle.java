package ca.concordia.encs.conquerdia.model;

import java.util.Arrays;

import ca.concordia.encs.conquerdia.model.map.Country;

public class Battle {
	private Country winner;
	private Country fromCountry;
	private Country toCountry;
	private int numberOfAttackerDices;
	private int numberOfDefenderDices;

	public Battle(Country attackingCountry, Country defendingCountry) {
		this.fromCountry = attackingCountry;
		this.toCountry = defendingCountry;
	}

	public Country getFromCountry() {
		return fromCountry;
	}

	public Country getToCountry() {
		return toCountry;
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

	public String simulateBattle() {
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
		if (toCountry.getNumberOfArmies() == 0) {
			conquer();

			return String.format(
					"Congrats! {0} has conquered {1}. Please move atleast {2} of your armies from {3} to the conqured country",
					fromCountry.getOwner().getName(), toCountry.getName(), numberOfAttackerDices,
					fromCountry.getName());
		}

		return null;
	}

	private void conquer() {
		toCountry.setOwner(fromCountry.getOwner());
		winner = fromCountry;
		fromCountry.getOwner().setSuccessfulAttack(true);
		/// Check Number of countries owned by the defender, if 0 gives all cards to
		/// attacker and remove player from model player queue
		Player defender = toCountry.getOwner();
		if (defender.getNumberOfCountries() == 0) {
			fromCountry.getOwner().getCards().addAll(defender.getCards());
			PlayersModel.getInstance().getPlayers().remove(defender);
		}
	}

	public boolean isConquered() {
		return winner != null;
	}

	public boolean isMoreAttackPossible() {
		return fromCountry.getNumberOfArmies() > 1;
	}

	public void allOutAttack(Country fromCountry, Country toCountry) {

	}
}
