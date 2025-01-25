package wbb.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UiTest {

    @Test
    public void testPrettyPrint() {
        Ui ui = new Ui();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String message = "Test Message";
        ui.prettyPrint(message);

        String expectedOutput = Ui.HORIZONTAL_LINE + message + "\n" + Ui.HORIZONTAL_LINE;
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    public void testDisplayWelcomeMessage() {
        Ui ui = new Ui();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ui.displayWelcomeMessage();

        String expectedOutput = "\tHello! I'm WinterBearBot\n\tWhat can I do for you?";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }
}
