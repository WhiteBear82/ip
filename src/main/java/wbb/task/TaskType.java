package wbb.task;

/**
 * Represents the types of tasks (e.g., TODO, DEADLINE, EVENT) that can be created.
 * Each task type defines how to parse and create a specific task from user input.
 */
public enum TaskType {
    TODO("todo", "Please enter a task name (e.g., todo borrow book).") {
        @Override
        public Task createTask(String[] parts) {
            return new Todo(parts[2].trim());
        }
    },
    DEADLINE("deadline", "Please enter a valid deadline (e.g., deadline return book /by Sunday).") {
        @Override
        public Task createTask(String[] parts) {
            return new Deadline(parts[2].trim(), parts[3].trim());
        }
    },
    EVENT("event", "Please enter event details (e.g., event project meeting /from Mon 2pm /to 4pm).") {
        @Override
        public Task createTask(String[] parts) {
            return new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        }
    };

    private final String type;
    private final String errorMessage;

    /**
     * Constructor.
     * @param type The type of task (e.g. todo, deadline, or event).
     * @param errorMessage The error message.
     */
    TaskType(String type, String errorMessage) {
        this.type = type;
        this.errorMessage = errorMessage;
    }

    /**
     * Provides the correct format to prevent future errors.
     * @return The correct format.
     */
    public String getFormatExample() {
        return errorMessage;
    }

    /**
     * Get the taskType from a given String.
     * @param type The type of task (e.g. todo, deadline, or event).
     * @return The taskType (e.g. todo, deadline, or event).
     */
    public static TaskType fromString(String type) {
        TaskType taskType = null;
        for (TaskType t : TaskType.values()) {
            if (t.type.equalsIgnoreCase(type)) {
                taskType = t;
            }
        }
        return taskType;
    }

    /**
     * Create a task (e.g. Todo, Deadline, or Event)
     * @param parts The parts of the user input command (e.g. ["deadline", "return book", "/by", "Sunday"]
     * @return The Task.
     */
    public abstract Task createTask(String[] parts);
}
