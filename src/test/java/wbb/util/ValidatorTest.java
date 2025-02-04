package wbb.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import wbb.exception.WBBException;
import wbb.task.Task;
import wbb.task.TaskType;
import wbb.task.Todo;
import wbb.ui.Ui;

class ValidatorTest {

    private final Validator validator = new Validator();
    private final Ui ui = new Ui();

    @Test
    void testValidateAndGetItemIdxValidIndex() throws WBBException {
        String command = "mark 3";
        int result = validator.validateAndGetItemIdx(command);
        assertEquals(2, result); // Should return 2 as 3 - 1 = 2
    }

    @Test
    void testValidateAndGetItemIdxMissingIndex() {
        String command = "mark";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Missing item index"));
    }

    @Test
    void testValidateAndGetItemIdxNonIntegerIndex() {
        String command = "mark two";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Index must be integers only"));
    }

    @Test
    void testValidateAndGetItemIdxNonPositiveIndex() {
        String command = "mark 0";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Index must be greater than or equal to 1"));
    }

    @Test
    void testValidateItemIdxForTaskListValidIndex() throws WBBException {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Task 1"));
        validator.validateItemIdxForTaskList(taskList, 0, ui); // No exception should be thrown
    }

    @Test
    void testValidateItemIdxForTaskListEmptyTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateItemIdxForTaskList(taskList, 0, ui));
        assertTrue(exception.getMessage().contains("Task list is empty"));
    }

    @Test
    void testValidateItemIdxForTaskListIndexOutOfBounds() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Task 1"));
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateItemIdxForTaskList(taskList, taskList.size(), ui));
        assertTrue(exception.getMessage().contains("Invalid item index"));
    }

    @Test
    void testValidateAndGetTaskNameValidName() throws WBBException {
        String command = "todo Read book";
        String result = validator.validateAndGetTaskName(command, "todo", TaskType.TODO);
        assertEquals("Read book", result);
    }

    @Test
    void testValidateAndGetTaskNameEmptyTaskName() {
        String command = "todo ";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateAndGetTaskName(command, "todo", TaskType.TODO));
        assertTrue(exception.getMessage().contains("Missing task name"));
    }

    @Test
    void testValidateTaskNameByValidTaskName() throws WBBException {
        String taskName = "Submit report /by Monday";
        validator.validateTaskNameBy(taskName, TaskType.DEADLINE); // No exception should be thrown
    }

    @Test
    void testValidateTaskNameByMissingBy() {
        String taskName = "Submit report";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateTaskNameBy(taskName, TaskType.DEADLINE));
        assertTrue(exception.getMessage().contains("Missing '/by'"));
    }

    @Test
    void testValidateAndGetTaskNameByValidInput() throws WBBException {
        String taskName = "Submit report /by Monday";
        String[] result = validator.validateAndGetTaskNameBy(taskName, TaskType.DEADLINE);
        assertArrayEquals(new String[]{"Submit report ", " Monday"}, result);
    }

    @Test
    void testValidateAndGetTaskNameByMissingDeadline() {
        String taskName = "Submit report /by";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateAndGetTaskNameBy(taskName, TaskType.DEADLINE));
        assertTrue(exception.getMessage().contains("Missing deadline"));
    }

    @Test
    void testValidateFromToValidInput() throws WBBException {
        String taskName = "Project work /from Monday /to Friday";
        validator.validateFromTo(taskName, TaskType.EVENT); // No exception should be thrown
    }

    @Test
    void testValidateFromToMissingFromOrTo() {
        String taskName = "Project work /from Monday";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateFromTo(taskName, TaskType.EVENT));
        assertTrue(exception.getMessage().contains("Missing '/from' or '/to'"));
    }

    @Test
    void testValidateFromToFromAfterTo() {
        String taskName = "Project work /to Friday /from Monday";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateFromTo(taskName, TaskType.EVENT));
        assertTrue(exception.getMessage().contains("/from must come before /to"));
    }
}
