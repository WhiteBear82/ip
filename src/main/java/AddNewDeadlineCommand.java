import java.util.ArrayList;

public class AddNewDeadlineCommand extends AddCommand {
    /**
     * To validate input and add/save new deadline task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if missing "/by", deadline or taskName.
     */
    @Override
    protected void exec(String taskName, TaskType taskType, ArrayList<Task> taskList, Ui ui, Storage storage) throws WBBException {
        validator.validateTaskNameBy(taskName, taskType);
        String[] taskNameBy = validator.validateAndGetTaskNameBy(taskName, taskType);
        String deadline = new DateTimeUtility().tryParseDateAndOrTime(taskNameBy[1].trim());
        Task task = new Deadline(taskNameBy[0].trim(), deadline);
        super.addAndSaveTask(task, taskList, ui, storage);
    }
}
