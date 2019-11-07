package ca.concordia.encs.conquerdia.model;

import java.util.ArrayList;
import java.util.Arrays;

import ca.concordia.encs.conquerdia.model.map.Country;

public class Battle {
	private Country winner;
	private Country fromCountry;
	private Country toCountry;
	private int numberOfAttackerDices;
	private int numberOfDefenderDices;
	private DiceRoller diceRoller;

	private BattleState state;

	public enum BattleState {
		Attacked,
		Defended,
		Conquered,
	}

	public Battle(Country attackingCountry, Country defendingCountry) {
		state = BattleState.Attacked;
		this.fromCountry = attackingCountry;
		this.toCountry = defendingCountry;
		diceRoller = DiceRoller.getInstance();
	}

	public ArrayList<String> allOutAttack() {
		ArrayList<String> log = new ArrayList<String>();
		boolean continueAttack = true;

		do {
			numberOfAttackerDices = getMaxDiceCountForAttacker();
			if (numberOfAttackerDices < 1) {
				continueAttack = false;
				log.add(String.format("%s does not have anymore army to attack", fromCountry.getOwner().getName()));
			} else {
				numberOfDefenderDices = getMaxDiceCountForDefender();
				state = BattleState.Attacked;
				log.add(simulateBattle());
				continueAttack = (winner == null);
			}
		} while (continueAttack);
		fromCountry.getOwner().setAttackFinished();
		return log;
	}

	public String simulateBattle() {
		state = BattleState.Defended;

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
					"Congrats! %s has conquered %s. Please move atleast %s of your armies from %s to the conqured country %s.",
					fromCountry.getOwner().getName(), toCountry.getName(), numberOfAttackerDices,
					fromCountry.getName(), toCountry.getName());
		}

		return String.format("Attacker rolled %s & killed %s armies. Defender rolled %s and killed %s armies",
				Arrays.toString(attackerDiceRolled),
				killedByAttacker,
				Arrays.toString(defenderDiceRolled),
				killedByDefender);
	}

	/**
	 * 
	 * @return The maximum dice count of attacker for the all out phase
	 */
	private int getMaxDiceCountForAttacker() {
		int armies = fromCountry.getNumberOfArmies();
		int count = 0;

		if (armies > 1) {
			count = Math.min(armies, 3);
		}
		return count;
	}

	/**
	 * 
	 * @return The maximum dice count of defender for the all out phase
	 */
	private int getMaxDiceCountForDefender() {
		int armies = fromCountry.getNumberOfArmies();// toCountry.getNumberOfArmies();
		return Math.min(armies, 2);
	}

	private void conquer() {
		state = BattleState.Conquered;
		toCountry.setOwner(fromCountry.getOwner());
		winner = fromCountry;
		fromCountry.getOwner().setAttackFinished();
		fromCountry.getOwner().setSuccessfulAttack(true);
		/// Check Number of countries owned by the defender, if 0 gives all cards to
		/// attacker and remove player from model player queue
		Player defender = toCountry.getOwner();
		if (defender.getNumberOfCountries() == 0) {
			fromCountry.getOwner().getCards().addAll(defender.getCards());
			PlayersModel.getInstance().getPlayers().remove(defender);
		}
		Player attacker = fromCountry.getOwner();
		boolean isAttackerOwnedAContinent = attacker.ownedAll(toCountry.getContinent().getCountriesName());
		if (isAttackerOwnedAContinent) {
			attacker.addContinent(toCountry.getContinent());
			attacker.addUnplacedArmies(toCountry.getContinent().getValue());
		}
	}

	public boolean isConquered() {
		return winner != null;
	}

	public boolean isMoreAttackPossible() {
		return fromCountry.getNumberOfArmies() > 1;
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

	public Country getWinner() {
		return winner;
	}

	public BattleState getState() {
		return state;
	}

	public boolean isAttackPossible() {

		return state == BattleState.Defended;
	}

	public boolean isDefendPossible() {
		return state == BattleState.Attacked;
	}
}
