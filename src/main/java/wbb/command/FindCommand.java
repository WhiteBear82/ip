package wbb.command;

import java.util.ArrayList;
import java.util.stream.Collectors;

import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

/**
 * Find an item in the taskList.
 */
public class FindCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     */
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {
        displayMatchingTasks(taskList, command, ui);
    }

    /**
     * Displays all tasks that are matched by the "find" command.
     * @param taskList The taskList.
     */
    public void displayMatchingTasks(ArrayList<Task> taskList, String command, Ui ui) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            ui.printErrorMsg("Please enter a keyword to search for tasks.");
            return;
        }

        ArrayList<Task> matchingTasks = getMatchingTasks(parts[1].trim(), taskList);
        ui.printMatchingTasks(matchingTasks);
    }

    /**
     * Retrieves tasks that are matched by the "find" command.
     * @param taskList The taskList.
     * @return The tasks that are matched by the "find" command.
     */
    public ArrayList<Task> getMatchingTasks(String taskName, ArrayList<Task> taskList) {
        return taskList.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(taskName.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
