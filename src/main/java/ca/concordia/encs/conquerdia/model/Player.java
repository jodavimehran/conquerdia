package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.security.SecureRandom;
import java.util.*;

/**
 * Represents a player in the Game
 */
public class Player {
	private static int NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD = 0;
	/**
	 * Player Name
	 */
	private final String name;
	/**
	 * Countries owned by this player
	 */
	private final HashMap<String, Country> countries = new HashMap<>();
	/**
	 * Continents owned by this player
	 */
	private final HashMap<String, Continent> continents = new HashMap<>();
	/**
	 * List of the card that this player has
	 */
	private final List<CardType> cards = new ArrayList<>();
	/**
	 * The number of armies that belong to this player and are not placed on any
	 * country.
	 */
	private int unplacedArmies = 0;

	/**
	 * true if fortification phase for the current turn has down by player
	 */
	private boolean fortificationFinished;

	/**
	 * This Class performs the simulation for Attack
	 */
	private Battle battle;
	/**
	 * This Attribute shows the Attack is finished.
	 */
	private boolean attackFinished;

	/**
	 * This Attribute shows that during the attack phase of the current player there
	 * has been a successful attack
	 */
	private boolean successfulAttack;

	/**
	 * @param name The name of a player must be determined when you want to create a
	 *             player
	 */
	private Player(String name) {
		this.name = name;
	}

	private static int getNumberOfArmiesForExchangeCard() {
		NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD += 5;
		return NUMBER_OF_ARMIES_FOR_EXCHANGE_CARD;
	}

	/**
	 * @param successfulAttack
	 */
	public void setSuccessfulAttack(boolean successfulAttack) {
		this.successfulAttack = successfulAttack;
	}

	public boolean hasSuccessfulAttack() {
		return successfulAttack;
	}

	/**
	 * Gets the battle of the player
	 */
	public Battle getBattle() {
		return battle;
	}

	/**
	 * This is set to true whenever the attack is finished.
	 */
	public void setAttackFinished() {
		this.attackFinished = true;
	}

	/**
	 * @return true if fortification is down
	 */
	public boolean isFortificationFinished() {
		return fortificationFinished;
	}

	/**
	 * @return true if attack is down
	 */
	public boolean isAttackFinished() {
		return attackFinished;
	}

	/**
	 * @return the name of this player
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param add The number of armies that you want to add to unplaced armies of
	 *            this player
	 */
	public void addUnplacedArmies(int add) {
		if (add > 0)
			unplacedArmies += add;
	}

	/**
	 * @param minus the number of armies that you want to minus from unplaced armies
	 *              of this player
	 */
	public void minusUnplacedArmies(int minus) {
		unplacedArmies -= minus;
		if (unplacedArmies < 0)
			unplacedArmies = 0;
	}

	/**
	 * Add a country to the list of the countries that owned by this player
	 *
	 * @param country The country to add
	 */
	public void addCountry(Country country) {
		countries.put(country.getName(), country);
	}

	/**
	 * Remove a country from the list of the countries that owned by this player
	 *
	 * @param countryName countryName
	 */
	public void removeCountry(String countryName) {
		countries.remove(countryName);
	}

	/**
	 * @return the number of countries this player owns
	 */
	public int getNumberOfCountries() {
		return countries.size();
	}

	/**
	 * @return name of the countries this player owns
	 */
	public Set<String> getCountryNames() {
		return countries.keySet();
	}

	/**
	 * @return name of the continents this player owns
	 */
	public Set<String> getContinentNames() {
		return continents.keySet();
	}

	/**
	 * @return the total number of armies owned by this player
	 */
	public int getTotalNumberOfArmies() {
		return countries.values().stream().mapToInt(Country::getNumberOfArmies).sum();
	}

	/**
	 * @return the number of continents this player owns
	 */
	public int getNumberOfContinent() {
		return continents.size();
	}

