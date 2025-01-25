package wbb.command;
import java.util.ArrayList;
import wbb.exception.WBBException;
import wbb.task.Task;
import wbb.task.TaskType;
import wbb.ui.Ui;
import wbb.storage.Storage;
import wbb.parser.Parser;

public class AddCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     * @throws WBBException if the command cannot be executed correctly.
     */
    @Override
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
        addTask(taskList, command, ui, storage);
    }

    /**
     * To add task into the list.
     * Add item to list - Either "todo", "deadline" or "event".
     * Format "todo": todo <taskName>
     * Format "deadline": deadline <taskName> /by <when>
     * Format "event": event <taskName> /from <start> /to <end>
     * @param list The taskList.
     * @param command The input command by the user.
     */
    public void addTask(ArrayList<Task> list, String command, Ui ui, Storage storage) throws WBBException {
        String typeOfTask = command.split(" ")[0];  // "todo", "deadline", "event"
        TaskType taskType = TaskType.fromString(typeOfTask);  // enum of typeOfTask
        String taskName = validator.validateAndGetTaskName(command, typeOfTask, taskType);  // name of task

        AddCommand addCommand = new Parser().parseAddCommand(typeOfTask);
        addCommand.exec(taskName, taskType, list, ui, storage);
    }

    /**
     * To add task into list and save into storage.
     * @param task The task.
     * @param taskList The taskList.
     * @param ui The ui instance.
     * @param storage The storage instance.
     */
    protected void addAndSaveTask(Task task, ArrayList<Task> taskList, Ui ui, Storage storage) {
        taskList.add(task);
        storage.saveTasks(taskList);
        ui.printAdditionSuccessfulMsg(taskList.size(), task);
    }

    /**
     * Method for its subclasses to execute.
     * @param taskName The taskName.
     * @param taskType The taskType.
     * @param taskList The taskList.
     * @param ui The ui instance.
     * @param storage The storage.
     * @throws WBBException If it does not perform as expected.
     */
    protected void exec(String taskName, TaskType taskType, ArrayList<Task> taskList, Ui ui, Storage storage) throws WBBException {}
}
