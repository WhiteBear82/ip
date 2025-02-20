package wbb.command;

import java.util.ArrayList;

import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

/**
 * Mark or unmark a task.
 */
public class ChangeStatusCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     * @throws WBBException if the command cannot be executed correctly.
     */
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
        changeItemStatus(taskList, command, ui, storage);
    }

    /**
     * To change item status to either "Done" or "Undone".
     * @param taskList The taskList.
     * @param command The input command by the user.
     */
    public void changeItemStatus(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
        // Validate itemIdx and taskList before continuing
        int itemIdx = validator.validateAndGetItemIdx(command);
        validator.validateItemIdxForTaskList(taskList, itemIdx, ui);

        // Validation passed, continue running main program logic (i.e. to change the item status)
        Task taskName = taskList.get(itemIdx);
        if (command.startsWith("mark")) {
            taskName.setDone();
            ui.prettyPrint("Nice! I've marked this task as done:\n\t" + taskName);
        } else {
            taskName.setUndone();
            ui.prettyPrint("OK, I've marked this task as not done yet:\n\t" + taskName);
        }
        storage.saveTasks(taskList);
    }
}