	/**
	 * Add a continent to the list of the continents that owned by this player
	 *
	 * @param continent The continent to be added to the list of continents that
	 *                  owned by this player
	 */
	public void addContinent(Continent continent) {
		continents.put(continent.getName(), continent);
	}

	/**
	 * Remove a continent from the list of the continents that owned by this player
	 *
	 * @param continentName Name of the continent to be removed
	 */
	public void removeContinent(String continentName) {
		continents.remove(continentName);
	}

	/**
	 * Returns <tt>true</tt> if this player own all of the countries of the
	 * specified collection.
	 *
	 * @param countries countries to be checked for owned by this player
	 * @return <tt>true</tt> if this player own all of the countries of the
	 *         specified collection
	 */
	public boolean ownedAll(Set<String> countries) {
		return this.countries.keySet().containsAll(countries);
	}

	/**
	 * This Method return checks if the player owns the specified country.
	 *
	 * @param country the specified country
	 * @return returns true if the player owns the specified country.
	 */
	public boolean owns(String country) {
		return this.countries.containsKey(country);
	}

	/**
	 * @return number of reinforcement armies according to the Risk rules.
	 */
	public int calculateNumberOfReinforcementArmies() {
		int numberOfReinforcementArmies = 0;
		numberOfReinforcementArmies += countries.size() / 3;
		for (Map.Entry<String, Continent> entry : continents.entrySet()) {
			numberOfReinforcementArmies += entry.getValue().getValue();
		}
		if (numberOfReinforcementArmies < 3)
			numberOfReinforcementArmies = 3;
		unplacedArmies = numberOfReinforcementArmies;
		return numberOfReinforcementArmies;

	}

	/**
	 * @return The number of armies that belong to this player and are not placed on
	 *         any country.
	 */
	public int getUnplacedArmies() {
		return unplacedArmies;
	}

	/**
	 * This method is called when a player use none in fortification phase
	 */
	public void fortify() {
		fortificationFinished = true;
	}

	/**
	 * @param fromCountryName source country
	 * @param toCountryName   destination country
	 * @param numberOfArmy    number of army
	 * @throws ValidationException
	 */
	public String fortify(String fromCountryName, String toCountryName, int numberOfArmy) throws ValidationException {
		Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
		if (fromCountry == null) {
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", fromCountryName));
		}
		Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
		if (toCountry == null) {
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", toCountryName));
		}
		if (numberOfArmy < 1) {
			throw new ValidationException("Invalid number! Number of armies must be greater than 0.");
		}
		if (fromCountry.getOwner() == null || !fromCountry.getOwner().getName().equals(name)) {
			throw new ValidationException(
					String.format("Country with name \"%s\" does not belong to you!", fromCountryName));
		}
		if (toCountry.getOwner() == null || !toCountry.getOwner().getName().equals(name))
			throw new ValidationException(
					String.format("Country with name \"%s\" does not belong to you!", toCountryName));
		if (!WorldMap.isTherePath(fromCountry, toCountry))
			throw new ValidationException(
					"There is no path between these two countries that is composed of countries that you owns.");

		int realNumberOfArmies = numberOfArmy > fromCountry.getNumberOfArmies() - 1
				? fromCountry.getNumberOfArmies() - 1
				: numberOfArmy;
		fromCountry.removeArmy(realNumberOfArmies);
		toCountry.placeArmy(realNumberOfArmies);
		fortify();
		return String.format("%d army/armies was/were moved from %s to %s.", realNumberOfArmies, fromCountryName,
				toCountryName);
	}

