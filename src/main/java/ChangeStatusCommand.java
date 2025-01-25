import java.util.ArrayList;

public class ChangeStatusCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     * @throws WBBException if the command cannot be executed correctly.
     */
    protected void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
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
            ui.prettyPrint("\tNice! I've marked this task as done:\n\t\t" + taskName);
        }
        else {
            taskName.setUndone();
            ui.prettyPrint("\tOK, I've marked this task as not done yet:\n\t\t" + taskName);
        }
        storage.saveTasks(taskList);
    }
}
