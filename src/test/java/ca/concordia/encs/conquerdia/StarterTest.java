package ca.concordia.encs.conquerdia;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class StarterTest {


    @Test
    public void tournamentTest() {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            StringBuilder input = new StringBuilder();
            input.append("tournament -M risk Alberta Montreal -P aggressive random -G 5 -D 50").append(System.getProperty("line.separator")).append("exit");
            InputStream in = new ByteArrayInputStream(input.toString().getBytes());
            new Starter().start(new Scanner(in), ps);
            String output = os.toString("UTF8");
            Assert.assertTrue(output.contains("Tournament Finished Successfully."));
        } catch (UnsupportedEncodingException e) {

        }
    }
}
