package wbb.util;

import org.junit.jupiter.api.Test;
import wbb.task.TaskType;
import wbb.ui.Ui;
import wbb.exception.WBBException;
import wbb.task.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private final Validator validator = new Validator();
    private final Ui ui = new Ui();

    @Test
    void testValidateAndGetItemIdx_ValidIndex() throws WBBException {
        String command = "mark 3";
        int result = validator.validateAndGetItemIdx(command);
        assertEquals(2, result); // Should return 2 as 3 - 1 = 2
    }

    @Test
    void testValidateAndGetItemIdx_MissingIndex() {
        String command = "mark";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Missing item index"));
    }

    @Test
    void testValidateAndGetItemIdx_NonIntegerIndex() {
        String command = "mark two";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Index must be integers only"));
    }

    @Test
    void testValidateAndGetItemIdx_NonPositiveIndex() {
        String command = "mark 0";
        WBBException exception = assertThrows(WBBException.class, () -> validator.validateAndGetItemIdx(command));
        assertTrue(exception.getMessage().contains("Index must be greater than or equal to 1"));
    }

    @Test
    void testValidateItemIdxForTaskList_ValidIndex() throws WBBException {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Task 1"));
        validator.validateItemIdxForTaskList(taskList, 0, ui); // No exception should be thrown
    }

    @Test
    void testValidateItemIdxForTaskList_EmptyTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateItemIdxForTaskList(taskList, 0, ui));
        assertTrue(exception.getMessage().contains("Task list is empty"));
    }

    @Test
    void testValidateItemIdxForTaskList_IndexOutOfBounds() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Task 1"));
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateItemIdxForTaskList(taskList, taskList.size(), ui));
        assertTrue(exception.getMessage().contains("Invalid item index"));
    }

    @Test
    void testValidateAndGetTaskName_ValidName() throws WBBException {
        String command = "todo Read book";
        String result = validator.validateAndGetTaskName(command, "todo", TaskType.TODO);
        assertEquals("Read book", result);
    }

    @Test
    void testValidateAndGetTaskName_EmptyTaskName() {
        String command = "todo ";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateAndGetTaskName(command, "todo", TaskType.TODO));
        assertTrue(exception.getMessage().contains("Missing task name"));
    }

    @Test
    void testValidateTaskNameBy_ValidTaskName() throws WBBException {
        String taskName = "Submit report /by Monday";
        validator.validateTaskNameBy(taskName, TaskType.DEADLINE); // No exception should be thrown
    }

    @Test
    void testValidateTaskNameBy_MissingBy() {
        String taskName = "Submit report";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateTaskNameBy(taskName, TaskType.DEADLINE));
        assertTrue(exception.getMessage().contains("Missing '/by'"));
    }

    @Test
    void testValidateAndGetTaskNameBy_ValidInput() throws WBBException {
        String taskName = "Submit report /by Monday";
        String[] result = validator.validateAndGetTaskNameBy(taskName, TaskType.DEADLINE);
        assertArrayEquals(new String[]{"Submit report ", " Monday"}, result);
    }

    @Test
    void testValidateAndGetTaskNameBy_MissingDeadline() {
        String taskName = "Submit report /by";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateAndGetTaskNameBy(taskName, TaskType.DEADLINE));
        assertTrue(exception.getMessage().contains("Missing deadline"));
    }

    @Test
    void testValidateFromTo_ValidInput() throws WBBException {
        String taskName = "Project work /from Monday /to Friday";
        validator.validateFromTo(taskName, TaskType.EVENT); // No exception should be thrown
    }

    @Test
    void testValidateFromTo_MissingFromOrTo() {
        String taskName = "Project work /from Monday";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateFromTo(taskName, TaskType.EVENT));
        assertTrue(exception.getMessage().contains("Missing '/from' or '/to'"));
    }

    @Test
    void testValidateFromTo_FromAfterTo() {
        String taskName = "Project work /to Friday /from Monday";
        WBBException exception = assertThrows(WBBException.class, ()
                -> validator.validateFromTo(taskName, TaskType.EVENT));
        assertTrue(exception.getMessage().contains("/from must come before /to"));
    }
}
