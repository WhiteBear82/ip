import java.util.ArrayList;

public class ListCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     */
    protected void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {
        ui.printList(taskList);
    }
}