	public ArrayList<String> attack(String fromCountryName, String toCountryName, int numdice, boolean isAllOut)
			throws ValidationException {
		validateCountries(fromCountryName, toCountryName);

		ArrayList<String> log = new ArrayList<String>();
		Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
		Country toCountry = WorldMap.getInstance().getCountry(toCountryName);

		if (!isAllOut) {
			if (numdice > 3) {
				throw new ValidationException(String.format(
						"The Attacker \"%s\" can not roll more than 3 dices. (\"%d\" dice rolled)", getName(),
						numdice));
			}
			if (numdice >= fromCountry.getNumberOfArmies()) {
				throw new ValidationException(String.format(
						"Number of dice rolled by Attacker \"%s\" is \"%d\". It should be less than \"%d\" (the number of armies in \"%s\")",
						getName(), numdice, fromCountry.getNumberOfArmies(), fromCountry.getName()));
			}
			log.add(String.format("%s has attacked %s with %s number of dice(s).", fromCountryName, toCountryName,
					numdice));
		} else {
			log.add(String.format("%s has started an all out attack on %s.", fromCountryName, toCountryName));
		}

		battle = new Battle(fromCountry, toCountry);
		battle.setAllOut(isAllOut);
		battle.setNumberOfAttackerDices(numdice);

		return log;
	}

	private void validateCountries(String fromCountryName, String toCountryName)
			throws ValidationException {
		Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
		Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
		if (fromCountry == null) {
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", fromCountryName));
		}
		if (!this.owns(fromCountryName)) {
			throw new ValidationException(String.format("Country with name \"%s\" is not  onwend by the player \"%s\"!",
					fromCountry.getName(), getName()));
		}

		if (toCountry == null) {
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", toCountryName));
		}
		if (this.owns(toCountryName)) {
			throw new ValidationException("You cannot attack to your countries.");
		}
		if (!fromCountry.isAdjacentTo(toCountryName)) {
			throw new ValidationException(String.format("Country with name \"%s\" is not adjacent to \"%s\"!",
					fromCountryName, toCountryName));
		}

		if (fromCountry.getNumberOfArmies() <= 1) {
			throw new ValidationException("You have not enough army in source country.");
		}
	}

	/**
	 * Performs a defend action of attack phase. It also checks if this player is in
	 * battle and has a country to defend.
	 *
	 * @param numDice Number of dice rolled by the defending player
	 * @return messages for the view
	 */
	public ArrayList<String> defend(int numDice) throws ValidationException {
		Country defendingCountry, attackingCountry;
		ArrayList<String> messages = new ArrayList<>();
		String error = null;

		if (numDice > 2) {
			error = "Defender cannot roll more than 2 dices and not more than the number of armies contained defending country";
		} else if (battle == null /* || battle.getToCountry().getOwner() != this */) {
			error = String.format("% does not have any country under attack.", this.name);
		} else if (numDice > (defendingCountry = battle.getToCountry()).getNumberOfArmies()) {

			error = String.format(
					"Defending country %s has less number of armies %s than the number of dice rolled %s."
							+ " The number of dice cannot be more the number of armies in the defending country.",
					defendingCountry.getName(), defendingCountry.getNumberOfArmies(), numDice);
		} else if (numDice > (attackingCountry = battle.getFromCountry()).getNumberOfArmies()) {

			error = String.format(
					"Attacking country %s has less number of armies %s than the number of dice rolled %s."
							+ " The number of dice cannot be more the number of armies in the attacking country.",
					attackingCountry.getName(), attackingCountry.getNumberOfArmies(), numDice);
		}

		if (error != null) {
			throw new ValidationException(error);
		} else {
			messages.add(String.format("Player %s defended with %d dice(s).", this.name, numDice));
			battle.setNumberOfDefenderDices(numDice);
			String battleMessage = battle.simulateBattle();
			messages.add(battleMessage);
		}

		return messages;
	}

	/**
	 * Move armies after conquering another country during a battle
	 *
	 * @param numberOfArmies
	 * @return
	 */
	public String attackMove(int numberOfArmies) {
		return null;
	}

	/**
	 * @param countryName  name of the country
	 * @param numberOfArmy number of army
	 * @throws ValidationException
	 */
	public String reinforce(String countryName, int numberOfArmy) throws ValidationException {
		Country country = WorldMap.getInstance().getCountry(countryName);
		if (country == null)
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", countryName));
		if (numberOfArmy < 1)
			throw new ValidationException("Invalid number! Number of armies must be greater than zero.");
		if (country.getOwner() == null || !country.getOwner().getName().equals(name))
			throw new ValidationException(
					String.format("Country with name \"%s\" does not belong to you!", countryName));
		int realNumberOfArmies = numberOfArmy > unplacedArmies ? unplacedArmies : numberOfArmy;
		country.placeArmy(realNumberOfArmies);
		minusUnplacedArmies(realNumberOfArmies);
		return String.format("%s placed %d army/armies to %s.", name, realNumberOfArmies, countryName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Player))
			return false;

