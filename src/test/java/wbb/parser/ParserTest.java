package wbb.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wbb.ui.Ui;
import wbb.command.*;

public class ParserTest {

    private Parser parser;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
        ui = new Ui();
    }

    @Test
    public void testParseCommand_validCommands() {
        assertInstanceOf(ListCommand.class, parser.parseCommand("list", ui));
        assertInstanceOf(ExitCommand.class, parser.parseCommand("bye", ui));
        assertInstanceOf(ChangeStatusCommand.class, parser.parseCommand("mark", ui));
        assertInstanceOf(ChangeStatusCommand.class, parser.parseCommand("unmark", ui));
        assertInstanceOf(AddCommand.class, parser.parseCommand("todo", ui));
        assertInstanceOf(AddCommand.class, parser.parseCommand("deadline", ui));
        assertInstanceOf(AddCommand.class, parser.parseCommand("event", ui));
        assertInstanceOf(DeleteCommand.class, parser.parseCommand("delete", ui));
        assertInstanceOf(DisplayTasksCommand.class, parser.parseCommand("tasks", ui));
    }

    @Test
    public void testParseCommand_invalidCommand() {
        String invalidCommand = "invalid";
        assertNull(parser.parseCommand(invalidCommand, ui));
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
