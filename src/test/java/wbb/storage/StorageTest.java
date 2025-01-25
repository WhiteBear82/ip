package wbb.storage;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

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
