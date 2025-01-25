package wbb.parser;
import wbb.ui.Ui;
import wbb.command.*;
import wbb.exception.WBBException;

public class Parser {
    /**
     * Parses the original command into subclasses of Command.
     * @param command The original user command
     * @param ui The ui instance.
     * @return The subclass of Command.
     */
    public Command parseCommand(String command, Ui ui) {
        String commandPrefix = command.split(" ")[0];
        try {
            return switch (commandPrefix) {
                case "list" -> new ListCommand();
                case "bye" -> new ExitCommand();
                case "mark", "unmark" -> new ChangeStatusCommand();
                case "todo", "deadline", "event" -> new AddCommand();
                case "delete" -> new DeleteCommand();
                case "tasks" -> new DisplayTasksCommand();
                default -> throw new WBBException("\tERROR: Invalid command (valid commands are: list, todo, deadline, event, mark, unmark, delete, tasks, bye)");
            };
        } catch (WBBException e) {
            ui.prettyPrint(e.getMessage());
        }
        return null; // Should not reach here
    }

    /**
     * Parses the add command into subclasses of AddCommand.
     * @param typeOfTask The type of task.
     * @return The subclass of AddCommand.
     */
    public AddCommand parseAddCommand(String typeOfTask) {
        if (typeOfTask.equalsIgnoreCase("todo"))
            return new AddNewTodoCommand();
        else if (typeOfTask.equalsIgnoreCase("deadline"))
            return new AddNewDeadlineCommand();
        else
            return new AddNewEventCommand();
    }
}
