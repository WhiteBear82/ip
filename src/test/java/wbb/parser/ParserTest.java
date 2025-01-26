package wbb.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wbb.WinterBearBot;
import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;
import wbb.command.*;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void testParseCommand_validCommands() throws WBBException {
        assertInstanceOf(ListCommand.class, parser.parseCommand("list"));
        assertInstanceOf(ExitCommand.class, parser.parseCommand("bye"));
        assertInstanceOf(ChangeStatusCommand.class, parser.parseCommand("mark"));
        assertInstanceOf(ChangeStatusCommand.class, parser.parseCommand("unmark"));
        assertInstanceOf(AddCommand.class, parser.parseCommand("todo"));
        assertInstanceOf(AddCommand.class, parser.parseCommand("deadline"));
        assertInstanceOf(AddCommand.class, parser.parseCommand("event"));
        assertInstanceOf(DeleteCommand.class, parser.parseCommand("delete"));
        assertInstanceOf(DisplayTasksCommand.class, parser.parseCommand("tasks"));
    }

    @Test
    public void testParseCommand_invalidCommand() {
        // Simulate an invalid command that should cause an error message
        WinterBearBot bot = new WinterBearBot();
        ArrayList<Task> taskList = new ArrayList<>();
        bot.ui = new Ui();  // Use real UI for testing
        bot.storage = new Storage();  // Use real storage
        bot.parser = new Parser();

        String invalidCommand = "invalid";
        assertThrows(WBBException.class, () -> {
            Command invalidCommandObj = bot.parser.parseCommand(invalidCommand);
            invalidCommandObj.execute(taskList, invalidCommand, bot.ui, bot.storage);
        });
    }

    @Test
    public void testParseAddCommand_validTaskTypes() {
        assertInstanceOf(AddNewTodoCommand.class, parser.parseAddCommand("todo"));
        assertInstanceOf(AddNewDeadlineCommand.class, parser.parseAddCommand("deadline"));
        assertInstanceOf(AddNewEventCommand.class, parser.parseAddCommand("event"));
    }

    @Test
    public void testParseAddCommand_invalidTaskType() {
        assertInstanceOf(AddNewEventCommand.class, parser.parseAddCommand("unknown"));
    }
}
