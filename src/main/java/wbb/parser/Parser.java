package wbb.parser;

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
        System.out.println(commandPrefix);
        return switch (commandPrefix) {
        case "list" -> new ListCommand();
        case "bye" -> new ExitCommand();
        case "mark", "unmark" -> new ChangeStatusCommand();
        case "todo", "deadline", "event" -> new AddCommand();
        case "delete" -> new DeleteCommand();
        case "tasks" -> new DisplayTasksCommand();
        case "find" -> new FindCommand();
        default -> throw new WBBException("ERROR: Invalid command "
                + "(valid commands are: list, todo, deadline, event, mark, unmark, delete, tasks, find, bye)");
        };
    }

    /**
     * Parses the add command into subclasses of AddCommand.
     * @param typeOfTask The type of task (e.g. Todo, Deadline, Event).
     * @return The subclass of AddCommand.
     */
    public AddCommand parseAddCommand(String typeOfTask) {
        if (typeOfTask.equalsIgnoreCase("todo")) {
            return new AddNewTodoCommand();
        } else if (typeOfTask.equalsIgnoreCase("deadline")) {
            return new AddNewDeadlineCommand();
        } else {
            return new AddNewEventCommand();
        }
    }
}
