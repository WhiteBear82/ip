package wbb.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wbb.WinterBearBot;
import wbb.command.AddCommand;
import wbb.command.AddNewDeadlineCommand;
import wbb.command.AddNewEventCommand;
import wbb.command.AddNewTodoCommand;
import wbb.command.ChangeStatusCommand;
import wbb.command.Command;
import wbb.command.DeleteCommand;
import wbb.command.DisplayTasksCommand;
import wbb.command.ExitCommand;
import wbb.command.ListCommand;
import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

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
        bot.setUi(new Ui()); // Use real UI for testing
        bot.setStorage(new Storage()); // Use real storage
        bot.setParser(new Parser());

        String invalidCommand = "invalid";
        assertThrows(WBBException.class, () -> {
            Command invalidCommandObj = bot.getParser().parseCommand(invalidCommand);
            invalidCommandObj.execute(taskList, invalidCommand, bot.getUi(), bot.getStorage());
        });
    }

    @Test
    public void testParseAddCommand_validTaskTypes() throws WBBException {
        assertInstanceOf(AddNewTodoCommand.class, parser.parseAddCommand("todo"));
        assertInstanceOf(AddNewDeadlineCommand.class, parser.parseAddCommand("deadline"));
        assertInstanceOf(AddNewEventCommand.class, parser.parseAddCommand("event"));
    }

    @Test
    public void testParseAddCommand_invalidTaskType() throws WBBException {
        assertInstanceOf(AddNewEventCommand.class, parser.parseAddCommand("unknown"));
    }
}
