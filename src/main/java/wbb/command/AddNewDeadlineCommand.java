package wbb.command;

import java.util.ArrayList;

import wbb.exception.WBBException;
import wbb.storage.Storage;
import wbb.task.Deadline;
import wbb.task.Task;
import wbb.task.TaskType;
import wbb.ui.Ui;
import wbb.util.DateTimeUtility;

/**
 * Class for user to Add a new deadline
 */
public class AddNewDeadlineCommand extends AddCommand {
    /**
     * To validate input and add/save new deadline task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if missing "/by", deadline or taskName.
     */
    @Override
    public void exec(String taskName, TaskType taskType,
                     ArrayList<Task> taskList, Ui ui, Storage storage) throws WBBException {
        validator.validateTaskNameBy(taskName, taskType);
        String[] taskNameBy = validator.validateAndGetTaskNameBy(taskName, taskType);
        String deadline = new DateTimeUtility().tryParseDateAndOrTime(taskNameBy[1].trim());
        Task task = new Deadline(taskNameBy[0].trim(), deadline);
        super.addAndSaveTask(task, taskList, ui, storage);
    }
}
