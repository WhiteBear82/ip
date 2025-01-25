import java.util.ArrayList;

public class ExitCommand extends Command {
    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     */
    protected void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {}

    /**
     * Indicates whether the command is an Exit command.
     * @return True if the command is an Exit command, otherwise false.
     */
    @Override
    protected boolean isExit() {
        return true;
    }
}
