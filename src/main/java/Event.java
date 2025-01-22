public class Event extends Task{
    protected String from;
    protected String to;

    /**
     * Event which is has a description, start date/time and end date/time.
     * @param description The name of the task.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /**
     * toString method for this class.
     * @return The string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to " + to + ")";
    }


    /**
     * Provides a format for saving into hard disk.
     * @return The desired format for saving into hard disk.
     */
    @Override
    public String toFileFormat() {
        return String.format("event | %b | %s | %s | %s", isDone, description, from, to);
    }
}
