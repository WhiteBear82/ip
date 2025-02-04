package wbb.command;

import java.util.ArrayList;

import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

/**
 * List all items in the taskList.
 */
public class ListCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     */
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {
        ui.printList(taskList);
    }
}
