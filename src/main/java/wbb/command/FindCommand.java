package wbb.command;

import java.util.ArrayList;

import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

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
        ArrayList<Task> tasksDueToday = getMatchingTasks(command.split(" ")[1].trim(), taskList);
        ui.printMatchingTasks(tasksDueToday);
    }

    /**
     * Retrieves tasks that are matched by the "find" command.
     * @param taskList The taskList.
     * @return The tasks that are matched by the "find" command.
     */
    public ArrayList<Task> getMatchingTasks(String taskName, ArrayList<Task> taskList) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : taskList)
            if (task.getDescription().toLowerCase().contains(taskName))
                matchingTasks.add(task);
        return matchingTasks;
    }
}
