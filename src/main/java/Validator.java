import java.util.ArrayList;

public class Validator {
    /**
     * To retrieve, validate and return the item (task) index.
     * @param command The user command.
     * @return The item index.
     * @throws WBBException if missing index, non-positive index, or non-integer index.
     */
    protected int validateAndGetItemIdx(String command) throws WBBException {
        String[] commandItemIdx = command.split(" ");
        if (commandItemIdx.length <= 1)
            throw new WBBException("\tERROR: Missing item index.\n\tPlease provide a valid index (e.g. mark 2 / unmark 2)");

        int itemIdx;
        try {
            itemIdx = Integer.parseInt(commandItemIdx[1]) - 1; // -1 for array indexing
        } catch (NumberFormatException e) {
            throw new WBBException("\tERROR: Index must be integers only (e.g. 1, 2, ...)");
        }

        if (itemIdx <= -1)  // Potential error input: "mark 0"
            throw new WBBException("\tERROR: Index must be greater than or equal to 1");
        return itemIdx;
    }

    /**
     * To validate if item index supplied is out of the task list range, or verify if the task list is empty.
     * @param taskList The taskList.
     * @param itemIdx The itemIdx.
     * @throws WBBException if index is out of bounds, or task list is empty.
     */
    protected void validateItemIdxForTaskList(ArrayList<Task> taskList, int itemIdx, Ui ui) throws WBBException {
        if (taskList.isEmpty())
            throw new WBBException("\tERROR: Task list is empty. Nothing to do.");
        if (itemIdx >= taskList.size()) {  // Potential error input: "mark 100" when list only has 1 element
            ui.printList(taskList);
            throw new WBBException("\tERROR: Invalid item index.\n\tItem index out of bounds. Please select a valid index in the list above");
        }
    }

    /**
     * To validate input and return task name from the user command.
     * @param command The user command.
     * @param typeOfTask The type of task (e.g. todo, deadline, or event).
     * @param taskType The type of task (Enum).
     * @return The taskName, if valid.
     * @throws WBBException if taskName is empty.
     */
    protected String validateAndGetTaskName(String command, String typeOfTask, TaskType taskType) throws WBBException {
        String taskName = command.substring(typeOfTask.length()).trim();
        if (taskName.isEmpty())
            throw new WBBException("\tERROR: Missing task name\n\t" + taskType.getFormatExample());
        return taskName;
    }

    /**
     * To validate taskName if it contains "/by".
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if taskName does not contain "/by".
     */
    protected void validateTaskNameBy(String taskName, TaskType taskType) throws WBBException {
        if (!taskName.contains("/by")) // Potential error input: "deadline borrow book"
            throw new WBBException("\tERROR: Missing '/by'\n\t" + taskType.getFormatExample());
    }

    /**
     * To validate taskName and deadline and return both if valid.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return taskNameBy, which contains taskName and deadline, if valid.
     * @throws WBBException if missing deadline or task name.
     */
    protected String[] validateAndGetTaskNameBy(String taskName, TaskType taskType) throws WBBException {
        String[] taskNameBy = taskName.split("/by");
        if (taskNameBy.length != 2)
            throw new WBBException("\tERROR: Missing deadline \n\t" + taskType.getFormatExample());
        if (taskNameBy[0].trim().isEmpty())  // Potential error input: "deadline /by 2"
            throw new WBBException(("\tERROR: Missing task name\n\t" + taskType.getFormatExample()));
        return taskNameBy;
    }

    /**
     * To validate "/from" and "/to" are present and in correct positions.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if either "/from" or "/to" is missing, or "/from" comes after "/to".
     */
    protected void validateFromTo(String taskName, TaskType taskType) throws WBBException {
        if (!taskName.contains("/from") || !taskName.contains("/to"))
            throw new WBBException("\tERROR: Missing '/from' or '/to'\n\t" + taskType.getFormatExample());
        if (taskName.indexOf("/from") > taskName.indexOf("/to"))
            throw new WBBException("\tERROR: /from must come before /to\n\t" + taskType.getFormatExample());
    }

    /**
     * To further validate input and return taskName, start datetime, and end datetime.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return taskName, start datetime, and end datetime, together in a String "taskNameFromTo".
     * @throws WBBException if missing start date or taskName.
     */
    protected String[] validateAndGetTaskNameFromTo(String taskName, TaskType taskType) throws WBBException {
        String[] taskNameFromTo = taskName.split("/from");
        if (taskNameFromTo.length != 2)  // Potential error input: "event borrow /from"
            throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
        else if (taskNameFromTo[0].trim().isEmpty())  // Potential error input: "event /from /to 2pm"
            throw new WBBException("\tERROR: Missing task name \n\t" + taskType.getFormatExample());
        else
        if ((taskNameFromTo[1].trim().split("/to")).length == 0)  // Potential error input: "event borrow /from /to"
            throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
        else if (taskNameFromTo[1].trim().split("/to")[0].isEmpty())  // Potential error input: "event borrow /from /to 2pm"
            throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
        return taskNameFromTo;
    }

    /**
     * To further validate input and return start datetime and end datetime.
     * @param taskNameFromTo The taskName, start datetime and end datetime in one String "taskNameFromTo"
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return start datetime and end datetime in one String "fromTo"
     * @throws WBBException if missing end date.
     */
    protected String[] validateAndGetFromTo(String[] taskNameFromTo, TaskType taskType) throws WBBException {
        String[] fromTo = taskNameFromTo[1].split("/to");
        if (fromTo.length != 2)
            throw new WBBException("\tERROR: Missing end date \n\t" + taskType.getFormatExample());  // Potential error input: "event borrow /from 2pm /to"
        return fromTo;
    }
}
