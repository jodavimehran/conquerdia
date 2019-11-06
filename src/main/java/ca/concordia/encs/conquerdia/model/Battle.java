package ca.concordia.encs.conquerdia.model;

import java.util.Arrays;

import ca.concordia.encs.conquerdia.model.map.Country;

public class Battle {
	private Country winner;
	private Country fromCountry;
	private Country toCountry;
	private int numberOfAttackerDices;
	private int numberOfDefenderDices;
	private DiceRoller diceRoller;
	private boolean isAllOut;

	public Battle(Country attackingCountry, Country defendingCountry) {
		this.fromCountry = attackingCountry;
		this.toCountry = defendingCountry;
		diceRoller = DiceRoller.getInstance();
	}

	public String simulateBattle() {
		int[] attackerDiceRolled = diceRoller.generateSortedDices(numberOfAttackerDices);
		int[] defenderDiceRolled = diceRoller.generateSortedDices(numberOfDefenderDices);

		int killedByDefender = 0;
		int killedByAttacker = 0;
		int minDiceRolled = Math.min(numberOfAttackerDices, numberOfDefenderDices);

		for (int i = 0; i < minDiceRolled; i++) {
			if (defenderDiceRolled[i] >= attackerDiceRolled[i]) {
				killedByDefender++;
			} else {
				killedByAttacker++;
			}
		}

		fromCountry.removeArmy(killedByDefender);
		toCountry.removeArmy(killedByAttacker);

		// Check if toCuntry is Conquered
		if (toCountry.hasNoArmy()) {
			conquer();

			return String.format(
					"Congrats! {0} has conquered {1}. Please move atleast {2} of your armies from {3} to the conqured country",
					fromCountry.getOwner().getName(), toCountry.getName(), numberOfAttackerDices,
					fromCountry.getName());
		}

		return String.format("Attacker rolled {0} & killed {1} armies. Defender rolled {2} and killed {3} armies",
				Arrays.toString(attackerDiceRolled),
				killedByAttacker,
				Arrays.toString(defenderDiceRolled),
				killedByDefender);
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

	public boolean isAllOut() {
		return isAllOut;
	}

	public void setAllOut(boolean isAllOut) {
		this.isAllOut = isAllOut;
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
}
