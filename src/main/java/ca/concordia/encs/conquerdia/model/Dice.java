package ca.concordia.encs.conquerdia.model;

import java.security.SecureRandom;

public class Dice {
	/*
	 * The constructor for Dice is private so no new Object can be created
	 */
	private Dice() {
		
	}
	/**
	 * this  method generates a random number between 1-6
	 * @return a random number between  1-6
	 */
	public static int rollDice() {
		SecureRandom rand = new SecureRandom(); 
		return rand.nextInt(5)+1;
	}
}
