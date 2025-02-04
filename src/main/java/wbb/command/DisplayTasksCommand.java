package wbb.command;

import java.util.ArrayList;

import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

/**
 * List all items in the taskList that are due today.
 */
public class DisplayTasksCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     */
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {
        displayTodayTasks(taskList, ui);
    }

    /**
     * Displays all tasks that are due today.
     * @param taskList The taskList.
     */
    public void displayTodayTasks(ArrayList<Task> taskList, Ui ui) {
        ArrayList<Task> tasksDueToday = getTasksDueToday(taskList);
        ui.printTasksDueToday(tasksDueToday);
    }

    /**
     * Retrieves tasks that are due today.
     * @param tasks The taskList.
     * @return The task that is due today.
     */
    public ArrayList<Task> getTasksDueToday(ArrayList<Task> tasks) {
        ArrayList<Task> tasksDueToday = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDueToday()) {
                tasksDueToday.add(task);
            }
        }
        return tasksDueToday;
    }
}
