package wbb.parser;

import java.util.Map;

import wbb.command.AddCommand;
import wbb.command.AddNewDeadlineCommand;
import wbb.command.AddNewEventCommand;
import wbb.command.AddNewTodoCommand;
import wbb.command.ChangeStatusCommand;
import wbb.command.Command;
import wbb.command.DeleteCommand;
import wbb.command.DisplayTasksCommand;
import wbb.command.ExitCommand;
import wbb.command.FindCommand;
import wbb.command.ListCommand;
import wbb.exception.WBBException;

/**
 * Parse a command.
 */
public class Parser {
    private static final Map<String, AddCommand> ADD_COMMANDS = Map.of(
            "todo", new AddNewTodoCommand(),
            "deadline", new AddNewDeadlineCommand(),
            "event", new AddNewEventCommand()
    );
    private static final Map<String, Command> COMMANDS = Map.of(
            "list", new ListCommand(),
            "bye", new ExitCommand(),
            "mark", new ChangeStatusCommand(),
            "unmark", new ChangeStatusCommand(),
            "todo", new AddCommand(),
            "deadline", new AddCommand(),
            "event", new AddCommand(),
            "delete", new DeleteCommand(),
            "tasks", new DisplayTasksCommand(),
            "find", new FindCommand()
    );
    /**
     * Parses the original command into subclasses of Command.
     * @param inputs The original user command
     * @return The subclass of Command.
     */
    public Command parseCommand(String... inputs) throws WBBException {
        if (inputs.length == 0) {
            throw new WBBException("ERROR: No command specified");
        }
        String commandPrefix = inputs[0].trim().split("\\s+", 2)[0];

        return COMMANDS.entrySet().stream()
                .filter(entry -> entry.getKey().equals(commandPrefix))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new WBBException("ERROR: Invalid command "
                        + "(valid commands are: " + String.join(", ", COMMANDS.keySet()) + ")"));
    }

    /**
     * Parses the add command into subclasses of AddCommand.
     * @param typeOfTask The type of task (e.g. Todo, Deadline, Event).
     * @return The subclass of AddCommand.
     */
    public AddCommand parseAddCommand(String typeOfTask) throws WBBException {
        return ADD_COMMANDS.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(typeOfTask))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new WBBException("Invalid task type: " + typeOfTask));
    }

}
