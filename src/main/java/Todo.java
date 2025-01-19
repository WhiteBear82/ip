public class Todo extends Task{
    /**
     * Todo which has a description only.
     * @param description The name of the task.
     */
    public Todo(String description) {
        super(description);
    }


    /**
     * toString method for this class
     * @return The string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
