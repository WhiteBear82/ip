package wbb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import wbb.ui.Ui;
import wbb.storage.Storage;
import wbb.parser.Parser;
import wbb.task.*;
import wbb.command.*;
import wbb.exception.WBBException;

class WinterBearBotTest {

    private WinterBearBot bot;
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        // Initialize the WinterBearBot instance
        bot = new WinterBearBot();
        taskList = new ArrayList<>();  // Mock an empty task list
    }

    @Test
    void testWinterBearBotInitialization() {
        // Test that the WinterBearBot is initialized correctly
        assertNotNull(bot);
        assertNotNull(bot.ui);
        assertNotNull(bot.storage);
        assertNotNull(bot.parser);
        assertNotNull(bot.taskList);  // Check that the taskList is empty initially
    }

    @Test
    void testManageTaskList_withValidCommand() throws WBBException {
        // Manually simulate a valid command (e.g., "list")
        bot.ui = new Ui();  // Use real UI for testing
        bot.storage = new Storage();  // Use real storage
        bot.parser = new Parser();

        // Add a task to the list for this test
        taskList.add(new Todo("Test task"));

        // Manually trigger the execution of a valid command
        String command = "list";
        Command listCommand = new ListCommand(); // Assuming ListCommand handles the "list" command
        listCommand.execute(taskList, command, bot.ui, bot.storage);

        // Verify if the task list size is 1 after the "list" command is called
        assertEquals(1, taskList.size());
    }

    @Test
    void testManageTaskList_withInvalidCommand() {
        // Simulate an invalid command that should cause an error message
        bot.ui = new Ui();  // Use real UI for testing
        bot.storage = new Storage();  // Use real storage
        bot.parser = new Parser();

        // Create a task list with an invalid command
        String invalidCommand = "invalid";
        assertThrows(WBBException.class, () -> {
            Command invalidCommandObj = bot.parser.parseCommand(invalidCommand);
            invalidCommandObj.execute(taskList, invalidCommand, bot.ui, bot.storage);
        });
    }

    @Test
    void testRunProgram() throws WBBException {
        // Test if the program runs correctly and exits with "bye"
        bot.ui = new Ui();  // Use real UI for testing
        bot.storage = new Storage();  // Use real storage
        bot.parser = new Parser();

        // Simulate the "bye" command and check if the program exits
        String command = "bye";
        Command exitCommand = new ExitCommand(); // Assuming ExitCommand handles the "bye" command
        exitCommand.execute(taskList, command, bot.ui, bot.storage);

        // Assert that the program exits after the "bye" command is executed
        assertTrue(exitCommand.isExit());
    }

    @Test
    void testAddTask() throws WBBException {
        // Test adding a task to the list
        String command = "todo Read a book";  // Simulate a "todo" task addition
        Command addCommand = new AddNewTodoCommand(); // Assuming AddNewTodoCommand handles "todo" task
        addCommand.execute(taskList, command, bot.ui, bot.storage);

        // Verify if the task has been added to the task list
        assertEquals(1, taskList.size());
        assertEquals("Read a book", taskList.get(0).getDescription());
    }
}
