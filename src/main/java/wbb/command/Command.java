package wbb.command;

import java.util.ArrayList;

import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;
import wbb.util.Validator;

/**
 * Represent a user's command.
 */
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
    public abstract void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) throws WBBException;

    /**
     * Indicates whether the command is an Exit command.
     * @return True if the command is an Exit command, otherwise false.
     */
    public boolean isExit() {
        return false;
    }
}
