package wbb.command;
import java.util.ArrayList;
import wbb.task.*;
import wbb.task.TaskType;
import wbb.ui.Ui;
import wbb.storage.Storage;
import wbb.exception.WBBException;

public class AddNewEventCommand extends AddCommand {
    /**
     * To validate input and add/save new event task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if missing "/from" or "/to", "/from" comes after "/to", start date, end date, or taskName
     */
    @Override
    public void exec(String taskName, TaskType taskType, ArrayList<Task> taskList, Ui ui, Storage storage) throws WBBException {
        validator.validateFromTo(taskName, taskType);
        String[] taskNameFromTo = validator.validateAndGetTaskNameFromTo(taskName, taskType);
        String[] fromTo = validator.validateAndGetFromTo(taskNameFromTo, taskType);
        Task task = new Event(taskNameFromTo[0].trim(), fromTo[0].trim(), fromTo[1].trim());
        super.addAndSaveTask(task, taskList, ui, storage);
    }
}
