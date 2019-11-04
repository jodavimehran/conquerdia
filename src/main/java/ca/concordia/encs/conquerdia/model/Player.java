package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.exception.ValidationException;
import ca.concordia.encs.conquerdia.model.map.Continent;
import ca.concordia.encs.conquerdia.model.map.Country;
import ca.concordia.encs.conquerdia.model.map.WorldMap;
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
     * specified collection
     */
    public boolean ownedAll(Set<String> countries) {
        return this.countries.keySet().containsAll(countries);
    }

    /**
     * This  Method return checks if the player owns the specified country.
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
     * any country.
     */
    public int getUnplacedArmies() {
        return unplacedArmies;
    }

    /**
     * @param fromCountryName source country
     * @param toCountryName   destination country
     * @param numberOfArmy    number of army
     * @return the result
     */
    public String fortify(String fromCountryName, String toCountryName, int numberOfArmy) {
        Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
        if (fromCountry == null)
            return String.format("Country with name \"%s\" was not found!", fromCountryName);
        Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
        if (toCountry == null)
            return String.format("Country with name \"%s\" was not found!", toCountryName);
        if (numberOfArmy < 1)
            return "Invalid number! Number of armies must be greater than 0.";

        if (fromCountry.getOwner() == null || !fromCountry.getOwner().getName().equals(name))
            return String.format("Country with name \"%s\" does not belong to you!", fromCountryName);

        if (toCountry.getOwner() == null || !toCountry.getOwner().getName().equals(name))
            return String.format("Country with name \"%s\" does not belong to you!", toCountryName);

        if (!WorldMap.isTherePath(fromCountry, toCountry))
            return "There is no path between these two countries that is composed of countries that you owns.";
        int realNumberOfArmies = numberOfArmy > fromCountry.getNumberOfArmies() - 1
                ? fromCountry.getNumberOfArmies() - 1
                : numberOfArmy;
        fromCountry.removeArmy(realNumberOfArmies);
        toCountry.placeArmy(realNumberOfArmies);

        return String.format("%d army/armies was/were moved from %s to %s.", realNumberOfArmies, fromCountryName,
                toCountryName);
    }

    public String attack(String fromCountryName, String toCountryName, int numdice, String allOut,
                         String noAttack) {
        Country fromCountry = WorldMap.getInstance().getCountry(fromCountryName);
        if (fromCountry == null)
            return String.format("Country with name \"%s\" was not found!", fromCountryName);
        Country toCountry = WorldMap.getInstance().getCountry(toCountryName);
        if (toCountry == null)
            return String.format("Country with name \"%s\" was not found!", toCountryName);
        /*Rule1: (Country Selection Validation):
         *The player may choose one of the countries he owns( that contains two or more armies)
         *and declare an attack on an adjacent country that is owned by another player.*/
        if (fromCountry != null && toCountry != null) {
            if (this.owns(fromCountry.getName())) {
                if (fromCountry.getNumberOfArmies() >= 2) {
                    if (numdice <= 3) {
                        if (numdice < fromCountry.getNumberOfArmies()) {
                            toCountry.setAttackDeclared();
                            //Rule 2:(Battle) A battle is then simulated by the attacker rolling at most 3 dice
                            //	(which should not be more than the number of armies contained in the attacking country)
                            simulateAttack(fromCountry, toCountry);
                        } else {
                            return String.format("Number of dice rolled by Arracker \"%s\" : is \"%d\". It should be less than \"%d\" (the number of armies in \"%s\")", getName(), Integer.valueOf(numdice), fromCountry.getNumberOfArmies(), fromCountry.getName());
                        }
                    } else {
                        return String.format("The Attacker \"%s\"  can not roll more than 3 dices. (\"%d\" dice rolled)", getName(), Integer.valueOf(numdice));
                    }
                }
            } else {
                return String.format("Country with name \"%s\" is not  onwend by the player \"%s\"!", fromCountry.getName(), getName());
            }
        }
        return null;
    }

    public void simulateAttack(Country fromCountry, Country toCountry) {

    }

    public String defend(int numDice) {

        //defender should choose the number of dice to defend with if attack is declared on a country

        if (numDice > 2 /* || numDice > army attacking */) {
            return "Defender cannot roll more than 2 dices or more than the number of armies contained in the attacking country";
        }
        return "TODO Defend status?";
    }

    public String attackMove() {
        return null;
    }

    /**
     * @param countryName  name of the country
     * @param numberOfArmy number of army
     * @throws ValidationException
     */
    public void reinforce(String countryName, int numberOfArmy) throws ValidationException {
        Country country = WorldMap.getInstance().getCountry(countryName);
        if (country == null)
            throw new ValidationException(String.format("Country with name \"%s\" was not found!", countryName));
        if (numberOfArmy < 1)
            throw new ValidationException("Invalid number! Number of armies must be greater than zero.");
        if (country.getOwner() == null || !country.getOwner().getName().equals(name))
            throw new ValidationException(String.format("Country with name \"%s\" does not belong to you!", countryName));
        StringBuilder sb = new StringBuilder();
        int realNumberOfArmies = numberOfArmy > unplacedArmies ? unplacedArmies : numberOfArmy;
        country.placeArmy(realNumberOfArmies);
        minusUnplacedArmies(realNumberOfArmies);
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
    private void winCard() {
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
    private void exchangeCard(int first, int second, int third) throws ValidationException {
        if (cards.size() < 3) {
            throw new ValidationException(String.format("Dear %s, you need at least three cards to exchange.", name));
        }
        if (first <= 0 || first > cards.size() || second <= 0 || second > cards.size() || third <= 0 || third > cards.size() || first == second || first == third || second == third) {
            throw new ValidationException(String.format("Card numbers must be unique and positive numbers between 1 and %d", cards.size()));
        }
        CardType firstCard = cards.get(first);
        CardType secondCard = cards.get(second);
        CardType thirdCard = cards.get(third);
        if ((!firstCard.equals(secondCard) || !firstCard.equals(thirdCard) || !secondCard.equals(thirdCard)) && (firstCard.equals(secondCard) || firstCard.equals(thirdCard) || secondCard.equals(thirdCard))) {
            throw new ValidationException("A player can exchange a set of three cards of the same kind, or a set of three cards of all different kinds");
        }
        cards.remove(firstCard);
        cards.remove(secondCard);
        cards.remove(thirdCard);
        unplacedArmies += getNumberOfArmiesForExchangeCard();
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
