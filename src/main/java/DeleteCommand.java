import java.util.ArrayList;

public class DeleteCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     * @throws WBBException if the command cannot be executed correctly.
     */
    protected void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
        deleteTask(taskList, command, ui, storage);
    }

    /**
     * To delete tasks.
     * @param taskList The taskList
     * @param command The user command (e.g. delete 3)
     * @throws WBBException if itemIdx is invalid (non-positive, non-integer, out of bounds) or taskList is empty.
     */
    public void deleteTask(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException {
        // Validate itemIdx and taskList before continuing
        int itemIdx = validator.validateAndGetItemIdx(command);
        validator.validateItemIdxForTaskList(taskList, itemIdx, ui);

        // Validation passed, continue running main program logic (i.e. to delete the item)
        Task taskName = taskList.get(itemIdx);
        taskList.remove(itemIdx);
        ui.printDeleteSuccessfulMsg(taskList.size(), taskName);
        storage.saveTasks(taskList);
    }
}
