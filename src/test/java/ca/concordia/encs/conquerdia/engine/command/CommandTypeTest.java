package ca.concordia.encs.conquerdia.engine.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandTypeTest {

    @Test
    public void findCommandTypeByName() {
        assertEquals(CommandType.findCommandTypeByName("editcontinent"), CommandType.EDIT_CONTINENT);
    }

    @Test
    public void getFactory() {
        assertTrue(CommandType.EDIT_CONTINENT.getFactory() instanceof EditContinentCommandFactory);
    }
}