		Player player = (Player) o;

		return new EqualsBuilder()
				.append(getName(), player.getName())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(getName())
				.toHashCode();
	}

	/**
	 * Add a card randomly to the list of the cards that this player have
	 */
	public void winCard() {
		cards.add(CardType.values()[new SecureRandom().nextInt(CardType.values().length)]);
	}

	/**
	 * Exchange Card
	 *
	 * @param first  position of first card
	 * @param second position of second card
	 * @param third  position of third card
	 * @throws ValidationException validation exception
	 */
	public String exchangeCard(int first, int second, int third) throws ValidationException {
		if (cards.size() < 3) {
			throw new ValidationException(String.format("Dear %s, you need at least three cards to exchange.", name));
		}
		if (first <= 0 || first > cards.size() || second <= 0 || second > cards.size() || third <= 0
				|| third > cards.size() || first == second || first == third || second == third) {
			throw new ValidationException(
					String.format("Card numbers must be unique and positive numbers between 1 and %d", cards.size()));
		}
		CardType firstCard = cards.get(first);
		CardType secondCard = cards.get(second);
		CardType thirdCard = cards.get(third);
		if ((!firstCard.equals(secondCard) || !firstCard.equals(thirdCard) || !secondCard.equals(thirdCard))
				&& (firstCard.equals(secondCard) || firstCard.equals(thirdCard) || secondCard.equals(thirdCard))) {
			throw new ValidationException(
					"A player can exchange a set of three cards of the same kind, or a set of three cards of all different kinds");
		}
		cards.remove(firstCard);
		cards.remove(secondCard);
		cards.remove(thirdCard);
		int numberOfArmiesForExchangeCard = getNumberOfArmiesForExchangeCard();
		unplacedArmies += numberOfArmiesForExchangeCard;
		return String.format("Player %s exchanges %s, %s, %s cards with %d armies.", name, firstCard.getName(),
				secondCard.getName(), thirdCard.getName(), numberOfArmiesForExchangeCard);
	}

	public void cleanPlayerStatus() {
		fortificationFinished = false;
		attackFinished = false;
	}

	public List<CardType> getCards() {
		return cards;
	}

	/**
	 * @param countryName Name of the country that one army be placed on it
	 * @return the result
	 */
	public void placeArmy(String countryName) throws ValidationException {
		if (StringUtils.isBlank(countryName))
			throw new ValidationException("Country name is not valid!");
		Country country = WorldMap.getInstance().getCountry(countryName);
		if (country == null)
			throw new ValidationException(String.format("Country with name \"%s\" was not found!", countryName));
		if (!this.equals(country.getOwner())) {
			throw new ValidationException(
					String.format("Dear %s, Country with name \"%s\" does not belong to you!", name, countryName));
		}
		if (this.unplacedArmies < 1) {
			throw new ValidationException("You Don't have any unplaced army!");
		}
		minusUnplacedArmies(1);
		country.placeOneArmy();
	}

	/**
	 * Card Types
	 */
	public enum CardType {
		INFANTRY("Infantry"),
		CAVALRY("Cavalry"),
		ARTILLERY("Artillery");
		private final String name;

		CardType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * The Builder for {@link Player}
	 */
	public static class Builder {
		private final Player player;

		/**
		 * Player-Builder's constructor has one parameter because a player must have
		 * name.
		 *
		 * @param name The name of a player
		 */
		public Builder(String name) {
			player = new Player(name);
		}

		/**
		 * @return return built player
		 */
		public Player build() {
			return this.player;
		}
	}
}
