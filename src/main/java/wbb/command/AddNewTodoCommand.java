package wbb.command;
import java.util.ArrayList;
import wbb.task.*;
import wbb.task.TaskType;
import wbb.ui.Ui;
import wbb.storage.Storage;

public class AddNewTodoCommand extends AddCommand {
    /**
     * To validate input and add/save new todo task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     */
    @Override
    public void exec(String taskName, TaskType taskType, ArrayList<Task> taskList, Ui ui, Storage storage) {
        Task task = new Todo(taskName);
        super.addAndSaveTask(task, taskList, ui, storage);
    }
}
