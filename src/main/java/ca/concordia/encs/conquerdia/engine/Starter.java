package ca.concordia.encs.conquerdia.engine;

import java.util.Scanner;

public class Starter {
    private final ConquerdiaController conquerdia = new ConquerdiaController();

    public static void main(String[] args) {
        new Starter().conquerdia.start(new Scanner(System.in), System.out);
    }
}
