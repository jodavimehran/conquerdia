package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.model.map.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battle {
    private Country winner;
    private Country fromCountry;
    private Country toCountry;
    private int numberOfAttackerDices;
    private int numberOfDefenderDices;
    private DiceRoller diceRoller;

    private BattleState state;

    /**
     * The constructor of the battle class
     *
     * @param attackingCountry The attacker country
     * @param defendingCountry The country that is being attacked.
     */
    public Battle(Country attackingCountry, Country defendingCountry) {
        state = BattleState.Attacked;
        this.fromCountry = attackingCountry;
        this.toCountry = defendingCountry;
        diceRoller = DiceRoller.getInstance();
    }

    /**
     * Performs an allOut attack
     *
     * @return the result of the allOut Attack
     */
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
                log.addAll(simulateBattle());
                continueAttack = (winner == null);
            }
        } while (continueAttack);

        return log;
    }

    /**
     * This method simulates the battle for attack commands
     *
     * @return An String of arrays demonstrating the  result of  simulating the attacks.
     */
    public ArrayList<String> simulateBattle() {
        state = BattleState.Defended;
        ArrayList<String> log = new ArrayList<>();

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

        log.add(String.format("Attacker rolled %s & killed %s armies. Defender rolled %s and killed %s armies",
                Arrays.toString(attackerDiceRolled),
                killedByAttacker,
                Arrays.toString(defenderDiceRolled),
                killedByDefender));

        // Check if toCuntry is Conquered
        if (toCountry.hasNoArmy()) {
            log.addAll(conquer());
            log.add(String.format(
                    "Congrats! %s has conquered %s. Please move atleast %s of your armies from %s to the conquered country %s.",
                    fromCountry.getOwner().getName(), toCountry.getName(), 1,
                    fromCountry.getName(), toCountry.getName()));
        }

        return log;
    }

    /**
     * @return The maximum dice count of attacker for the all out phase
     */
    private int getMaxDiceCountForAttacker() {
        int armies = fromCountry.getNumberOfArmies();
        int count = 0;

        switch (armies) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                count = Math.min(armies, 3);
        }

        return count;
    }

    /**
     * @return The maximum dice count of defender for the all out phase
     */
    private int getMaxDiceCountForDefender() {
        int armies = fromCountry.getNumberOfArmies();// toCountry.getNumberOfArmies();
        return Math.min(armies, 2);
    }

    /**
     * When the attack is done and the country is conquered this method is performed
     * to apply attack related rules
     */
    private List<String> conquer() {
        List<String> result = new ArrayList<>();
        state = BattleState.Conquered;
        Player attacker = fromCountry.getOwner();
        toCountry.setOwner(attacker);
        attacker.addCountry(toCountry);

        winner = fromCountry;
        // fromCountry.getOwner().setAttackFinished();
        attacker.setSuccessfulAttack(true);
        /// Check Number of countries owned by the defender, if 0 gives all cards to
        /// attacker and remove player from model player queue
        Player defender = toCountry.getOwner();
        defender.removeCountry(toCountry.getName());
        if (defender.getNumberOfCountries() == 0) {
            result.add(String.format("%s is kicked out from game.", defender.getName()));
            PlayersModel.getInstance().getPlayers().remove(defender);
            if (defender.getCards().size() > 0) {
                result.add(String.format("Attacker (%s) wins all %d cards of kicked out player (%s).", attacker.getName(), defender.getCards().size(), defender.getName()));
                attacker.getCards().addAll(defender.getCards());
            }
        }

        boolean isAttackerOwnedAContinent = attacker.ownedAll(toCountry.getContinent().getCountriesName());
        if (isAttackerOwnedAContinent) {
            result.add(String.format("Attacker (%s) conquered a continent as well.", attacker.getName(), defender.getCards().size(), defender.getName()));
            attacker.addContinent(toCountry.getContinent());
        }
        return result;
    }

    /**
     * @return true if the result of an attack is to conquer a country; otherwise return false.
     */
    public boolean isConquered() {
        return winner != null;
    }

    /**
     * @return true if more attack is possible
     */
    public boolean isMoreAttackPossible() {
        return fromCountry.getNumberOfArmies() > 1;
    }

    /**
     * @return the Attacking country
     */
    public Country getFromCountry() {
        return fromCountry;
    }

    /**
     * @return the country that is being attacked.
     */
    public Country getToCountry() {
        return toCountry;
    }

    /**
     * @return the number of Attacker dices.
     */
    public int getNumberOfAttackerDices() {
        return numberOfAttackerDices;
    }

    /**
     * Set the number of attacker dices
     *
     * @param numberOfAttackerDices number of attacker dices
     */
    public void setNumberOfAttackerDices(int numberOfAttackerDices) {
        this.numberOfAttackerDices = numberOfAttackerDices;
    }

    /**
     * @return the number of defender dices
     */
    public int getNumberOfDefenderDices() {
        return numberOfDefenderDices;
    }

    /**
     * Sets the number of defender dices
     *
     * @param numberOfDefenderDices number of defender dices
     */
    public void setNumberOfDefenderDices(int numberOfDefenderDices) {
        this.numberOfDefenderDices = numberOfDefenderDices;
    }

    /**
     * @return The winner country
     */
    public Country getWinner() {
        return winner;
    }

    /**
     * @return get the current state of the battle
     */
    public BattleState getState() {
        return state;
    }

    /**
     * @return true if the attack is possible; false otherwise.
     */
    public boolean isAttackPossible() {
        return state == BattleState.Defended;
    }

    /**
     * @return true if the defend is possible; otherwise false.
     */
    public boolean isDefendPossible() {
        return state == BattleState.Attacked;
    }

    public enum BattleState {
        Attacked,
        Defended,
        Conquered,
    }
}
