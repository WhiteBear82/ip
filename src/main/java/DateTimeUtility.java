import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtility {

    /**
     * Tries to parse deadline to either LocalDate or LocalDateTime.
     * @param deadline The deadline.
     * @return The deadline, in LocalDate or LocalDateTime, if applicable.
     */
    protected String tryParseDateAndOrTime(String deadline) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        // Try date-time parsing and get formatted string
        String result = tryParseDateTime(deadline, dateTimeFormatter);
        if (result != null)
            return result;

        // Try date-only parsing and get formatted string
        result = tryParseDate(deadline, dateFormatter);
        if (result != null)
            return result;

        // Try time-only parsing and get formatted string
        result = tryParseTime(deadline, timeFormatter);
        if (result != null)
            return result;

        return deadline;
    }
    /**
     * Tries to parse String to LocalDateTime.
     * @param dateTimeString The String to be parsed.
     * @param formatter The DateTimeFormatter.
     * @return The parsed LocalDateTime, if possible, otherwise null.
     */
    protected String tryParseDateTime(String dateTimeString, DateTimeFormatter formatter) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
            if (dateTime.isBefore(LocalDateTime.now()))
                return null;
            return formatDateTime(dateTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Tries to parse String to LocalDate.
     * @param dateString The String to be parsed.
     * @param formatter The DateTimeFormatter.
     * @return The parsed LocalDate, if possible, otherwise null.
     */
    protected String tryParseDate(String dateString, DateTimeFormatter formatter) {
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            if (date.isBefore(LocalDate.now()))
                return null;
            return formatDate(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Tries to parse String to LocalTime.
     * @param timeString The String to be parsed.
     * @param formatter The DateTimeFormatter.
     * @return The parsed LocalTime, if possible, otherwise null.
     */
    protected String tryParseTime(String timeString, DateTimeFormatter formatter) {
        try {
            LocalTime time = LocalTime.parse(timeString, formatter);
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time); // Default to today's date
            if (dateTime.isBefore(LocalDateTime.now()))
                return null;
            return formatDateTime(dateTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts a LocalDateTime to friendly print format, e.g., 15th of January 2025, 7:30pm.
     * @param dateTime The LocalDateTime.
     * @return A friendly print format.
     */
    protected String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy, h:mma");
        return ordinalDay(dateTime.getDayOfMonth()) + " of " + dateTime.format(dateTimeFormatter);
    }

    /**
     * Converts a LocalDateTime to friendly print format, e.g., 15th of January 2025.
     * @param date The LocalDate.
     * @return A friendly print format.
     */
    protected String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return ordinalDay(date.getDayOfMonth()) + " of " + date.format(dateFormatter);
    }

    /**
     * Add "st", "nd", "rd", or "th" depending on the day, e.g. 1 -> 1st.
     * @param day The day in number.
     * @return The appended format.
     */
    protected String ordinalDay(int day) {
        if (day >= 11 && day <= 13) {
            return day + "th";
        }
        return switch (day % 10) {
            case 1 -> day + "st";
            case 2 -> day + "nd";
            case 3 -> day + "rd";
            default -> day + "th";
        };
    }

    /**
     * Remove "st", "nd", "rd", or "th" depending on the day, e.g. 1 -> 1st.
     * @param day The day in number.
     * @return The un-appended format.
     */
    protected String removeOrdinalDay(String day) {
        return day.replaceAll("(\\d+)(st|nd|rd|th)", "$1");
    }

    /**
     * Checks if a given deadline task is due today.
     * @param by The deadline.
     * @return True if the deadline is equals today, otherwise false.
     */
    protected boolean isDueToday(String by) {
        String normalizedDate = removeOrdinalDay(by);  // e.g. "23rd" -> "23"

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
}
