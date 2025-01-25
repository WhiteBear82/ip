package wbb.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.*;
import wbb.ui.Ui;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    private ArrayList<Task> taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
        ui = new Ui(); // Assume Ui has basic functionalities for printing
        storage = new Storage(); // Mock or test file path
    }

    @Test
    void testAddTodoCommand() {
        AddCommand command = new AddNewTodoCommand();
        String input = "todo Read a book";

        assertDoesNotThrow(() -> command.execute(taskList, input, ui, storage));
        assertEquals(1, taskList.size());
        assertEquals("Read a book", taskList.get(0).getDescription());
    }

    @Test
    void testAddDeadlineCommand() {
        AddCommand command = new AddNewDeadlineCommand();
        String input = "deadline Submit assignment /by 2025-01-30";

        assertDoesNotThrow(() -> command.execute(taskList, input, ui, storage));
        assertEquals(1, taskList.size());
        assertEquals("Submit assignment", taskList.get(0).getDescription());
        assertEquals("2025-01-30", ((Deadline) taskList.get(0)).getBy());
    }

    @Test
    void testAddEventCommand() {
        AddCommand command = new AddNewEventCommand();
        String input = "event Project meeting /from 2025-01-30 /to 2025-01-31";

        assertDoesNotThrow(() -> command.execute(taskList, input, ui, storage));
        assertEquals(1, taskList.size());
        assertEquals("Project meeting", taskList.get(0).getDescription());
    }

    @Test
    void testChangeStatusCommand() {
        ChangeStatusCommand command = new ChangeStatusCommand();
        taskList.add(new Todo("Complete homework"));
        taskList.get(0).setDone();

        String input = "unmark 1";

        assertDoesNotThrow(() -> command.execute(taskList, input, ui, storage));
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void testDeleteCommand() {
        DeleteCommand command = new DeleteCommand();
        taskList.add(new Todo("Clean the house"));

        String input = "delete 1";

        assertDoesNotThrow(() -> command.execute(taskList, input, ui, storage));
        assertTrue(taskList.isEmpty());
    }

    @Test
    void testListCommand() {
        ListCommand command = new ListCommand();
        taskList.add(new Todo("Buy groceries"));

        assertDoesNotThrow(() -> command.execute(taskList, "", ui, storage));
        assertEquals(1, taskList.size());
    }

    @Test
    void testDisplayTasksCommand() {
        DisplayTasksCommand command = new DisplayTasksCommand();
        taskList.add(new Deadline("Exercise", "2025-01-25")); // Assuming isDueToday uses the date

        assertDoesNotThrow(() -> command.execute(taskList, "", ui, storage));
        assertEquals(1, taskList.size());
    }

    @Test
    void testExitCommand() {
        ExitCommand command = new ExitCommand();

        assertDoesNotThrow(() -> command.execute(taskList, "", ui, storage));
        assertTrue(command.isExit());
    }

    @Test
    void testInvalidAddCommand() {
        AddCommand command = new AddNewTodoCommand();
        String input = "todo"; // Missing task description

        assertThrows(WBBException.class, () -> command.execute(taskList, input, ui, storage));
    }

    @Test
    void testInvalidDeleteCommand() {
        DeleteCommand command = new DeleteCommand();
        String input = "delete 5"; // Index out of bounds

        assertThrows(WBBException.class, () -> command.execute(taskList, input, ui, storage));
    }
}
