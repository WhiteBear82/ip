import java.util.ArrayList;

public abstract class Command {
    protected Validator validator;
    public Command() {
        this.validator = new Validator();
    }

    /**
     * Executes a given command.
     * @param taskList The taskList.
     * @param command The user command.
     * @param ui The ui.
     * @param storage The storage.
     * @throws WBBException if the command cannot be executed correctly.
     */
    protected abstract void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException;

    /**
     * Indicates whether the command is an Exit command.
     * @return True if the command is an Exit command, otherwise false.
     */
    protected boolean isExit() {
        return false;
    }
}
