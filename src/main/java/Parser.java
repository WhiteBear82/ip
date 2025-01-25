public class Parser {
    protected Command parseCommand(String command, Ui ui) {
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

    protected AddCommand parseAddCommand(String typeOfTask) {
        if (typeOfTask.equalsIgnoreCase("todo"))
            return new AddNewTodoCommand();
        else if (typeOfTask.equalsIgnoreCase("deadline"))
            return new AddNewDeadlineCommand();
        else
            return new AddNewEventCommand();
    }
}
