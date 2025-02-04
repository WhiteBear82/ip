package wbb.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import wbb.util.DateTimeUtility;

class TaskTest {

    @Test
    void testTodoConstructor() {
        Task todo = new Todo("Read a book");
        assertEquals("Read a book", todo.getDescription());
        assertFalse(todo.isDone(), "Todo task should not be marked as done initially.");
    }

    @Test
    void testTodoToString() {
        Task todo = new Todo("Read a book");
        assertEquals("[T][ ] Read a book", todo.toString(), "Todo string representation is incorrect.");
        todo.setDone();
        assertEquals("[T][X] Read a book", todo.toString(),
                "Todo string representation after marking as done is incorrect.");
    }

    @Test
    void testTodoToFileFormat() {
        Task todo = new Todo("Read a book");
        assertEquals("todo | false | Read a book", todo.toFileFormat(), "Todo file format is incorrect.");
        todo.setDone();
        assertEquals("todo | true | Read a book", todo.toFileFormat(),
                "Todo file format after marking as done is incorrect.");
    }

    @Test
    void testDeadlineConstructor() {
        Task deadline = new Deadline("Submit assignment", "2025-01-30");
        assertEquals("Submit assignment", deadline.getDescription());
        assertFalse(deadline.isDone(), "Deadline task should not be marked as done initially.");
    }

    @Test
    void testDeadlineToString() {
        Task deadline = new Deadline("Submit assignment", "2025-01-30");
        assertEquals("[D][ ] Submit assignment (by: 2025-01-30)", deadline.toString(),
                "Deadline string representation is incorrect.");
        deadline.setDone();
        assertEquals("[D][X] Submit assignment (by: 2025-01-30)", deadline.toString(),
                "Deadline string representation after marking as done is incorrect.");
    }

    @Test
    void testDeadlineToFileFormat() {
        Task deadline = new Deadline("Submit assignment", "2025-01-30");
        assertEquals("deadline | false | Submit assignment | 2025-01-30", deadline.toFileFormat(),
                "Deadline file format is incorrect.");
        deadline.setDone();
        assertEquals("deadline | true | Submit assignment | 2025-01-30", deadline.toFileFormat(),
                "Deadline file format after marking as done is incorrect.");
    }

    @Test
    void testEventConstructor() {
        Task event = new Event("Team meeting", "2025-02-01 10:00", "2025-02-01 12:00");
        assertEquals("Team meeting", event.getDescription());
        assertFalse(event.isDone(), "Event task should not be marked as done initially.");
    }

    @Test
    void testEventToString() {
        Task event = new Event("Team meeting", "2025-02-01 10:00", "2025-02-01 12:00");
        assertEquals("[E][ ] Team meeting (from: 2025-02-01 10:00 to 2025-02-01 12:00)", event.toString(),
                "Event string representation is incorrect.");
        event.setDone();
        assertEquals("[E][X] Team meeting (from: 2025-02-01 10:00 to 2025-02-01 12:00)", event.toString(),
                "Event string representation after marking as done is incorrect.");
    }

    @Test
    void testEventToFileFormat() {
        Task event = new Event("Team meeting", "2025-02-01 10:00", "2025-02-01 12:00");
        assertEquals("event | false | Team meeting | 2025-02-01 10:00 | 2025-02-01 12:00",
                event.toFileFormat(), "Event file format is incorrect.");
        event.setDone();
        assertEquals("event | true | Team meeting | 2025-02-01 10:00 | 2025-02-01 12:00",
                event.toFileFormat(), "Event file format after marking as done is incorrect.");
    }

    @Test
    void testDeadlineIsDueToday() {
        DateTimeUtility dateTimeUtility = new DateTimeUtility();
        String today = dateTimeUtility.formatDate(LocalDate.now()); // Ensures correct formatting for today
        Task deadline = new Deadline("Submit report", today);
        assertTrue(deadline.isDueToday(), "Deadline task should be due today if the deadline is today.");
    }

    @Test
    void testDeadlineIsNotDueToday() {
        Task deadline = new Deadline("Submit report", "2025-12-31");
        assertFalse(deadline.isDueToday(), "Deadline task should not be due today if the deadline is not today.");
    }
}
