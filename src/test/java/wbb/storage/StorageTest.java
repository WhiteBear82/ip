package wbb.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class StorageTest {

    @Test
    public void testSaveTasksDoesNotThrowException() {
        Storage storage = new Storage();
        ArrayList<wbb.task.Task> tasks = new ArrayList<>();
        assertDoesNotThrow(() -> storage.saveTasks(tasks));
    }

    @Test
    public void testLoadTasksReturnsEmptyListWhenNoFile() {
        Storage storage = new Storage();
        ArrayList<wbb.task.Task> tasks = storage.loadTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }
}
