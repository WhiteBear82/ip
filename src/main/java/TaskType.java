public enum TaskType {
    TODO("todo", "Please enter a task name (e.g., todo borrow book)."),
    DEADLINE("deadline", "Please enter a valid deadline (e.g., deadline return book /by Sunday)."),
    EVENT("event", "Please enter event details (e.g., event project meeting /from Mon 2pm /to 4pm).");

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
        for (TaskType t : TaskType.values())
            if (t.type.equalsIgnoreCase(type))
                taskType = t;
        return taskType;
    }
}
