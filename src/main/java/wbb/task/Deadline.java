package wbb.task;
import wbb.util.DateTimeUtility;

public class Deadline extends Task {
    protected String by;

    /**
     * Deadline which has a description and a deadline.
     * @param description The name of the task.
     * @param by The deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts friendly print format to LocalDateTime/LocalDate, and returns the tasks that are due today.
     * @return The tasks that are due today.
     */
    @Override
    public boolean isDueToday() {
        return new DateTimeUtility().isDueToday(by);
    }

    /**
     * toString method for this class.
     * @return The string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Provides a format for saving into hard disk.
     * @return The desired format for saving into hard disk.
     */
    @Override
    public String toFileFormat() {
        return String.format("deadline | %b | %s | %s", isDone, description, by);
    }
}
