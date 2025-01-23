public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor.
     * @param description The taskName.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Provides the taskName.
     * @return The taskName.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets task to "isDone".
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Unsets task from "isDone".
     */
    public void setUndone() {
        this.isDone = false;
    }

    /**
     * Provides [ ] if task is not done, otherwise [X].
     * @return [ ] if task is not done, otherwise [X].
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * toString method for this class.
     * @return The string.
     */
    @Override
    public String toString() {
        return this.getStatusIcon() + " " + this.getDescription();
    }

    public abstract String toFileFormat();

    /**
     * For deadline class, which has a method for override, other tasks default to not having due date.
     * @return False.
     */
    public boolean isDueToday() {
        return false;
    }
}
