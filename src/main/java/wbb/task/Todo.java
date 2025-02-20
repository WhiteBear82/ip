package wbb.task;

/**
 * Represents a Todo task with a description, providing methods to represent the task as a string
 * and for saving it in a specific format.
 */
public class Todo extends Task {
    /**
     * Todo which has a description only.
     * @param description The name of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * toString method for this class.
     * @return The string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }


    /**
     * Provides a format for saving into hard disk.
     * @return The desired format for saving into hard disk.
     */
    @Override
    public String toFileFormat() {
        return String.format("todo | %b | %s", isDone(), description);
    }
}
