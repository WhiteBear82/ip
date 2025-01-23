import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        // Normalize the date by removing ordinal suffixes like "23rd" -> "23"
        String normalizedDate = by.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

        // List of possible date-time formats
        String[] possibleFormats = {
                "d 'of' MMMM yyyy, h:mma", // e.g., 23 of January 2025, 6:00pm
                "d 'of' MMMM yyyy",        // e.g., 23 of January 2025
        };

        // Iterate through the formats to parse the date
        for (String format : possibleFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, java.util.Locale.ENGLISH);

                // If time is included in the format, use LocalDateTime
                if (format.contains("h:mma") || format.contains("HHmm")) {
                    LocalDateTime taskDateTime = LocalDateTime.parse(normalizedDate, formatter);
                    return taskDateTime.toLocalDate().equals(LocalDate.now());
                } else { // Otherwise, use LocalDate
                    LocalDate taskDate = LocalDate.parse(normalizedDate, formatter);
                    return taskDate.equals(LocalDate.now());
                }
            } catch (DateTimeParseException ignored) {
                // Try the next format
            }
        }

        // No format works
        return false;
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
